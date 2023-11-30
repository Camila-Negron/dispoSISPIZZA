package com.example.sispizza;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sispizza.database.DatabaseHelper;
import com.example.sispizza.Product;


import java.util.List;

public class ProductManagementActivity extends Activity {
    private DatabaseHelper db;
    private List<Product> productList;
    private ProductAdapter adapter;
    private ListView listViewProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        db = new DatabaseHelper(this);
        listViewProducts = findViewById(R.id.listViewProducts);

        loadProducts();
    }

    private void loadProducts() {
        productList = db.getAllProducts();
        adapter = new ProductAdapter(this, productList);
        listViewProducts.setAdapter(adapter);
    }

    // Método para añadir un producto (puede ser llamado desde un botón, por ejemplo)
    private void addProduct(String name, double price) {
        Product newProduct = new Product(name, price);
        db.addProduct(newProduct);
        loadProducts();  // Recargar la lista de productos
    }

    // Método para actualizar un producto
    private void updateProduct(Product product) {
        db.updateProduct(product);
        loadProducts();  // Recargar la lista de productos
    }

    // Método para eliminar un producto
    private void deleteProduct(Product product) {
        db.deleteProduct(product);
        loadProducts();  // Recargar la lista de productos
    }
}
