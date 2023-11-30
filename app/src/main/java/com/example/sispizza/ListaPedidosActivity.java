package com.example.sispizza; // Asegúrate de usar el nombre de paquete correcto

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sispizza.database.DatabaseHelper; // Importa tu clase DatabaseHelper

import java.util.ArrayList;

public class ListaPedidosActivity extends AppCompatActivity {

    private ListView listView;
    private TextView totalPedido;
    private Button botonPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos); // Usa el layout correcto

        listView = findViewById(R.id.listView);
        totalPedido = findViewById(R.id.totalPedido);
        botonPagar = findViewById(R.id.botonPagar);

        configurarListView();
        configurarBotonPagar();
    }

    private void configurarListView() {
        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<String> listaPedidos = db.obtenerListaPedidos();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPedidos);
        listView.setAdapter(adapter);

        // Aquí, calcula y actualiza el total de los pedidos
        actualizarTotalPedidos(listaPedidos);
    }

    private void actualizarTotalPedidos(ArrayList<String> pedidos) {
        double total = 0.0;
        // Suponiendo que cada elemento en 'pedidos' tiene el formato "Precio Total: $X.XX"
        for (String pedido : pedidos) {
            String[] partes = pedido.split(", ");
            for (String parte : partes) {
                if (parte.startsWith("Precio Total: ")) {
                    String precio = parte.replace("Precio Total: $", "");
                    total += Double.parseDouble(precio);
                }
            }
        }
        totalPedido.setText("Total: $" + String.format("%.2f", total));
    }

    private void configurarBotonPagar() {
        botonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí implementarías la lógica del proceso de pago
            }
        });
    }
}
