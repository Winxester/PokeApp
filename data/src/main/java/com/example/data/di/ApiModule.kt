package com.example.data.di

import com.example.core.constants.Constants.Companion.BASE_URL
import com.example.data.repository.PokemonRepositoryImpl
import com.example.data.repository.dataSource.RemotePokemonDataSource
import com.example.data.repository.dataSource.RemotePokemonDataSourceImpl
import com.example.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideDataSource(retrofit: Retrofit): RemotePokemonDataSource =
        retrofit.create(RemotePokemonDataSource::class.java)

    @Singleton
    @Provides
    fun providesRepository(dataSourceImpl: RemotePokemonDataSourceImpl): PokemonRepository =
        PokemonRepositoryImpl(dataSourceImpl)

}