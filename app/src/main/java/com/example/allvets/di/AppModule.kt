package com.example.allvets.di

import android.content.Context
import com.example.allvets.domain.WebService
import com.example.allvets.domain.login.AVLoginRepo
import com.example.allvets.domain.login.AVLoginRepoImpl
import com.example.allvets.utils.AppConstans
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun providesLoginRepository(repoLogin: AVLoginRepoImpl): AVLoginRepo

    companion object {

        @Provides
        fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().also {
                        it.setLevel(
                            HttpLoggingInterceptor.Level.HEADERS
                        )
                    }
                )
                .build()

        @Provides
        fun providesRetrofitInstance(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        fun providesCurrencyService(retrofit: Retrofit) = retrofit.create<WebService>()

        @Provides
        @Singleton
        fun provideFirestore() = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseStorage() = FirebaseStorage.getInstance()


    }


}