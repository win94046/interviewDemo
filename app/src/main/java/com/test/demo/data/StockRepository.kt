package com.test.demo.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class StockRepository @Inject constructor(protected open val stockDao: StockDao) {
    open val allStocks: Flow<List<StockHistory>> = stockDao.getAllStocks()

    open suspend fun insert(stock: StockHistory) {
        stockDao.insertStock(stock)
    }
    open suspend fun insertAll(stocks: List<StockHistory>) {
        stockDao.insertAll(stocks)
    }

    open suspend fun update(stock: StockHistory) {
        stockDao.updateStock(stock)
    }

    open suspend fun delete(stock: StockHistory) {
        stockDao.deleteStock(stock)
    }
    open suspend fun deleteAll() {
        stockDao.deleteAll()
    }

    open fun getStockCount(): Flow<Int> {
        return stockDao.getStockCount()
    }

    open fun getStockBySymbol(symbol: String): Flow<List<StockHistory>> {
        return stockDao.getStockBySymbol(symbol)
    }
}
