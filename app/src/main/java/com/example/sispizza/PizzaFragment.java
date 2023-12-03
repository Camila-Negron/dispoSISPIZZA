package com.example.sispizza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PizzaFragment extends Fragment {

        private RecyclerView recyclerView;
        private PizzaAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_pizza, container, false);

                recyclerView = view.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                List<Pizza> pizzaList = new ArrayList<>();
                // Agrega elementos Pizza a la lista
                pizzaList.add(new Pizza("Pizza Boliviana", R.drawable.boliviana, "Locoto, Tomate, mozzarella y albahaca.",60.50));
                pizzaList.add(new Pizza("Pizza Champiñon", R.drawable.champi, "Champiñom y queso fundido.",47.50));
                pizzaList.add(new Pizza("Pizza Hawaiana", R.drawable.hawaiana, "Piña, Vegetales frescos y queso.",55.50));
                pizzaList.add(new Pizza("Pizza Margarita", R.drawable.camaron, "Camaron, Tomate, mozzarella y albahaca.",50.50));
                pizzaList.add(new Pizza("Pizza Pepperoni", R.drawable.mariscos, "Pepperoni y queso fundido.",45.50));
                pizzaList.add(new Pizza("Pizza Vegetariana", R.drawable.napolitana, "Vegetales frescos y queso.",65.50));

                adapter = new PizzaAdapter( pizzaList);
                recyclerView.setAdapter(adapter);
                adapter.setOnDetalleClickListener(new PizzaAdapter.OnDetalleClickListener() {
                        @Override
                        public void onDetalleClick(int position) {
                                // Manejar el clic del botón "Detalles" aquí
                                Toast.makeText(getActivity(), "Detalles de la pizza en la posición " + position, Toast.LENGTH_SHORT).show();

                                // Obtener la pizza seleccionada
                                Pizza selectedPizza = pizzaList.get(position);

                                // Crea un intent para abrir la actividad de detalles y pasa los datos
                                Intent intent = new Intent(getActivity(), DetallePizza1.class);
                                intent.putExtra("nombrePizza", selectedPizza.getNombre());
                                intent.putExtra("imagenPizza", selectedPizza.getImagen());
                                intent.putExtra("descripcionPizza", selectedPizza.getDescripcion());
                                intent.putExtra("precioPizza", selectedPizza.getPrecio());
                                Log.d("DetallePizza3", "Precio recibidoZZZ: " + selectedPizza.getPrecio());
                                startActivity(intent);
                        }


                });


                return view;
        }


}
