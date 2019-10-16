package com.example.calculadora2

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.content.res.Configuration
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    //variables de las pantallas:
    private lateinit var numero: TextView
    private lateinit var operacion: TextView
    private lateinit var resultado: TextView
    var memoria: Double=0.0

    //Posicion horizontsl, variables:
    private lateinit var Bbuscar: Button
    private lateinit var Ebuscar: EditText
    //private lateinit var web: WebView
    private var web: WebView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT) {
            numero = findViewById<TextView>(R.id.pantalla)
            operacion = findViewById<TextView>(R.id.pantallaOp)
            resultado = findViewById<TextView>(R.id.pantallaResul)
        }
        else {
            //Posicion horizontsl, variables:
            //Bbuscar = findViewById<Button>(R.id.btBuscar)
            //Ebuscar = findViewById<EditText>(R.id.etBuscar)
            //web = findViewById<WebView>(R.id.wvNavegador)
           // web.loadUrl("https://www.google.es")
            browser()

        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

    fun browser () {
        web = findViewById<WebView>(R.id.wvNavegador)
        web!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        web!!.loadUrl(etBuscar.text.toString())
        web!!.loadUrl("https://www.google.es")
    }

    fun buscar(view: View){
        var caract = etBuscar.text
        if(!caract.equals("")){
            var ultCaract = caract.substring(caract.length-3,caract.length)
            if(ultCaract.equals(".es")){
                web!!.loadUrl(""+caract)
            }else if(ultCaract.equals("com")){
                web!!.loadUrl(""+caract)
            }else{
                web!!.loadUrl("https://www.google.com/search?q="+caract+"&oq="+caract+"&aqs=chrome..69i57j69i60.820j0j7&sourceid=chrome&ie=UTF-8")
            }
        }
        closeKeyboard()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val ims = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            ims.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun pulsaNumero(view: View) {
        val boton = view as Button
        var numString: String = boton.text.toString()
        numero.setText(numero.text.toString() + numString)
    }


    fun operar(view: View) {
        var nuevaOp = view as Button
        var a: Double = 0.0
        var b: Double = 0.0
        var c: Double = 0.0
        var r: String
        var n: String
        var pop: String

        var opString: String = nuevaOp.text.toString()
        r = resultado.text.toString().trim()
        n = numero.text.toString().trim()
        pop = operacion.text.toString()


        if (r.equals("")) {
            a = 0.0
        } else {
            a = r.toString().toDouble()
        }

        if (n.equals("")) {
            b = 0.0
        } else {
            b = n.toString().toDouble()
        }

        c = a
        if(opString.equals("=")){
            opString=""
        }
        when (pop) {
            "+" -> {
                c = a + b
                operacion.text=opString
            }
            "-" -> {
                c = a - b
                operacion.text=opString
            }
            "x" -> {
                c = a * b
                operacion.text=opString
            }
            "/" -> {
                c = a / b
                operacion.text=opString
            }

            else ->{
                if(a==0.0 && b!=0.0) {
                    c = b
                }
                operacion.text=opString
            }
        }
        resultado.text=c.toString()
        numero.text=""

    }

    fun todoLimpio(view: View) {
        pantalla.setText("")
        pantallaOp.setText("")
        pantallaResul.setText("")
    }

    fun sumarMemoria(view: View) {
        if(!pantallaResul.equals("")) {
            var n: Double = resultado.text.toString().toDouble()
            memoria = memoria + n
        }
    }

    fun ponerMemoria(view: View) {
        numero.text=memoria.toString()
    }

    fun memoriaACero(view: View){
        memoria = 0.0
    }

    fun borrarUno(view: View) {
        var pantAux = pantalla.text
        if (!pantAux.equals("")) {
            var pantAux = pantAux.substring(0,pantAux.length-1)
            pantalla.setText(""+pantAux)
        }
    }

}
