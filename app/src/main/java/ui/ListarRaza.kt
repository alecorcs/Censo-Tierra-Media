package ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.Toast
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R
import com.google.android.material.snackbar.Snackbar

class ListarRaza : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_raza)

        // Recuperamos el valor de la raza pasado por el Intent
        val raza = intent.getStringExtra("RAZA")
        Log.d("ListarRazaActivity", "Raza: $raza")

        // Recuperamos el listado de la Base de Datos
        val db = HabitantesSQLite(this)
        val listadoHabitantes = raza?.let { db.getListadoPorRaza(it) }
        // Asignamos los elementos de la UI
        val nombreRaza = findViewById<TextView>(R.id.txtNombreRaza)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasListadoRaza)
        val tableLayout = findViewById<LinearLayout>(R.id.layoutListadoProfesion)

        // Actualizamos los datos que necesitamos mostrar en el listado
        when (raza) {
            "Elfo" -> nombreRaza.text = resources.getString(R.string.elfos)
            "Enano" -> nombreRaza.text = resources.getString(R.string.enanos)
            "Hombre" -> nombreRaza.text = resources.getString(R.string.hombres)
            "Hobbit" -> nombreRaza.text = resources.getString(R.string.hobbits)
            else -> nombreRaza.text = "Raza desconocida: $raza"
        }

        // Por cada habitante, crea una nueva TableRow y añádela al LinearLayout
        if (listadoHabitantes != null && listadoHabitantes.size > 0) {
            listadoHabitantes.forEach { habitante ->
                val fila = TableRow(this@ListarRaza)
                // Llama a insertarTextoEnTabla para cada detalle del habitante
                insertarTextoEnTabla(habitante.id.toString(), fila)
                insertarTextoEnTabla(habitante.nombre, fila)
                insertarTextoEnTabla(habitante.apellidos, fila)
                insertarTextoEnTabla(habitante.edad.toString(), fila)
                insertarTextoEnTabla(habitante.ubicacion, fila)
                // Añade la fila al TableLayout después de añadir todos los TextViews
                tableLayout.addView(fila) // Asegúrate de tener una referencia a tu TableLayout
            }
        }
        //Muestra el número de habitantes por raza
        val numHabitantes: TextView = findViewById(R.id.txtNumRaza)
        val habitantesString = resources.getString(R.string.habitantes)
        val numHabRaza= raza?.let { db.getNumeroHabitantesPorRaza(it).toString() }
        numHabitantes.text = "$numHabRaza $habitantesString"

        //Borrar habitante
        val btnBorrarHabitante: Button = findViewById(R.id.btnBorrarHab)
        val txtID: EditText = findViewById(R.id.editID)

        btnBorrarHabitante.setOnClickListener {
            val id = txtID.text.toString()
            if (id.isNotEmpty()) {
                // Llamada al método para borrar el habitante
                db.borrarHabitante(id)
                //Actualizamos la vista
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Habitante borrado",
                    Snackbar.LENGTH_SHORT
                ).setDuration(1000).show()
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Añadimos el listener para volver a la Activity anterior
        btnAtras.setOnClickListener {
            // Cierra la Activity actual => cierra esta Activity y vuelva a la anterior en la pila
            finish()
        }
    }

    /**
     * Método para insertar texto en la tabla
     * @param texto cadena de texto a añadir en la fila
     * @param fila fila de la tabla donde añadiremos el texto
     */
    private fun insertarTextoEnTabla(texto: String, fila: TableRow) {
        val textView = TextView(this@ListarRaza).apply {
            text = texto
            // Ajusta estos layoutParams dependiendo de tu contenedor final
            layoutParams = TableRow.LayoutParams(
                0, // Usar 0 para el ancho en TableRow con layout_weight
                TableRow.LayoutParams.WRAP_CONTENT,
                1f // Peso para distribución equitativa en TableRow
            )
            textSize = 11f
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
        }

        fila.addView(textView)
    }

}