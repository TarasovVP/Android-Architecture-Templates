package com.vnteam.architecturetemplates.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vnteam.architecturetemplates.presentation.MainActivity.Companion.BASE_URL
import com.vnteam.architecturetemplates.presentation.MainActivity.Companion.SERVER_TIMEOUT
import com.vnteam.architecturetemplates.data.database.AppDatabase
import com.vnteam.architecturetemplates.data.database.ForkDao
import com.vnteam.architecturetemplates.data.mapperimpls.ForkDBMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.ForkResponseMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.OwnerDBMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.OwnerResponseMapperImpl
import com.vnteam.architecturetemplates.data.repositoryimpl.DBRepositoryImpl
import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.repositoryimpl.ApiRepositoryImpl
import com.vnteam.architecturetemplates.domain.mappers.ForkDBMapper
import com.vnteam.architecturetemplates.domain.mappers.ForkResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.ForkUIMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerDBMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerUIMapper
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.ForkUseCase
import com.vnteam.architecturetemplates.presentation.mapperimpls.ForkUIMapperImpl
import com.vnteam.architecturetemplates.presentation.mapperimpls.OwnerUIMapperImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.ForkUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(SERVER_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Singleton
    @Provides
    fun provideForkDao(appDatabase: AppDatabase): ForkDao {
        return appDatabase.forkDao()
    }

    @Singleton
    @Provides
    fun provideOwnerResponseMapper(): OwnerResponseMapper {
        return OwnerResponseMapperImpl()
    }

    @Singleton
    @Provides
    fun provideOwnerDBMapper(): OwnerDBMapper {
        return OwnerDBMapperImpl()
    }

    @Singleton
    @Provides
    fun provideOwnerUIMapper(): OwnerUIMapper {
        return OwnerUIMapperImpl()
    }

    @Singleton
    @Provides
    fun provideForkResponseMapper(ownerResponseMapper: OwnerResponseMapper): ForkResponseMapper {
        return ForkResponseMapperImpl(ownerResponseMapper)
    }

    @Singleton
    @Provides
    fun provideForkDBMapper(ownerDBMapper: OwnerDBMapper): ForkDBMapper {
        return ForkDBMapperImpl(ownerDBMapper)
    }

    @Singleton
    @Provides
    fun provideForkUIMapper(ownerUIMapper: OwnerUIMapper): ForkUIMapper {
        return ForkUIMapperImpl(ownerUIMapper)
    }

    @Singleton
    @Provides
    fun provideApiRepository(apiService: ApiService, forkResponseMapper: ForkResponseMapper): ApiRepository {
        return ApiRepositoryImpl(apiService, forkResponseMapper)
    }

    @Singleton
    @Provides
    fun provideDBRepository(forkDao: ForkDao, forkDBMapper: ForkDBMapper): DBRepository {
        return DBRepositoryImpl(forkDao, forkDBMapper)
    }

    @Singleton
    @Provides
    fun provideForkUseCase(apiRepository: ApiRepository, dbRepository: DBRepository): ForkUseCase {
        return ForkUseCaseImpl(apiRepository, dbRepository)
    }
}