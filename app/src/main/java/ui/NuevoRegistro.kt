package ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R
import com.elsda.tierramedia_pac.habitantes.Censo
import com.elsda.tierramedia_pac.habitantes.HabitanteTierraMedia
import com.google.android.material.snackbar.Snackbar

class NuevoRegistro : AppCompatActivity() {

    /**
     * Método que nos devuelve un HabitanteTierraMedia aleatorio
     */
    private fun getNuevoHabitante(): HabitanteTierraMedia {
        val razas = arrayOf("Elfo", "Enano", "Hobbit", "Hombre")
        val razaElegida = razas.random()
        val habitanteTierraMedia = Censo().generarHabitante(razaElegida)

        return habitanteTierraMedia
    }

    /**
     * Método para obtener la cadena con el formato que necesitamos para el EditText

    private fun getTextResumenHabitante(
        nombre: String, apellidos: String, edad: Int,
        raza: String, ubicacion: String, profesion: String
    ): String {
        var cadena = "Nombre: $nombre\nApellidos: $apellidos\nEdad: ${edad.toString()}\n"
        cadena += "Raza: $raza\nUbicación: $ubicacion\nProfesion: $profesion"

        return cadena;
    }
    */
    /**
     * Función que muestra los datos de un Nuevo Habitante de la Tierra Media
     */
    private fun verNuevoHabitante(habitante: HabitanteTierraMedia) {
        // Encuentra los TextViews de la columna derecha
        val nombreCambio = findViewById<TextView>(R.id.txtnombreCambio)
        val apellidoCambio = findViewById<TextView>(R.id.txtapellidoCambio)
        val edadCambio = findViewById<TextView>(R.id.txtedadCambio)
        val razaCambio = findViewById<TextView>(R.id.txtrazaCambio)
        val ubicacionCambio = findViewById<TextView>(R.id.txtubicacionCambio)
        val profesionCambio = findViewById<TextView>(R.id.txtprofesionCambio)

        // Establece el texto de cada TextView basado en los atributos del habitante
        nombreCambio.text = habitante.nombre
        apellidoCambio.text = habitante.apellidos
        edadCambio.text = habitante.edad.toString()
        when (habitante.raza) {
            "Elfo" -> {
                razaCambio.text=resources.getString(R.string.elfos)
            }
            "Enano" -> {
                razaCambio.text=resources.getString(R.string.enanos)
            }
            "Hobbit" -> {
                razaCambio.text=resources.getString(R.string.hobbits)
            }
            "Hombre" -> razaCambio.text=resources.getString(R.string.hombres)
        }

        ubicacionCambio.text = habitante.ubicacion

        when (habitante.profesion) {
            "Caballero" -> {
                profesionCambio.text=resources.getString(R.string.caballero)
            }
            "Arquero" -> {
                profesionCambio.text=resources.getString(R.string.arquero)
            }
            "Herrero" -> {
                profesionCambio.text=resources.getString(R.string.herrero)
            }
            "Juglar" -> {
                profesionCambio.text=resources.getString(R.string.juglar)
            }
            "Campesino" -> {
                profesionCambio.text=resources.getString(R.string.campesino)
            }
            "Alquimista" -> {
                profesionCambio.text=resources.getString(R.string.alquimista)
            }
            "Escriba" -> {
                profesionCambio.text=resources.getString(R.string.escriba)
            }
            "Mercader" -> {
                profesionCambio.text=resources.getString(R.string.mercader)
            }
            "Monje" -> {
                profesionCambio.text=resources.getString(R.string.monje)
            }
            "Carpintero" -> {
                profesionCambio.text=resources.getString(R.string.carpintero)
            }
        }
        profesionCambio.text = habitante.profesion
    }

    /**
     * Método para cuando se crea la Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_registro)

        // Buscamos los botones de esta
        val btnActualizarDatos = findViewById<Button>(R.id.btnActualizarDatos)
        val btnInsertarRegistro = findViewById<Button>(R.id.btnInsertarEnCenso)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasNuevoRegistro)

        // Variable para el uso de la base de datos
        val dbHabitantes = HabitantesSQLite(this)

        // Tenemos los datos de un nuevo habitante al crear la Activity => lo mostramos
        // OJO => usamos var porque vamos a necesitar cambiar la variable en ActualizarDatos
        var nuevoHabitante = getNuevoHabitante()
        verNuevoHabitante(nuevoHabitante)

        // Añadimos el listener para volver a la Activity anterior
        btnAtras.setOnClickListener {
            // Cierra la Activity actual => cierra esta Activity y vuelva a la anterior en la pila
            finish()
        }

        /**
         * Si actualizamos los datos => tenemos un nuevo habitante
         */
        btnActualizarDatos.setOnClickListener {
            nuevoHabitante = getNuevoHabitante()
            verNuevoHabitante(nuevoHabitante)
        }

        /**
         * Insertamos el nuevo habitante en la base de datos
         */
        btnInsertarRegistro.setOnClickListener {
            // Insertar nuevo habitante
            val nuevoId = dbHabitantes.insertarHabitante(nuevoHabitante)
            nuevoHabitante.id = nuevoId.toInt()
            // Mostramos la información de insertar nuevo habitante
            Snackbar.make(
                findViewById(android.R.id.content),
                "Nuevo habitante registrado",
                Snackbar.LENGTH_SHORT
            ).setDuration(1000).show() // 1000 ms = 1 segundo
            // Actualizamos los datos con un nuevo habitante
            nuevoHabitante = getNuevoHabitante()
            verNuevoHabitante(nuevoHabitante)
        }
    }
}