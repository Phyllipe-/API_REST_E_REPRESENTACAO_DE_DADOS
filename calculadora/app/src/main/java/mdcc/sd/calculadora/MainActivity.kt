package mdcc.sd.calculadora

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import mdcc.sd.calculadora.ui.theme.CalculadoraTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    private val TAG_ADDITION = "ADDITION"
    private val TAG_SUBTRACTION = "SUBTRACTION"
    private val TAG_MULTIPLICATION = "MULTIPLICATION"
    private val TAG_DIVISION = "DIVISION"
    private val TAG_OPERATIONS = "OPERATIONS"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberZero = findViewById<ImageView>(R.id.zero)
        val numberOne = findViewById<ImageView>(R.id.one)
        val numberTwo = findViewById<ImageView>(R.id.two)
        val numberThree = findViewById<ImageView>(R.id.three)
        val numberFour = findViewById<ImageView>(R.id.four)
        val numberFive = findViewById<ImageView>(R.id.five)
        val numberSix = findViewById<ImageView>(R.id.six)
        val numberSeven = findViewById<ImageView>(R.id.seven)
        val numberEight = findViewById<ImageView>(R.id.eight)
        val numberNine = findViewById<ImageView>(R.id.nine)

        numberZero.setOnClickListener{ addExpression("0", true) }
        numberOne.setOnClickListener{ addExpression("1", true) }
        numberTwo.setOnClickListener{ addExpression("2", true) }
        numberThree.setOnClickListener{ addExpression("3", true) }
        numberFour.setOnClickListener{ addExpression("4", true) }
        numberFive.setOnClickListener{ addExpression("5", true) }
        numberSix.setOnClickListener{ addExpression("6", true) }
        numberSeven.setOnClickListener{ addExpression("7", true) }
        numberEight.setOnClickListener{ addExpression("8", true) }
        numberNine.setOnClickListener{ addExpression("9", true) }

        val addition = findViewById<ImageView>(R.id.addition)
        val subtraction = findViewById<ImageView>(R.id.subtraction)
        val multiplication = findViewById<ImageView>(R.id.multiplication)
        val division = findViewById<ImageView>(R.id.division)
        val equals = findViewById<ImageView>(R.id.equals)

        addition.setOnClickListener{ addExpression("+", true) }
        subtraction.setOnClickListener{ addExpression("-", true) }
        multiplication.setOnClickListener{ addExpression("×", true) }
        division.setOnClickListener{ addExpression("÷", true) }

        val cancel = findViewById<ImageView>(R.id.cancel)
        val clear = findViewById<ImageView>(R.id.clear)
        val result = findViewById<TextView>(R.id.result)
        val expression = findViewById<TextView>(R.id.expression)
        val comma = findViewById<ImageView>(R.id.comma)
        comma.setOnClickListener{ addExpression(",", false) }

        cancel.setOnClickListener{
            expression.text = ""
            result.text = ""
        }

        clear.setOnClickListener {
            val expressionString = expression.text.toString()
            if(expressionString.isNotBlank())
                expression.text = expressionString.substring(0,expressionString.length -1)
            result.text =""
        }

        equals.setOnClickListener {
            val calculatorAPI = RetrofitClient.calculatorAPI

            val (operation, operands1,operands2) = regexExpression(expression.text.toString())

            when (operation) {
                "+" -> addition(calculatorAPI,operands1.toDouble(),operands2.toDouble())
                "-" -> subtraction(calculatorAPI,operands1.toDouble(),operands2.toDouble())
                "×" -> multiplication(calculatorAPI,operands1.toDouble(),operands2.toDouble())
                "÷" -> multiplication(calculatorAPI,operands1.toDouble(),operands2.toDouble())
                else -> throw IllegalArgumentException("OPERADOR INVÁLIDO")
            }

        }

        val calculatorAPI = RetrofitClient.calculatorAPI

        calculatorAPI.getOperations().enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val operations = response.body()
                    // Faça algo com a lista de operações
                } else {
                    // Lidar com o erro da resposta
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                // Lidar com falha na requisição
            }
        })

    }

    fun addition(calculatorAPI: CalculatorAPI, value1: Double, value2: Double){
        RetrofitClient.calculatorAPI.addition(value1, value2).enqueue(object : Callback<Result> {
            val result = findViewById<TextView>(R.id.result)
            override fun onResponse(call: Call<Result>, response: Response<Result>) {

                if (response.isSuccessful) {

                    val resultResponse = response.body()

                    if(resultResponse?.error == false) {
                        result.text = resultResponse?.value.toString()
                        Log.i(TAG_ADDITION, resultResponse?.value.toString())
                    }else{
                        result.text = "OPERADOR INVÁLIDO"
                        Log.i(TAG_ADDITION, "OPERADOR INVÁLIDO")
                    }

                } else {
                    result.text = "ERRO DA RESPOSTA API"
                    Log.i(TAG_ADDITION, "ERRO DA RESPOSTA API")
                    // LIDAR COM O ERRO DA RESPOSTA API
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                result.text = "FALHA NA REQUISIÇÃO API"
                Log.i(TAG_ADDITION, "FALHA NA REQUISIÇÃO API")
                // LIDAR COM FALHA NA REQUISIÇÃO API
            }
        })
    }

    fun subtraction(calculatorAPI: CalculatorAPI, value1: Double, value2: Double){
        RetrofitClient.calculatorAPI.subtraction(value1, value2).enqueue(object : Callback<Result> {
            val result = findViewById<TextView>(R.id.result)
            override fun onResponse(call: Call<Result>, response: Response<Result>) {

                if (response.isSuccessful) {

                    val resultResponse = response.body()

                    if(resultResponse?.error == false) {
                        result.text = resultResponse?.value.toString()
                        Log.i(TAG_SUBTRACTION, resultResponse?.value.toString())
                    }else{
                        result.text = "OPERADOR INVÁLIDO"
                        Log.i(TAG_SUBTRACTION, "OPERADOR INVÁLIDO")
                    }

                } else {
                    result.text = "ERRO DA RESPOSTA API"
                    Log.i(TAG_SUBTRACTION, "ERRO DA RESPOSTA API")
                    // LIDAR COM O ERRO DA RESPOSTA API
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                result.text = "FALHA NA REQUISIÇÃO API"
                Log.i(TAG_SUBTRACTION, "FALHA NA REQUISIÇÃO API")
                // LIDAR COM FALHA NA REQUISIÇÃO API
            }
        })
    }

    fun multiplication(calculatorAPI: CalculatorAPI, value1: Double, value2: Double){
        RetrofitClient.calculatorAPI.multiplication(value1, value2).enqueue(object : Callback<Result> {
            val result = findViewById<TextView>(R.id.result)
            override fun onResponse(call: Call<Result>, response: Response<Result>) {

                if (response.isSuccessful) {

                    val resultResponse = response.body()

                    if(resultResponse?.error == false) {
                        result.text = resultResponse?.value.toString()
                        Log.i(TAG_MULTIPLICATION, resultResponse?.value.toString())
                    }else{
                        result.text = "OPERADOR INVÁLIDO"
                        Log.i(TAG_MULTIPLICATION, "OPERADOR INVÁLIDO")
                    }

                } else {
                    result.text = "ERRO DA RESPOSTA API"
                    Log.i(TAG_MULTIPLICATION, "ERRO DA RESPOSTA API")
                    // LIDAR COM O ERRO DA RESPOSTA API
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                result.text = "FALHA NA REQUISIÇÃO API"
                Log.i(TAG_MULTIPLICATION, "FALHA NA REQUISIÇÃO API")
                // LIDAR COM FALHA NA REQUISIÇÃO API
            }
        })
    }

    fun division(calculatorAPI: CalculatorAPI, value1: Double, value2: Double){
        RetrofitClient.calculatorAPI.division(value1, value2).enqueue(object : Callback<Result> {
            val result = findViewById<TextView>(R.id.result)
            override fun onResponse(call: Call<Result>, response: Response<Result>) {

                if (response.isSuccessful) {

                    val resultResponse = response.body()

                    if(resultResponse?.error == false) {
                        result.text = resultResponse?.value.toString()
                        Log.i(TAG_DIVISION, resultResponse?.value.toString())
                    }else{
                        result.text = "OPERADOR INVÁLIDO"
                        Log.i(TAG_DIVISION, "OPERADOR INVÁLIDO")
                    }

                } else {
                    result.text = "ERRO DA RESPOSTA API"
                    Log.i(TAG_DIVISION, "ERRO DA RESPOSTA API")
                    // LIDAR COM O ERRO DA RESPOSTA API
                }
            }

            override fun onFailure(call: Call<Result>, t: Throwable) {
                result.text = "FALHA NA REQUISIÇÃO API"
                Log.i(TAG_DIVISION, "FALHA NA REQUISIÇÃO API")
                // LIDAR COM FALHA NA REQUISIÇÃO API
            }
        })
    }
    fun addExpression(number: String, clearData: Boolean) {
        val result = findViewById<TextView>(R.id.result)
        val expression = findViewById<TextView>(R.id.expression)

        if(result.text.isNotEmpty())
            expression.text = ""
        if (clearData) {
            result.text = ""
            expression.append(number)
        } else {
            expression.append(result.text)
        }
    }

    fun regexExpression(expression: String): List<String> {
        val pattern = Regex("""\s*([-+×÷])\s*""")
        val matchResult = pattern.find(expression)
        val parts = pattern.split(expression)

        val operation = matchResult!!.groupValues[1]

        val operand1 = parts[0]
        val operand2 = parts[1]


        return listOf(operation, operand1,operand2)
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraTheme {
        Greeting("Android")
    }
}
