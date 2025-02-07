package ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R

class ListarProfesiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_profesiones)
        //TODO crear toda la lógica para que se muestre bien la información en el listado de profesiones
        var profesion = ""
        //Se  recupera el valor de la profesion por el Intent
        val profesionElegida = intent.getStringExtra("PROFESION")
        when (profesionElegida) {
            resources.getString(R.string.caballero) -> {
                profesion = "Caballero"
            }
            resources.getString(R.string.arquero) -> {
                profesion = "Arquero"
            }
            resources.getString(R.string.herrero) -> {
                profesion = "Herrero"
            }
            resources.getString(R.string.juglar) -> {
                profesion = "Juglar"
            }
            resources.getString(R.string.campesino) -> {
                profesion = "Campesino"
            }
            resources.getString(R.string.alquimista) -> {
                profesion = "Alquimista"
            }
            resources.getString(R.string.escriba) -> {
                profesion = "Escriba"
            }
            resources.getString(R.string.mercader) -> {
                profesion = "Mercader"
            }
            resources.getString(R.string.monje) -> {
                profesion = "Monje"
            }
            resources.getString(R.string.carpintero) ->
                profesion = "Carpintero"
        }
        Log.d("ListarProfesionActivity", "Profesion: $profesion")

        //Recuperamos el listado de la Base de Datos
        val db= HabitantesSQLite(this)
        val listadoHabitantes = profesion?.let { db.getListadoPorProfesion(it) }
        //Asignamos los elementos de la UI
        val nombreProfesion = findViewById<TextView>(R.id.txtNombreProfesion)
        val btnAtras= findViewById<ImageButton>(R.id.btnAtrasListadoProfesion)
        val tableLayout= findViewById<LinearLayout>(R.id.layoutListadoProfesion)

        //Acttualizamos  los datos  quenecesitamos mostrar en el listado
        when(profesionElegida){
            resources.getString(R.string.caballero) -> nombreProfesion.text = resources.getString(R.string.caballero)
            resources.getString(R.string.arquero) -> nombreProfesion.text = resources.getString(R.string.arquero)
            resources.getString(R.string.herrero) -> nombreProfesion.text = resources.getString(R.string.herrero)
            resources.getString(R.string.juglar) -> nombreProfesion.text = resources.getString(R.string.juglar)
            resources.getString(R.string.campesino) -> nombreProfesion.text = resources.getString(R.string.campesino)
            resources.getString(R.string.alquimista) -> nombreProfesion.text = resources.getString(R.string.alquimista)
            resources.getString(R.string.escriba)-> nombreProfesion.text = resources.getString(R.string.escriba)
            resources.getString(R.string.mercader) -> nombreProfesion.text = resources.getString(R.string.mercader)
            resources.getString(R.string.monje) -> nombreProfesion.text = resources.getString(R.string.monje)
            resources.getString(R.string.carpintero) -> nombreProfesion.text = resources.getString(R.string.carpintero)
            else -> nombreProfesion.text = "Profesion desconocida: $profesion"
        }

        //Creamos un nuevo TableRow y la añadimos al LinearLayout
        if (listadoHabitantes != null && listadoHabitantes.size > 0) {
            listadoHabitantes.forEach {  habitante ->
                val fila = TableRow(this@ListarProfesiones)
                //Llamamos a insertar texto en tabla
                insertarTextoEnTabla(habitante.id.toString(), fila)
                insertarTextoEnTabla(habitante.nombre, fila)
                insertarTextoEnTabla(habitante.apellidos, fila)
                when (habitante.raza) {
                    "Elfo" -> {
                        insertarTextoEnTabla(resources.getString(R.string.elfos), fila)
                    }
                    "Enano" -> {
                        insertarTextoEnTabla(resources.getString(R.string.enanos), fila)
                    }
                    "Hobbit" -> {
                        insertarTextoEnTabla(resources.getString(R.string.hobbits), fila)
                    }
                    "Hombre" ->
                        insertarTextoEnTabla(resources.getString(R.string.hombres), fila)
                }

                insertarTextoEnTabla(habitante.ubicacion, fila)

                tableLayout.addView(fila)

            }
        }
        //Funcion boton para vvolver al Activity anterior
        btnAtras.setOnClickListener{
            finish()
        }
    }

    /**
     * Método para insertar texto en la tabla
     * @param texto cadena de texto a añadir en la fila
     * @param fila fila de la tabla donde añadiremos el texto
     */
    private fun insertarTextoEnTabla(texto: String, fila: TableRow) {
        val textView = TextView(this@ListarProfesiones).apply {
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