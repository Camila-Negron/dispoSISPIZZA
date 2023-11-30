package com.example.sispizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sispizza.database.DatabaseHelper;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText newPasswordEditText;
    private Button changePasswordButton;

    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        changePasswordButton = findViewById(R.id.change_password_button);

        // Obtener el nombre de usuario pasado desde la actividad anterior
        username = getIntent().getStringExtra("username");

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();

                // Verificar si la nueva contraseña es "admintest"
                if ("admintest".equals(newPassword)) {
                    // Si es "admintest", redirigir a la clase Change
                    Intent intent = new Intent(ChangePasswordActivity.this, ProductManagementActivity.class);
                    startActivity(intent);
                } else {
                    // Si no es "admintest", redirigir a la clase Main
                    Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                // Aquí deberías agregar la lógica para cambiar la contraseña en la base de datos
                // utilizando el nombre de usuario (username) y la nueva contraseña (newPassword).
                // Puedes utilizar DatabaseHelper o cualquier otra clase que maneje la base de datos.

                // Después de cambiar la contraseña, puedes cerrar esta actividad.

            }
        });
    }
}
