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
import java.util.List;

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
        // Obtén referencias a los elementos de la interfaz de usuario
        txtAmount = findViewById(R.id.txtAmount);
        textPrice = findViewById(R.id.textPrice);
        // Recibe los datos pasados desde el Intent
        Bundle extras = getIntent().getExtras();
        precioPizza = extras.getDouble("precioPizza"); // Recupera el precio
        Log.d("DetallePizza3", "Precio recibidoWWW: " + precioPizza);
        if (extras != null) {
            imagenPizzaId = extras.getInt("imagenPizza");
            descripcionPizza = extras.getString("descripcionPizza");
            precioPizza = extras.getDouble("precioPizza"); // Recupera el precio
            if (precioPizza == 0) {
                Log.d("DetallePizza3", "Precio es 0. Finalizando la actividad.");
                finish();
                return;  // Termina la ejecución del método onCreate
            }
            // Muestra los datos en la interfaz de usuario
            //ImageView imageView = findViewById(R.id.imageView);
            //TextView descripcionTextView = findViewById(R.id.descriptionTextView);

            //imageView.setImageResource(imagenPizzaId);
            //descripcionTextView.setText(descripcionPizza);
            // Muestra el precio de la pizza en el TextView correspondiente
            //TextView precioPizzaTextView = findViewById(R.id.textPrice);
            //precioPizzaTextView.setText(String.format("Bs. %.2f", precioPizza));
            actualizarCantidadYPrecio();
        }



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

                // Obtén el valor del cliente ingresado por el usuario
                String cliente = etCliente.getText().toString();
                // Obtén la descripción, cantidad y precio total
                String descripcionPedido = descripcionPizza;
                int cantidadPedido = cantidad;
                double precioTotalPedido = cantidad * precioPizza;

                // Obtén la salsa seleccionada
                String salsaElegida = obtenerSalsaSeleccionada(radioGroupSalsas);

                // Luego, pasa la salsaElegida al método addPedido
                long newRowId = dbHelper.addPedido("1", cliente, cantidadPedido, precioTotalPedido, salsaElegida);
                Log.d("DetallePizzaButton", "Posible error aqui: 3");
                if (newRowId != -1) {
                    // El pedido se agregó exitosamente a la base de datos
                    mostrarMensaje("Pedido confirmado y guardado en la base de datos.");
                    Log.d("DetallePizzaButton", "Posible error aqui: 4");
                    Toast.makeText(DetallePizza1.this, "Se Realizo el pedido", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DetallePizza1.this, MainActivity.class);
                    Log.d("DetallePizzaButton", "Posible error aqui: ");
                    List<Pedido> listaPedidos = dbHelper.obtenerTodosLosPedidos(); // Suponiendo que tienes un método para obtener todos los pedidos
                    for (Pedido pedido : listaPedidos) {
                        Log.d("DetallePizzaButton", "ID: " + pedido.getId() +
                                ", Cliente: " + pedido.getCliente() +
                                ", Descripción: " + pedido.getDescripcion() +
                                ", Cantidad: " + pedido.getCantidad() +
                                ", Precio Total: " + pedido.getPrecioTotal() +
                                ", Salsa: " + pedido.getSalsa());
                    }
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
        actualizarCantidadYPrecio();
    }

    private void decrementarCantidad() {
        if (cantidad > 1) {
            cantidad--;
            actualizarCantidadYPrecio();
        }
    }

    private void actualizarCantidad() {
        txtAmount.setText(String.valueOf(cantidad));
    }

    private void actualizarPrecio() {
        double precioTotal = cantidad * (precioPizza);
        String precioFormateado = String.format("Bs. %.2f", precioTotal);
        textPrice.setText(precioFormateado);
    }

    private void actualizarCantidadYPrecio() {
        actualizarCantidad();
        actualizarPrecio();
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
