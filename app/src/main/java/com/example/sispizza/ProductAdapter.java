package com.example.sispizza;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvProductName);
        TextView tvPrice = convertView.findViewById(R.id.tvProductPrice);

        tvName.setText(product.getName());
        tvPrice.setText(String.format("$%.2f", product.getPrice()));

        return convertView;
    }
}
