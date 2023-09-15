package com.example.sispizza.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Definir la tabla de usuarios y su estructura
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, password TEXT);";

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
    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long newRowId = db.insert("users", null, values);
        db.close();

        return newRowId;
    }

    // Función para autenticar un usuario
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                new String[]{username, password});

        boolean success = cursor.moveToFirst();
        cursor.close();
        db.close();

        return success;
    }
}
