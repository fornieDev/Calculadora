package com.forniedev.intcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.forniedev.intcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private var number1Int : Float = 0f
    private var number2Int : Float = 0f
    private var currentOperation : String = ""
    private var number1Dec = false
    private var number2Dec = false
    private var number1Float = 0.0f
    private var number2Float = 0.0f
    private var number1IsNegative = false
    private var number2IsNegative = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button0.setOnClickListener {
            addDigit(binding.button0.text.toString())
        }
        binding.button1.setOnClickListener {
            addDigit(binding.button1.text.toString())
        }
        binding.button2.setOnClickListener {
            addDigit(binding.button2.text.toString())
        }
        binding.button3.setOnClickListener {
            addDigit(binding.button3.text.toString())
        }
        binding.button4.setOnClickListener {
            addDigit(binding.button4.text.toString())
        }
        binding.button5.setOnClickListener {
            addDigit(binding.button5.text.toString())
        }
        binding.button6.setOnClickListener {
            addDigit(binding.button6.text.toString())
        }
        binding.button7.setOnClickListener {
            addDigit(binding.button7.text.toString())
        }
        binding.button8.setOnClickListener {
            addDigit(binding.button8.text.toString())
        }
        binding.button9.setOnClickListener {
            addDigit(binding.button9.text.toString())
        }

        binding.buttonMas.setOnClickListener {
            operate(binding.buttonMas.text.toString())
        }
        binding.buttonMenos.setOnClickListener {
            operate(binding.buttonMenos.text.toString())
        }
        binding.buttonMulti.setOnClickListener {
            operate(binding.buttonMulti.text.toString())
        }
        binding.buttonDiv.setOnClickListener {
            operate("/")
        }

        binding.buttonEqual.setOnClickListener {
            solve()
        }
        binding.buttonDelete.setOnClickListener {
            delete()
        }

        binding.buttonPoint.setOnClickListener {
            if (currentOperation != ""){
                if (number2Dec != true){
                    number2Dec = true
                    if (number1Dec != false){
                        binding.tvOperation.text = "$number1Int$currentOperation${number2Int.toInt()}."
                    }else{
                        binding.tvOperation.text = "${number1Int.toInt()}$currentOperation${number2Int.toInt()}."
                    }
                }
                else{ Toast.makeText(this,"Error,ya habías pulsado el punto anteriormente.", Toast.LENGTH_SHORT).show() }
            }else{
                if (number1Dec != true){
                    number1Dec = true
                    binding.tvOperation.text = "${number1Int.toInt()}.$currentOperation"
                }
                else{ Toast.makeText(this,"Error,ya habías pulsado el punto anteriormente.", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun addDigit(digit : String){
        val digit = digit.toInt()
        if (currentOperation != ""){
            addDigitToNumber2(digit)
        }
        else{
            addDigitToNumber1(digit)
        }
    }

    private fun addDigitToNumber2(digit : Int){
        println("Adding digit to number2")
        if (number2Dec != true){
            if (number2IsNegative){
                number2Int = -(number2Int*10 + digit)
                number2IsNegative = false
            }else{
                if (number2Int < 0){
                    number2Int = number2Int*10 - digit
                }else{
                    number2Int = number2Int*10 + digit
                }
            }
            if (number1Dec != false){
                binding.tvOperation.text = "$number1Int$currentOperation${number2Int.toInt()}"
            }else{
                binding.tvOperation.text = "${number1Int.toInt()}$currentOperation${number2Int.toInt()}"
            }
        }else{
            println("Adding decimal to number2")
            number2Float = number2Float/10+digit.toFloat()/10
            number2Int = number2Int + number2Float
            if (number1Dec != true){
                binding.tvOperation.text = "${number1Int.toInt()}$currentOperation$number2Int"
            }else{
                println("Show post adding 2")
                binding.tvOperation.text = "$number1Int$currentOperation$number2Int"
            }
        }
    }

    private fun addDigitToNumber1(digit : Int){
        if (number1Dec != true){
            //Añado entero
            if(number1IsNegative){
                number1Int = -(number1Int*10 + digit)
                number1IsNegative = false
            }else{
                //Añado entero positivo
                if(number1Int < 0){
                    number1Int = number1Int*10 - digit
                }else{
                    number1Int = number1Int*10 + digit
                }
            }
            binding.tvOperation.text = "${number1Int.toInt()}"
        }else{
            number1Float = number1Float/10+digit.toFloat()/10
            println("n1f = $number1Float")
            println("n1int = $number1Int")

            if(number1Int <= 0){
                number1Int = number1Int - number1Float
            }else{
                number1Int = number1Int + number1Float
            }

            binding.tvOperation.text = "$number1Int"
        }
    }



    private fun operate(operation : String){
        if (currentOperation != ""){
            //Estoy trabajando con number 2
            if (operation == "-" && number2Int == 0.0f){
                //Voy a hacer negativo number 2
                number2IsNegative = true
                val currenttvoperation = binding.tvOperation.text
                binding.tvOperation.text = "$currenttvoperation-"
            }else{
                //Voy a operar
                println("Else number 2")
                currentOperation = operation
                binding.tvOperation.text = "${number1Int.toInt()}$currentOperation${number2Int.toInt()}"
            }
        }else{
            //Estoy trabajano con number 1
            if (operation == "-" && number1Int == 0.0f){
                //Voy a hacer negativo numbe 1
                number1IsNegative = true
                binding.tvOperation.text = "-"
            }else{
                //Voy a operar
                println("Else number 1")
                currentOperation = operation
                if (number1Dec != false){
                    binding.tvOperation.text = "$number1Int$currentOperation"
                }else{
                    binding.tvOperation.text = "${number1Int.toInt()}$currentOperation"
                }
            }
        }
    }

    private fun solve(){
        resetDec()
        when (currentOperation){
            "+" -> suma()
            "-" -> resta()
            "x" -> multiplica()
            "/" -> divide()
        }

    }

    private fun resetDec(){
        number1Dec = false
        number2Dec = false
        number1Float = 0.0f
        number2Float = 0.0f
    }

    private fun delete(){
        number1Int = 0f
        number2Int = 0f
        currentOperation = ""
        binding.tvResult.text = ""
        binding.tvOperation.text = ""
    }

    private fun suma(){
        number1Int= number1Int+number2Int
        showResult()
    }

    private fun resta(){
        number1Int= number1Int-number2Int
        showResult()
    }

    private fun multiplica(){
        number1Int= number1Int*number2Int
        showResult()
    }

    private fun divide(){
        number1Int= number1Int/number2Int
        showResult()
    }

    private fun showResult(){
        if (number1Int % 1.toFloat() != 0.0f){
            binding.tvResult.text = number1Int.toString()
        }else{
            binding.tvResult.text = number1Int.toInt().toString()
        }


    }

    //operaores negativos
    //Por alguna razón cuando meto decimales me resta una unidad de la parta entera
    //(es porque la parte entera es negativa y la decimal positiva y se suman,tendria que restar si entero es negativo)

    //(ojo que puede ser que haya pulsado antes de meter algun digito dividir o multi o suma) !!!!!!!!!!!!!!!!!
    //Ojo que existe la posibilidad de que pulsen el punto sin meter un número para la parte entera y se podrian escaquear de negativizar el numero(igual para el numero 2)!!!!!!

    //Operaciones acumuladas

    //Operaciones combinadas

    // esto sería mejor creando una dataclass number,que tenga una parte entera,una decimal,si es negativo,y cada vez que se pulsa un operador se instancia un nuevo objeto Numero
    //otro objeto que se llame operación que acumule los numeros y los operadores que les separan para luego al darle al igual se ejecuta segun preferencia

    //Quitar todos los warnings del xml y del activity
}