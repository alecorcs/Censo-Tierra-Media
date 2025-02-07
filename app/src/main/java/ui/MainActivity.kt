package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.elsda.tierramedia_pac.R
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TierraMedia_Pac)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Spinner de seleccion de idiomas
        val spinner: Spinner = findViewById(R.id.spIdioma)
        // Buscamos el botón censo
        val btnCenso = findViewById<Button>(R.id.btnCenso)

        //Lista de idiomas
        val idiomas = listOf("Elige idioma", "Español", "Inglés")

        // Crear un ArrayAdapter usando el array de strings y un layout de spinner predeterminado
        val adapter = ArrayAdapter(this, R.layout.spinner_item, idiomas)
        // Especificar el layout a usar cuando la lista de opciones aparezca
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Aplicar el adapter al spinner
        spinner.adapter = adapter

        //Cuando seleccionamos idioma cambiamos  el mismo de la app
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ){
                val language= if (position == 1) "es" else "en"
                //Mientras la posicion seleccionada sea distinta  de 0 se aplica el  idioma
                if (position!=0){
                    setLocale(language)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // No se requiere acción si no se selecciona nada
            }
        }

        // Establece el OnClickListener para el botón Censo

        btnCenso.setOnClickListener {
            // Crea un Intent para iniciar EligeOpcion
            val intent = Intent(this, EligeOpcion::class.java)
            startActivity(intent)
        }

    }

    //Funcion que aplica el cambio de  idioma a la app
    fun setLocale(lang: String?) {
        val myLocale = lang?.let { Locale(it) }
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
