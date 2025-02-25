package com.test.demo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.test.demo.MyApplication
import com.test.demo.data.StockDao
import com.test.demo.data.StockDatabase
import com.test.demo.data.StockRepository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideStockDatabase(context: Context): StockDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StockDatabase::class.java,
            "stock_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideStockDao(database: StockDatabase): StockDao {
        return database.stockDao()
    }

    @Singleton
    @Provides
    fun provideStockRepository(stockDao: StockDao): StockRepository {
        return StockRepository(stockDao)
    }
}
