package com.example.sispizza;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sispizza.database.DatabaseHelper;

import java.text.DecimalFormat;

public class DetallePizza1 extends AppCompatActivity {
    private TextView txtAmount; // Referencia al TextView para mostrar la cantidad
    private TextView textPrice; // Referencia al TextView para mostrar el precio
    private int cantidad = 1; // Inicializamos la cantidad en 1
    public double precioPizza;
    private int imagenPizzaId;
    private String descripcionPizza;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallepizza1); // Asegúrate de tener el layout adecuado

        // Recibe los datos pasados desde el Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imagenPizzaId = extras.getInt("imagenPizza");
            descripcionPizza = extras.getString("descripcionPizza");
            precioPizza = extras.getDouble("precioPizza"); // Recupera el precio
            Log.d("DetallePizza1", "Precio recibido: " + precioPizza);
            Log.d("DetallePizza1", "Imagen recibido: " + imagenPizzaId);

            // Muestra los datos en la interfaz de usuario
            ImageView imageView = findViewById(R.id.imageView);
            TextView descripcionTextView = findViewById(R.id.descriptionTextView);

            //imageView.setImageResource(imagenPizzaId);
            descripcionTextView.setText(descripcionPizza);

            // Muestra el precio de la pizza en el TextView correspondiente
            TextView precioPizzaTextView = findViewById(R.id.textPrice);
            precioPizzaTextView.setText(String.format("Bs. %.2f", precioPizza));
            Log.d("DetallePizza1", "Precio TextView recibido: " + precioPizza);

            actualizarCantidadYPrecio();
        }

        // Obtén referencias a los elementos de la interfaz de usuario
        txtAmount = findViewById(R.id.txtAmount);
        textPrice = findViewById(R.id.textPrice);
        EditText etCliente = findViewById(R.id.etCliente);
        RadioGroup radioGroupSalsas = findViewById(R.id.radioGroupSalsas);

        Button btIncrease = findViewById(R.id.btIncrease);
        Button btDecrease = findViewById(R.id.btDecrease);

        // Configura los OnClickListener para los botones
        btIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementarCantidad();
            }
        });

        btDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementarCantidad();
            }
        });

        // Inicialmente, muestra el precio base en textPrice
        actualizarCantidadYPrecio();

        dbHelper = new DatabaseHelper(this);
        // OnClickListener para el botón "Confirmar"
        Button btConfirmar = findViewById(R.id.btConfirmar);
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DetallePizzaButton", "Posible error aqui: ");
                // Obtén el valor del cliente ingresado por el usuario
                String cliente = etCliente.getText().toString();
                Log.d("DetallePizzaButton", "Posible error aqui:2 ");
                // Obtén la descripción, cantidad y precio total
                String descripcionPedido = descripcionPizza;
                int cantidadPedido = cantidad;
                double precioTotalPedido = cantidad * precioPizza;

                // Obtén la salsa seleccionada
                String salsaElegida = obtenerSalsaSeleccionada(radioGroupSalsas);

                // Luego, pasa la salsaElegida al método addPedido
                long newRowId = dbHelper.addPedido(cliente, descripcionPedido, cantidadPedido, precioTotalPedido, salsaElegida);
                Log.d("DetallePizzaButton", "Posible error aqui: 3");
                if (newRowId != -1) {
                    // El pedido se agregó exitosamente a la base de datos
                    mostrarMensaje("Pedido confirmado y guardado en la base de datos.");
                    Log.d("DetallePizzaButton", "Posible error aqui: 4");
                    Toast.makeText(DetallePizza1.this, "Se Realizo el pedido", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DetallePizza1.this, MainActivity.class);
                    Log.d("DetallePizzaButton", "Posible error aqui: ");
                    startActivity(intent);

                } else {
                    // Hubo un error al agregar el pedido a la base de datos
                    mostrarMensaje("Error al guardar el pedido en la base de datos.");
                }
            }
        });
    }

    private void incrementarCantidad() {
        cantidad++;
        Log.d("DetallePizzaPRecio", "Cambio de precio3");
        actualizarCantidadYPrecio();
    }

    private void decrementarCantidad() {
        if (cantidad > 1) {
            cantidad--;
            Log.d("DetallePizzaPRecio", "Cambio de precio2");
            actualizarCantidadYPrecio();
        }
    }

    private void actualizarCantidad() {
        txtAmount.setText(String.valueOf(cantidad));
    }

    private void actualizarPrecio() {
        double precioTotal = cantidad * (precioPizza+17);
        Log.d("DetallePizzaPRecio", "Cambio de precio");
        String precioFormateado = String.format("Bs. %.2f", precioTotal);
        Log.d("DetallePizzaPRecio", "Cambio de precio real"+precioFormateado);
        textPrice.setText(precioFormateado);
    }

    private void actualizarCantidadYPrecio() {
        actualizarCantidad();
        actualizarPrecio();
        Log.d("DetallePizzaPRecio", "Cambio de precio4");
    }

    private String obtenerSalsaSeleccionada(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText().toString();
        } else {
            return "";
        }
    }

    private void mostrarMensaje(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle("Mensaje")
                .setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
