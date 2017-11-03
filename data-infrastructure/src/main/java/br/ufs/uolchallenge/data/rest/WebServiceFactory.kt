package br.ufs.uolchallenge.data.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by bira on 11/3/17.
 */
object WebServiceFactory {

    fun create(debuggable: Boolean): UOLWebService {

        val httpClient = makeHttpClient(debuggable)
        val converter = GsonConverterFactory.create()
        val rxAdapter = RxJava2CallAdapterFactory.create()

        val retrofit = Retrofit.Builder()
                .baseUrl(UOLWebService.BASE_URL)
                .client(httpClient)
                .addConverterFactory(converter)
                .addCallAdapterFactory(rxAdapter)
                .build()

        return retrofit.create(UOLWebService::class.java)
    }

    private fun makeHttpClient(debuggable: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(makeLoggingInterceptor(debuggable))
                .build()
    }

    private fun makeLoggingInterceptor(debuggable: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (debuggable)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}