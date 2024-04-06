package com.example.uos.network


import com.example.uos.utils.Config
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(private val username: String, private val password: String) : Interceptor {

    private val credentials: String = Credentials.basic(username, password)

    companion object {
        fun getAPIService(): APIInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(customLogInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(APIInterface::class.java)
        }

        private fun customLogInterceptor(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor(CustomHttp())
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

            val interceptor = Interceptor { chain ->
                val request: Request
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("version", "v5")
                request = requestBuilder.build()
                chain.proceed(request)
            }

            var okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(interceptor)
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .build()

            okHttpClient = okHttpClient.newBuilder()
                .addInterceptor(ApiClient("UNITYOFSOUTHGUJARAT", "VYARA"))
                .build()

            return okHttpClient
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}
