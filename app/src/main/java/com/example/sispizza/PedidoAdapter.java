package com.example.sispizza;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PedidoAdapter extends BaseAdapter {

    Context context;
    List<Pedido> pedidoList;

    public PedidoAdapter(ProductManagementActivity productManagementActivity, List<Pedido> getData) {
        this.context = productManagementActivity;
        this.pedidoList = getData;
    }

    @Override
    public int getCount() {
        return pedidoList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tvNombre, tvPrecio, tvDescripcion, tvTotal;
        Pedido pedido = pedidoList.get(i);
        if (view == null) {
            view = View.inflate(context, R.layout.listview_pedido, null);
        }

        tvNombre = view.findViewById(R.id.tvLcliente);
        tvTotal = view.findViewById(R.id.tvLprecio);
        tvDescripcion = view.findViewById(R.id.tvLDescripcion);

        tvNombre.setText(pedido.getId()+"");
        tvTotal.setText(String.valueOf(pedido.getPrecioTotal()));
        tvDescripcion.setText(pedido.getDescripcion());
        return view;
    }
}
