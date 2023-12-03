package com.example.sispizza.database;

import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.example.sispizza.Pedido;
import com.example.sispizza.Product;


import com.example.sispizza.ChangePasswordActivity;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 6; // Actualiza la versión de la base de datos

    // Definir la tabla de usuarios y su estructura
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, password TEXT, salt TEXT, creation_date DATETIME);";

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Definir la tabla de pedido y su estructura
    private static final String CREATE_TABLE_PEDIDO =
            "CREATE TABLE IF NOT EXISTS pedido (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cliente TEXT, descripcion TEXT, cantidad INTEGER, precio_total REAL, salsa TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla de usuarios en la base de datos
        db.execSQL(CREATE_TABLE_USERS);

        // Crea la tabla de pedido en la base de datos
        db.execSQL(CREATE_TABLE_PEDIDO);

        //tabla productos
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar la actualización de la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS pedido");
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    // Función para agregar un nuevo usuario a la base de datos
    public long addUser(String username, String passwordHash, String salt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", passwordHash);
        values.put("salt", salt);
        values.put("creation_date", getCurrentDateTime()); // Agregar la fecha actual

        long newRowId = db.insert("users", null, values);
        db.close();

        return newRowId;
    }

    public long addPedido(String cliente,String descripcion, int cantidad, double precioTotal, String salsa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cliente", cliente);
        values.put("descripcion", descripcion);
        values.put("cantidad", cantidad);
        values.put("precio_total", precioTotal);
        values.put("salsa", salsa);

        //long newRowId = db.insert(TABLE_NAME, null, values);
        //db.close();

        //return newRowId;

        long newRowId = db.insert("pedido", null, values);
        db.close();

        return newRowId;
    }

    // Método para obtener la lista de pedidos desde la base de datos
    public ArrayList<String> obtenerListaPedidos() {
        ArrayList<String> listaPedidos = new ArrayList<>();

        // Abre una conexión de lectura a la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta la base de datos para obtener los pedidos
        String query = "SELECT * FROM pedido";
        Cursor cursor = db.rawQuery(query, null);

        // Obtiene los índices de las columnas
        int clienteIndex = cursor.getColumnIndex("cliente");
        int descripcionIndex = cursor.getColumnIndex("descripcion");
        int cantidadIndex = cursor.getColumnIndex("cantidad");
        int precioTotalIndex = cursor.getColumnIndex("precio_total");
        int salsaIndex = cursor.getColumnIndex("salsa");

        // Itera a través del resultado de la consulta y agrega los pedidos a la lista
        if (cursor.moveToFirst()) {
            do {
                // Utiliza los índices para obtener los valores de las columnas
                String cliente = cursor.getString(clienteIndex);
                String descripcion = cursor.getString(descripcionIndex);
                int cantidad = cursor.getInt(cantidadIndex);
                double precioTotal = cursor.getDouble(precioTotalIndex);
                String salsa = cursor.getString(salsaIndex);

                // Construye una cadena que represente el pedido y agrégala a la lista
                String pedidoString = "Cliente: " + cliente + ", Descripción: " + descripcion +
                        ", Cantidad: " + cantidad + ", Precio Total: " + precioTotal +
                        ", Salsa: " + salsa;
                listaPedidos.add(pedidoString);
            } while (cursor.moveToNext());
        }

        // Cierra el cursor y la conexión de la base de datos
        cursor.close();
        db.close();

        // Devuelve la lista de pedidos
        return listaPedidos;
    }




    // Función para autenticar un usuario
    public boolean authenticateUser(String username, String passwordHash, Context context) {
        if (isPasswordChangeRequired(username)) {
            // Redirigir al usuario a la ventana de cambio de contraseña
            Intent intent = new Intent(context, ChangePasswordActivity.class);
            intent.putExtra("username", username); // Pasar el nombre de usuario a la nueva actividad
            context.startActivity(intent);
            return false; // Indicar que el usuario debe cambiar la contraseña
        } else {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                    new String[]{username, passwordHash});

            boolean success = cursor.moveToFirst();
            cursor.close();
            db.close();

            return success;
        }
    }


    // Función para obtener el salt de un usuario por su nombre de usuario
    public String getSaltByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT salt FROM users WHERE username = ?",
                new String[]{username});

        String salt = null;
        if (cursor.moveToFirst()) {
            salt = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return salt;
    }

    // Función para obtener la fecha de creación formateada como "dd/MM/yyyy HH:mm"
    public String getFormattedCreationDate(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT creation_date FROM users WHERE username = ?",
                new String[]{username});

        long creationDate = 0;
        if (cursor.moveToFirst()) {
            creationDate = cursor.getLong(0);
        }

        cursor.close();
        db.close();

        // Crear un objeto Date a partir del valor en milisegundos
        Date date = new Date(creationDate);

        // Crear un formato para la fecha y hora deseada
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        // Formatear la fecha y hora
        return dateFormat.format(date);
    }

    private String getCurrentDateTime() {
        TimeZone boliviaTimeZone = TimeZone.getTimeZone("America/La_Paz");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        dateFormat.setTimeZone(boliviaTimeZone);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean isPasswordChangeRequired(String username) {
        return false;
        /*SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT creation_date FROM users WHERE username = ?",
                new String[]{username});

        if (cursor.moveToFirst()) {
            long creationDate = cursor.getLong(0);
            cursor.close();
            db.close();

            // Calcular la diferencia en milisegundos entre la fecha actual y la creación de la contraseña
            long currentTime = System.currentTimeMillis();
            long difference = currentTime - creationDate;

            // Definir el límite de tres meses en milisegundos (90 días * 24 horas * 60 minutos * 60 segundos * 1000)
            long threeMonthsInMillis = 90 * 24 * 60 * 60 * 1000;

            return difference > threeMonthsInMillis;
        } else {
            cursor.close();
            db.close();
            return false; // El usuario no existe en la base de datos
        }*/
    }


    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PRICE + " REAL)";





    // Métodos para insertar, obtener, actualizar y eliminar productos...

    public void addProduct(Product product) {
        // Registro de intento de añadir producto
        Log.i("DatabaseHelper", "Intentando añadir producto: " + product.getName());

        // Validación de los datos del producto
        if (!isValidProduct(product)) {
            Log.e("DatabaseHelper", "Datos del producto inválidos: " + product.getName());
            return;
        }

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, product.getName());
            values.put(COLUMN_PRICE, product.getPrice());

            long result = db.insert(TABLE_PRODUCTS, null, values);

            if (result == -1) {
                Log.e("DatabaseHelper", "Error al añadir producto: " + product.getName());
            } else {
                Log.i("DatabaseHelper", "Producto añadido exitosamente: " + product.getName());
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Excepción al añadir producto: " + product.getName(), e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    private boolean isValidProduct(Product product) {
        return product != null && isValidName(product.getName()) && isValidPrice(product.getPrice());
    }

    private boolean isValidName(String name) {
        // Implementa tu lógica de validación aquí
        return name != null && !name.trim().isEmpty();
    }

    private boolean isValidPrice(double price) {
        // Implementa tu lógica de validación aquí
        return price > 0;
    }


    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_PRICE}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getString(1), cursor.getDouble(2));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }


    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());

        // Actualizar fila
        return db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
    }


    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> listaPedidos = new ArrayList<>();

        // Abre una conexión de lectura a la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta la base de datos para obtener todos los pedidos
        String query = "SELECT * FROM pedido";
        Cursor cursor = db.rawQuery(query, null);

        // Obtiene los índices de las columnas
        int idIndex = cursor.getColumnIndex("id");
        int clienteIndex = cursor.getColumnIndex("cliente");
        int descripcionIndex = cursor.getColumnIndex("descripcion");
        int cantidadIndex = cursor.getColumnIndex("cantidad");
        int precioTotalIndex = cursor.getColumnIndex("precio_total");
        int salsaIndex = cursor.getColumnIndex("salsa");

        // Itera a través del resultado de la consulta y agrega los pedidos a la lista
        if (cursor.moveToFirst()) {
            do {
                // Utiliza los índices para obtener los valores de las columnas
                int id = cursor.getInt(idIndex);
                String cliente = cursor.getString(clienteIndex);
                String descripcion = cursor.getString(descripcionIndex);
                int cantidad = cursor.getInt(cantidadIndex);
                double precioTotal = cursor.getDouble(precioTotalIndex);
                String salsa = cursor.getString(salsaIndex);

                // Crea un objeto Pedido y agrégalo a la lista
                Pedido pedido = new Pedido(id, cliente, descripcion, cantidad, precioTotal, salsa);
                listaPedidos.add(pedido);
            } while (cursor.moveToNext());
        }

        // Cierra el cursor y la conexión de la base de datos
        cursor.close();
        db.close();

        // Devuelve la lista de pedidos
        return listaPedidos;
    }



}
