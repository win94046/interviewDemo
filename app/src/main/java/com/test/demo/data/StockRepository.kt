package com.test.demo.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StockRepository @Inject constructor(private val stockDao: StockDao) {
    val allStocks: Flow<List<StockHistory>> = stockDao.getAllStocks()

    suspend fun insert(stock: StockHistory) {
        stockDao.insertStock(stock)
    }
    suspend fun insertAll(stocks: List<StockHistory>) {
        stockDao.insertAll(stocks)
    }

    suspend fun update(stock: StockHistory) {
        stockDao.updateStock(stock)
    }

    suspend fun delete(stock: StockHistory) {
        stockDao.deleteStock(stock)
    }
    suspend fun deleteAll() {
        stockDao.deleteAll()
    }

    fun getStockCount(): Flow<Int> {
        return stockDao.getStockCount()
    }

    fun getStockBySymbol(symbol: String): Flow<List<StockHistory>> {
        return stockDao.getStockBySymbol(symbol)
    }
}
