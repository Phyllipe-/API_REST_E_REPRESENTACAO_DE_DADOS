package mdcc.sd.calculadora

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CalculatorAPI {
    @GET("operacoes")
    fun getOperations(): Call<List<String>>

    @POST("operacoes/soma/{value1}/{value2}")
    fun addition(
        @Path("value1") value1: Double,
        @Path("value2") value2: Double
    ): Call<Result>

    @POST("operacoes/subtrai/{value1}/{value2}")
    fun subtraction(
        @Path("value1") value1: Double,
        @Path("value2") value2: Double
    ): Call<Result>

    @POST("operacoes/multiplica/{value1}/{value2}")
    fun multiplication(
        @Path("value1") value1: Double,
        @Path("value2") value2: Double
    ): Call<Result>

    @POST("operacoes/divide/{value1}/{value2}")
    fun division(
        @Path("value1") value1: Double,
        @Path("value2") value2: Double
    ): Call<Result>
}