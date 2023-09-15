package com.example.sispizza;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sispizza.database.DatabaseHelper;



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

                // Agregar el nuevo usuario a la base de datos
                long newRowId = dbHelper.addUser(nuevoUsuario, nuevaContraseña);

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
}
