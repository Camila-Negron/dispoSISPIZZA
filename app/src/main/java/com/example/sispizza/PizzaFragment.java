package com.example.sispizza;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                pizzaList.add(new Pizza("Pizza Margarita", R.drawable.boliviana, "Tomate, mozzarella y albahaca."));
                pizzaList.add(new Pizza("Pizza Pepperoni", R.drawable.champi, "Pepperoni y queso fundido."));
                pizzaList.add(new Pizza("Pizza Vegetariana", R.drawable.hawaiana, "Vegetales frescos y queso."));

                adapter = new PizzaAdapter(pizzaList);
                recyclerView.setAdapter(adapter);

                return view;
        }
}
