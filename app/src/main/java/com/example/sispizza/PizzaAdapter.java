package com.example.sispizza;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private OnDetalleClickListener onDetalleClickListener;

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
        // Muestra el precio en el TextView correspondiente
        holder.textPrice.setText(String.format("Bs. %.2f", pizza.getPrecio()));

        holder.btDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (onDetalleClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    onDetalleClickListener.onDetalleClick(adapterPosition);

                    // Verifica la configuración del Intent
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, DetallePizza1.class);
                    intent.putExtra("imagenPizza", pizza.getImagen());
                    intent.putExtra("descripcionPizza", pizza.getDescripcion());
                    //intent.putExtra("precioPizza", pizza.getPrecio()); // Pasa el precio
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    /*public int getImagenId(int position) {
        return pizzaList.get(position).getImagen();
    }

    public double getPrecio(int position) {
        return pizzaList.get(position).getPrecio();
    }*/

    public class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView descriptionTextView;
        TextView textPrice;
        Button btDetalle;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            btDetalle = itemView.findViewById(R.id.btDetalle);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }

    public interface OnDetalleClickListener {
        void onDetalleClick(int position);
    }

    public void setOnDetalleClickListener(OnDetalleClickListener listener) {
        onDetalleClickListener = listener;
    }
}
