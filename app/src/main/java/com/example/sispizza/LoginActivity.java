package com.example.sispizza;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.sispizza.database.DatabaseHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegistrarse;
    private DatabaseHelper dbHelper;

    private Context mContext;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        mContext = this; // Asignar el Context actual

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Obtener el salt de la base de datos
                String salt = dbHelper.getSaltByUsername(username);
                int intentos = myPreferences.getInt("INTENTOS", 0);
                Calendar calendar = Calendar.getInstance();
                long fechaActualEnMinutos = calendar.getTimeInMillis() / (10 * 1000);
                long fechaGuardada = myPreferences.getLong("FECHA", 0);
                if(intentos>=3){
                    if (fechaActualEnMinutos - fechaGuardada >= 1) {
                        // Ha pasado un día completo desde la fecha almacenada

                        changeDatePreferences();
                        if (salt != null) {
                            // Combina el salt con la contraseña ingresada
                            String saltedPassword = password + salt;

                            // Aplicando Encriptación SHA256
                            String passwordConverted = bin2hex(getHash(saltedPassword));
                            Log.i("Password Hash Converted", passwordConverted);

                            if (dbHelper.authenticateUser(username, passwordConverted,mContext)) {
                                // Autenticación exitosa...
                                if(username=="admintest"){
                                    Log.i("Authentication", "Autenticacion exitosa dirigiendo a pantalla principal22222");
                                    changeDatePreferences();
                                    Intent intent = new Intent(LoginActivity.this, ProductManagementActivity.class);
                                    startActivity(intent);
                                }else {
                                    Log.i("Authentication", "Autenticacion exitosa dirigiendo a pantalla principal");
                                    changeDatePreferences();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                            } else {
                                // Autenticación fallida, mostrar un mensaje de error.
                                verifyAttempts();
                                Log.i("Authentication", "Autenticacion fallida, las credenciales no coinciden");
                                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Salt no encontrado en la base de datos
                            verifyAttempts();
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            Log.i("attempts counter", "Numero de intentos: " + intentos);
                        }
                    } else {
                        // No ha pasado un día completo
                        Toast.makeText(LoginActivity.this, "Inicio de sesion bloqueado", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if (salt != null) {
                        // Combina el salt con la contraseña ingresada
                        String saltedPassword = password + salt;

                        // Aplicando Encriptación SHA256
                        String passwordConverted = bin2hex(getHash(saltedPassword));
                        Log.i("Password Hash Converted", passwordConverted);

                        if (dbHelper.authenticateUser(username, passwordConverted,mContext)) {
                            // Autenticación exitosa...
                            if(username=="admintest"){
                                Log.i("Authentication", "Autenticacion exitosa dirigiendo a pantalla principal222222");
                                changeDatePreferences();
                                Intent intent = new Intent(LoginActivity.this, ProductManagementActivity.class);
                                startActivity(intent);
                            }else {
                                Log.i("Authentication", "Autenticacion exitosa dirigiendo a pantalla principal");
                                changeDatePreferences();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // Autenticación fallida, mostrar un mensaje de error.
                            verifyAttempts();
                            Log.i("Authentication", "Autenticacion fallida, las credenciales no coinciden");
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Salt no encontrado en la base de datos
                        verifyAttempts();
                        Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        Log.i("attempts counter", "Numero de intentos: " + intentos);
                    }

                }


            }
        });

        Button btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de registro cuando se presiona el botón "Registrarse"
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
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

    private void verifyAttempts(){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        int intentos = myPreferences.getInt("INTENTOS", 0);
        myEditor.putInt("INTENTOS", intentos+1);

        Log.i("attempts counter", "Numero de intentos: " + intentos);
        if (intentos+1 == 3){
            Calendar calendar = Calendar.getInstance();
            long fechaActualEnMilisegundos = calendar.getTimeInMillis();
            long fechaEnMinutos = fechaActualEnMilisegundos / (60 * 1000);
            myEditor.putLong("FECHA", fechaEnMinutos);
        }
        myEditor.commit();
    }
    private void changeDatePreferences(){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putInt("INTENTOS", 0);
        myEditor.putLong("FECHA", 0);
        myEditor.commit();
    }
}
