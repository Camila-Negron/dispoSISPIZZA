package com.example.sispizza;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private List<Pizza> pizzaList;

    public PizzaAdapter(List<Pizza> pizzaList) {
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.imageView.setImageResource(pizza.getImagen());
        holder.descriptionTextView.setText(pizza.getDescripcion());

        holder.btDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onDetalleClick(adapterPosition);

                    // Verifica la configuración del Intent
                    Intent intent = new Intent(v.getContext(), DetallePizza1.class);
                    intent.putExtra("imagenPizza", pizza.getImagen());
                    intent.putExtra("descripcionPizza", pizza.getDescripcion());
                    v.getContext().startActivity(intent);
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView descriptionTextView;
        Button btDetalle;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            btDetalle = itemView.findViewById(R.id.btDetalle);
        }
    }


    public interface OnDetalleClickListener {
        void onDetalleClick(int position);
    }

    private OnDetalleClickListener mListener;

    public void setOnDetalleClickListener(OnDetalleClickListener listener) {
        mListener = listener;
    }





}
