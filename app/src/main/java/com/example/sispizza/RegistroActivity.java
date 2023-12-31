package com.example.sispizza;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.sispizza.database.DatabaseHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.math.BigInteger;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNuevoUsuario;
    private EditText etNuevaContraseña;
    private Button btnRegistrar;
    private DatabaseHelper dbHelper;

    private CardView cardOne, cardTwo, cardThree, cardFour;

    private boolean isAtLeast8 = false, hasUpperCase = false, hasNumber = false, hasSymbol = false, isRegistrationClickeable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevaContraseña = findViewById(R.id.etNuevaContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        cardOne = findViewById(R.id.cardOne);
        cardTwo = findViewById(R.id.cardTwo);
        cardThree = findViewById(R.id.cardThree);
        cardFour = findViewById(R.id.cardFour);

        dbHelper = new DatabaseHelper(this);
        btnRegistrar.setBackgroundColor(Color.parseColor("#dcdcdc"));
        btnRegistrar.setEnabled(isRegistrationClickeable);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoUsuario = etNuevoUsuario.getText().toString();
                String nuevaContraseña = etNuevaContraseña.getText().toString();


                // Genera un salt aleatorio
                String salt = generateSalt(16);

                // Aplicando Encriptacion SHA256
                //String passwordSha = bin2hex(getHash(nuevaContraseña));


                // Combina el salt con la contraseña
                String saltedPassword = nuevaContraseña + salt;
                Log.i("salt saved", saltedPassword);

                // Aplicando Encriptación SHA256
                String passwordSha = bin2hex(getHash(saltedPassword));

                Log.i("Password Hash saved", passwordSha);

                // Almacena el salt junto con la contraseña hasheada en la base de datos
                long newRowId = dbHelper.addUser(nuevoUsuario, passwordSha, salt);

                if (newRowId != -1) {
                    // Registro de usuario exitoso
                    Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad de registro después de un registro exitoso
                } else {
                    // Error al registrar el usuario
                    Toast.makeText(RegistroActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inputChange();
    }

    public byte[] getHash(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }


    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
    }

    private boolean checkEmptyField(String user, String password){
        if (user.length()>0 && password.length()>0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("ResourceType")
    private void passwordCheck(){
        String password = etNuevaContraseña.getText().toString();



        //Verificando que tenga minimo 8 caracteres
        if (password.length()>= 8){
            isAtLeast8 = true;
            cardOne.setCardBackgroundColor((getColor(R.color.accent)));
        }else{
            isAtLeast8 = false;
            cardOne.setCardBackgroundColor((getColor(R.color.Default)));
        }

        //Verificando letra mayuscula
        if (password.matches("(.*[A-Z].*)")){
            hasUpperCase = true;
            cardTwo.setCardBackgroundColor(getColor(R.color.accent));
        }else {
            hasUpperCase = false;
            cardTwo.setCardBackgroundColor(getColor(R.color.Default));
        }

        //verificando caracter numerico
        if(password.matches("(.*[0-9].*)")){
            hasNumber = true;
            cardThree.setCardBackgroundColor(getColor(R.color.accent));
        }else {
            hasNumber = false;
            cardThree.setCardBackgroundColor(getColor(R.color.Default));
        }

        //verificando que contenga numero
        if (password.matches("^(?=.*[_.()$&@]).*$")){
            hasSymbol = true;
            cardFour.setCardBackgroundColor(getColor(R.color.accent));
        }else{
            hasSymbol = false;
            cardFour.setCardBackgroundColor(getColor(R.color.Default));
        }

        checkRequirements();
    }

    @SuppressLint("ResourceType")
    private void checkRequirements(){
        if(isAtLeast8 && hasUpperCase && hasNumber && hasSymbol){
            isRegistrationClickeable = true;
            btnRegistrar.setBackgroundColor(Color.parseColor("#e69026"));

            btnRegistrar.setEnabled(true);

        }else {
            isRegistrationClickeable = false;
            btnRegistrar.setBackgroundColor(Color.parseColor("#dcdcdc"));
            btnRegistrar.setEnabled(false);
        }

    }

    private void inputChange(){
        etNuevaContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordCheck();
                checkRequirements();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public static String generateSalt(int length) {
        // Longitud de bytes deseada (la longitud de la cadena de caracteres será mayor)
        int byteLength = length / 2; // Divide por 2 porque cada byte se representa como dos caracteres hexadecimales

        // Genera bytes aleatorios
        byte[] saltBytes = new byte[byteLength];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(saltBytes);

        // Convierte los bytes en una cadena hexadecimal
        String salt = bytesToHex(saltBytes);

        return salt;
    }

    // Convierte un arreglo de bytes en una cadena hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
