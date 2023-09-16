package com.example.sispizza;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sispizza.database.DatabaseHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegistroActivity extends AppCompatActivity {

    private EditText etNuevoUsuario;
    private EditText etNuevaContraseña;
    private Button btnRegistrar;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevaContraseña = findViewById(R.id.etNuevaContraseña);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        dbHelper = new DatabaseHelper(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoUsuario = etNuevoUsuario.getText().toString();
                String nuevaContraseña = etNuevaContraseña.getText().toString();

                // Aplicando Encriptacion SHA256
                String passwordSha = bin2hex(getHash(nuevaContraseña));

                Log.i("Password Hash saved",bin2hex(getHash(nuevaContraseña)));

                // Agregar el nuevo usuario a la base de datos
                long newRowId = dbHelper.addUser(nuevoUsuario, passwordSha);

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
    }

    public byte[] getHash(String password) {
        MessageDigest digest=null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }
    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }

}
