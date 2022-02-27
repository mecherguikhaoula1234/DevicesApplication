package com.example.managedevices.DI

import android.content.Context
import com.example.managedevices.Data.db.AppDatabase
import com.example.managedevices.Data.db.DeviceDao
import com.example.managedevices.Network.RetrofitBuilder
import com.example.managedevices.Network.RetrofitService
import com.example.managedevices.Repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRepository(deviceDao: DeviceDao, retrofitService: RetrofitService): HomeRepository {
        return HomeRepository(deviceDao, retrofitService)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return RetrofitBuilder().createRetrofitInstance()
    }

    @Singleton
    @Provides
    fun provideDeviceApi(retrofit: Retrofit): RetrofitService{
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DeviceDao {
        return AppDatabase.getDatabase(context).getDeviceDao()
    }
}