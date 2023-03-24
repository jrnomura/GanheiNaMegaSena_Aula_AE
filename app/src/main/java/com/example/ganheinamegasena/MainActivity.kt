package com.example.ganheinamegasena

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    //variavel da classe (de campo) usar em vários lugares, não esta recebendo nada
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //setando os campos
        val editNumber: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerator: Button = findViewById(R.id.btn_generator)

        //recebendo para começar gravar os números, criando banco de dados
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        //verificando se tem números do ultimo sorteio, para fazer a lógica
        val result = prefs.getString("result", null)
        //if -> let
        /*if (result != null) {
            txtResult.text = "Ultima Aposta: $result"
        }*/
        //let - igual ao de cima if
        result?.let { txtResult.text = "Ultima Aposta: $it" }

        //colocando evento de click no botão
        btnGenerator.setOnClickListener {
            val text = editNumber.text.toString()//converte Edittext para String

            numberGenerator(text, txtResult)//função que vai gerar os números
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        // validar se campo texto esta vazio
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }


        val qtd = text.toInt() //converte String para Int
        //validar campo informando entre 6 e 15
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        //Logica de Programção
        val numbers = mutableSetOf<Int>()//variavel que armazena em uma lista de numeros sem repetir
        val randon = Random()//objeto que gera números aleatórios

        //loop infinito até que usar o break
        while (true) {
            val number =
                randon.nextInt(60)//variavel recebe objeto randon e gera números de 0 até 59
            numbers.add(number + 1)//pega lista gerada e colocar variavel que gerou os números, somando sempre + 1

            //se tamanho da lista for igual a quantidade de números digitados, para o while
            if (numbers.size == qtd) {
                break
            }
        }

        //setando os números da lista no txtResult, para o usuario ver
        //usando função que pode transformar uma lista de Int em String e colocar uma String para separar
        txtResult.text = numbers.joinToString(" - ")

        //gravar os ultimos números gerados
        /*val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()*/

        //pode ser assim
        prefs.edit().apply(){
            putString("result", txtResult.text.toString())
            apply()
        }

    }

}


