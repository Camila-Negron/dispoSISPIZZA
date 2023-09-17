package com.example.sispizza.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.SecureRandom;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_database.db";
    private static final int DATABASE_VERSION = 1;

    // Definir la tabla de usuarios y su estructura
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, password TEXT, salt BLOB);";

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
    public long addUser(String username, String passwordHash, byte[] salt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", passwordHash);
        values.put("salt", salt);

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
    public byte[] getSaltByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT salt FROM users WHERE username = ?",
                new String[]{username});

        byte[] salt = null;
        if (cursor.moveToFirst()) {
            salt = cursor.getBlob(0);
        }

        cursor.close();
        db.close();

        return salt;
    }
}
