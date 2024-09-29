package com.puj.proyectoensenarte.dictionary.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.puj.proyectoensenarte.R

// Database Helper class to manage database creation and version management
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_CATEGORIA)
        db?.execSQL(CREATE_TABLE_PALABRA)
    }

    private fun vaciarBaseDeDatos(db: SQLiteDatabase?) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PALABRA")
        Log.d("DatabaseHelper", "Base de datos reiniciada: Tablas eliminadas")
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)

        vaciarBaseDeDatos(db)

        onCreate(db)
    }

    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIA")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PALABRA")
        onCreate(db)
    }

    // Función para insertar las categorías iniciales en la base de datos




    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "miBaseDeDatos.db"

        // Table names
        const val TABLE_CATEGORIA = "categoria"
        const val TABLE_PALABRA = "palabra"

        // Create table for 'categoria'
        private const val CREATE_TABLE_CATEGORIA = """
            CREATE TABLE $TABLE_CATEGORIA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                imagen TEXT
            );
        """

        // Create table for 'palabra'
        private const val CREATE_TABLE_PALABRA = """
            CREATE TABLE $TABLE_PALABRA (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                video1 TEXT,
                video2 TEXT,
                video3 TEXT,
                definicion TEXT,
                ejemplo TEXT,
                categoria_id INTEGER,
                FOREIGN KEY(categoria_id) REFERENCES $TABLE_CATEGORIA(id) ON DELETE CASCADE
            );
        """
    }
}
