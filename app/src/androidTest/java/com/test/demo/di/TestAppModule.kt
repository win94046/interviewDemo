package com.test.demo.di


import com.test.demo.FakeStockRepository
import com.test.demo.data.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class] // 這行關鍵 讓 `TestAppModule` 取代 `AppModule`
)
abstract class TestAppModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(fakeRepository: FakeStockRepository): StockRepository
}
