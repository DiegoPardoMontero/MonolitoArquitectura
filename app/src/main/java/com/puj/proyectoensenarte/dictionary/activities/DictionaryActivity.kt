package com.puj.proyectoensenarte.dictionary.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.puj.proyectoensenarte.databinding.ActivityDictionaryBinding
import com.puj.proyectoensenarte.dictionary.adapters.CategoryAdapter
import com.puj.proyectoensenarte.dictionary.data.DatabaseHelper
import com.puj.proyectoensenarte.dictionary.data.DatabaseHelper.Companion.TABLE_PALABRA

class DictionaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDictionaryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var dbHelper : DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DatabaseHelper(this)
        setupSearchBar()
        setupCategoriesRecyclerView()
        insertarCategoriasIniciales(dbHelper.writableDatabase)
        insertarPalabrasIniciales(dbHelper.writableDatabase, this)
        loadCategories()
        mostrarCategorias(dbHelper.readableDatabase)
        mostrarPalabras(dbHelper.readableDatabase)
    }

    private fun setupSearchBar() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding.ivSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun setupCategoriesRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            navigateToDetallePorCategoria(category.name, category.imageUrl)
        }
        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(this@DictionaryActivity, 3)
        }
    }

    private fun loadCategories() {
        categoryAdapter.loadCategoriesFromDatabase(this, dbHelper.readableDatabase)
    }

    private fun performSearch() {
        var searchQuery = binding.etSearch.text.toString().trim()
        searchQuery = searchQuery.lowercase()
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            return
        }

        hideKeyboard()
    }

    private fun navigateToDetallePorCategoria(categoria: String, imageUrl: String) {
        val intent = Intent(this, DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
            putExtra("CATEGORIA_IMAGE_URL", imageUrl)
        }
        startActivity(intent)
    }

    private fun navigateToResultadoBusquedaCategoria(categoria: String) {
        var categoria = capitalizeFirstLetter(categoria)
        val intent = Intent(this, ResultadoBusquedaCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
        }
        startActivity(intent)
    }

    private fun navigateToError404() {
        val intent = Intent(this, Error404Activity::class.java)
        startActivity(intent)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }


    private fun insertarCategoriasIniciales(db: SQLiteDatabase?) {
        val categorias = listOf(
            arrayOf("Inteligencia", "inteligencia"),
            arrayOf("Alimentacion", "alimentacion"),
            arrayOf("Cantidad", "cantidad"),
            arrayOf("Fisiologia", "fisiologia"),
            arrayOf("Espacio", "espacio"),
            arrayOf("Tiempo", "tiempo"),
            arrayOf("Salud", "salud")
        )

        for (categoria in categorias) {
            val insertCategoriaQuery = """
            INSERT OR IGNORE INTO ${DatabaseHelper.TABLE_CATEGORIA} (nombre, imagen)
            VALUES ('${categoria[0]}', '${categoria[1]}')
        """.trimIndent()
            db?.execSQL(insertCategoriaQuery)
        }
    }

    private fun insertarPalabrasIniciales(db: SQLiteDatabase?, context: Context) {
        val palabras = listOf(
            arrayOf("aguacate", "2"),
            arrayOf("ayer", "5"),
            arrayOf("cantidad", "2")
        )

        for (palabra in palabras) {
            val nombrePalabra = palabra[0]
            val categoriaId = palabra[1]

            val videoPalabra = "android.resource://${context.packageName}/raw/${nombrePalabra}_palabra"
            val videoDefinicion = "android.resource://${context.packageName}/raw/${nombrePalabra}_definicion"
            val videoEjemplo = "android.resource://${context.packageName}/raw/${nombrePalabra}_ejemplo"

            val insertPalabraQuery = """
            INSERT INTO $TABLE_PALABRA (nombre, video1, video2, video3, definicion, ejemplo, categoria_id)
            VALUES ('$nombrePalabra', '$videoPalabra', '$videoDefinicion', '$videoEjemplo', 
                    'Definición de $nombrePalabra', 'Ejemplo de $nombrePalabra', $categoriaId)
        """.trimIndent()
            db?.execSQL(insertPalabraQuery)
        }
    }



    fun mostrarCategorias(db: SQLiteDatabase) {
        val cursor = db.rawQuery("SELECT id, nombre, imagen FROM categoria", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

                Log.d("DatabaseContent", "Categoría ID: $id, Nombre: $nombre, Imagen: $imagen")
            } while (cursor.moveToNext())
        } else {
            Log.d("DatabaseContent", "No hay categorías disponibles.")
        }

        cursor.close()
    }

    fun mostrarPalabras(db: SQLiteDatabase) {
        val cursor = db.rawQuery("SELECT id, nombre, video1, video2, video3, definicion, ejemplo, categoria_id FROM palabra", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val video1 = cursor.getString(cursor.getColumnIndexOrThrow("video1"))
                val video2 = cursor.getString(cursor.getColumnIndexOrThrow("video2"))
                val video3 = cursor.getString(cursor.getColumnIndexOrThrow("video3"))
                val definicion = cursor.getString(cursor.getColumnIndexOrThrow("definicion"))
                val ejemplo = cursor.getString(cursor.getColumnIndexOrThrow("ejemplo"))
                val categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow("categoria_id"))

                Log.d("DatabaseContent", "Palabra ID: $id, Nombre: $nombre, Video1: $video1, Video2: $video2, Video3: $video3, Definición: $definicion, Ejemplo: $ejemplo, Categoría ID: $categoriaId")
            } while (cursor.moveToNext())
        } else {
            Log.d("DatabaseContent", "No hay palabras disponibles.")
        }

        cursor.close()
    }


    private fun capitalizeFirstLetter(input: String): String {
        return if (input.isNotEmpty()) {
            input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        } else {
            input
        }
    }
}