package com.example.sispizza;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sispizza.database.DatabaseHelper;
import java.util.ArrayList;


public class ListaPedidosActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;
    private TextView totalPedido;
    private Button botonPagar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        //setContentView(R.layout.activity_main);

        // Inicializa la base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtén una referencia al ListView en tu diseño
        listView = findViewById(R.id.listView);

        // Obtén la lista de pedidos desde la base de datos (esto dependerá de tu implementación)
        ArrayList<String> listaPedidos = dbHelper.obtenerListaPedidos();

        // Crea un adaptador para mostrar la lista en el ListView
        adapter = new ArrayAdapter<>(this, R.layout.item_pedido, listaPedidos);

        // Configura el adaptador en el ListView
        listView.setAdapter(adapter);

        // Inicializa el texto del total y el botón de pagar
        totalPedido = findViewById(R.id.totalPedido);
        botonPagar = findViewById(R.id.botonPagar);

        // Calcula y muestra el total del pedido (esto dependerá de tu implementación)
        totalPedido.setText("Total: $" + calcularTotalPedido(listaPedidos));

        // Agrega funcionalidad al botón de pagar (esto dependerá de tu implementación)
        botonPagar.setOnClickListener(v -> {
            // Aquí iría la lógica para realizar el pago
        });
    }

    private double calcularTotalPedido(ArrayList<String> listaPedidos) {
        double total = 0.0;
        // Aquí implementarías la lógica para sumar el total de los pedidos
        return total;
    }
}
