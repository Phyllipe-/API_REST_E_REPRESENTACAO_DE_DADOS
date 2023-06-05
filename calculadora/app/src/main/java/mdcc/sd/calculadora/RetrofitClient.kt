package mdcc.sd.calculadora

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://ec2-3-133-82-159.us-east-2.compute.amazonaws.com:3000/calculadora/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val calculatorAPI: CalculatorAPI by lazy {
        retrofit.create(CalculatorAPI::class.java)
    }
}
