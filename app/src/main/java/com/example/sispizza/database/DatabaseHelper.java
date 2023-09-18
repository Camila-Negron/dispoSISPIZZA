package com.example.sispizza.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 3; // Actualiza la versión de la base de datos

    // Definir la tabla de usuarios y su estructura
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, password TEXT, salt TEXT, creation_date DATETIME);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios cuando se crea la base de datos
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Manejar la actualización de la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS users");
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

    // Función para autenticar un usuario
    public boolean authenticateUser(String username, String passwordHash) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                new String[]{username, passwordHash});

        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
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
}
