package bobby.irawan.base.android.project.di

import androidx.room.Room
import bobby.irawan.base.android.project.utils.Constants.HEADER_INTERCEPTOR
import bobby.irawan.base.android.project.utils.Constants.LOGGING_INTERCEPTOR
import bobby.irawan.moviecatalogue.data.interceptor.HeaderInterceptor
import bobby.irawan.moviecatalogue.data.local.room.TempDatabase
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Bobby Irawan on 14/12/20.
 */

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            TempDatabase::class.java,
            "temp_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<TempDatabase>().favoriteDao()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(qualifier = named(LOGGING_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(qualifier = named(HEADER_INTERCEPTOR)))
            .addInterceptor(ChuckerInterceptor(androidContext()))
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<Interceptor>(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    single<Interceptor>(named(HEADER_INTERCEPTOR)) {
        HeaderInterceptor()
    }

    single {
        Retrofit.Builder().baseUrl("BASE URL")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
}