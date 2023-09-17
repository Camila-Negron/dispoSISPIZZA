package com.example.sispizza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
                pizzaList.add(new Pizza("Pizza Boliviana", R.drawable.boliviana, "Locoto, Tomate, mozzarella y albahaca."));
                pizzaList.add(new Pizza("Pizza Champiñon", R.drawable.champi, "Champiñom y queso fundido."));
                pizzaList.add(new Pizza("Pizza Hawaiana", R.drawable.hawaiana, "Piña, Vegetales frescos y queso."));
                pizzaList.add(new Pizza("Pizza Margarita", R.drawable.camaron, "Camaron, Tomate, mozzarella y albahaca."));
                pizzaList.add(new Pizza("Pizza Pepperoni", R.drawable.mariscos, "Pepperoni y queso fundido."));
                pizzaList.add(new Pizza("Pizza Vegetariana", R.drawable.napolitana, "Vegetales frescos y queso."));

                adapter = new PizzaAdapter( pizzaList);

                adapter.setOnDetalleClickListener(new PizzaAdapter.OnDetalleClickListener() {
                        @Override
                        public void onDetalleClick(int position) {
                                // Manejar el clic del botón "Detalles" aquí
                                Toast.makeText(getActivity(), "Detalles de la pizza en la posición " + position, Toast.LENGTH_SHORT).show();
                                if (position == 0) {
                                        // Crea un intent para abrir la actividad de detalles
                                        Intent intent = new Intent(getActivity(), DetallePizza1.class);
                                        startActivity(intent);
                                }
                        }
                });


                recyclerView.setAdapter(adapter);

                return view;
        }


}
