package br.ufs.uolchallenge.data.rest

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by bira on 11/3/17.
 */
object WebServiceFactory {

    val DEFAULT_TIMEOUT_SECONDS = 15L

    fun create(
            apiURL: String = UOLWebService.BASE_URL,
            debuggable: Boolean = false): UOLWebService {

        val logger = createLogger(debuggable)
        val httpClient = createHttpClient(logger = logger)
        val converter = GsonConverterFactory.create()
        val rxAdapter = RxJava2CallAdapterFactory.create()

        val retrofit = Retrofit.Builder()
                .baseUrl(apiURL)
                .client(httpClient)
                .addConverterFactory(converter)
                .addCallAdapterFactory(rxAdapter)
                .build()

        return retrofit.create(UOLWebService::class.java)
    }

    private fun createLogger(debuggable: Boolean): Interceptor {
        val loggingLevel = if (debuggable) {
            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val logger = HttpLoggingInterceptor()
        logger.level = loggingLevel
        return logger
    }

    private fun createHttpClient(logger: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
    }

}