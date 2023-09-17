package com.example.sispizza;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetallePizza1 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallepizza1); // Asegúrate de tener el layout adecuado

        // Recibe los datos pasados desde el Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int imagenPizza = extras.getInt("imagenPizza");
            String nombrePizza = extras.getString("nombrePizza");
            String descripcionPizza = extras.getString("descripcionPizza");

            // Muestra los datos en la interfaz de usuario
            ImageView imageView = findViewById(R.id.imageView);
            //TextView nombreTextView = findViewById(R.id.nombrePizzaTextView);
            TextView descripcionTextView = findViewById(R.id.descriptionTextView);

            imageView.setImageResource(imagenPizza);
            //nombreTextView.setText(nombrePizza);
            descripcionTextView.setText(descripcionPizza);
        }
    }
}



