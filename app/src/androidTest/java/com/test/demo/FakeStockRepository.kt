package com.test.demo


import com.test.demo.data.StockHistory
import com.test.demo.data.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.*

class FakeStockRepository @Inject constructor() : StockRepository(stockDao = FakeStockDao()) {

    private val stockData = MutableStateFlow<List<StockHistory>>(emptyList())

    override val allStocks: Flow<List<StockHistory>> = stockData.asStateFlow()

    override suspend fun insert(stock: StockHistory) {
        stockData.value = stockData.value + stock
    }

    override suspend fun insertAll(stocks: List<StockHistory>) {
        stockData.value = stockData.value + stocks
    }

    override suspend fun update(stock: StockHistory) {
        stockData.value = stockData.value.map {
            if (it.symbol == stock.symbol && it.date == stock.date) stock else it
        }
    }

    override suspend fun delete(stock: StockHistory) {
        stockData.value = stockData.value.filterNot { it.symbol == stock.symbol && it.date == stock.date }
    }

    override suspend fun deleteAll() {
        stockData.value = emptyList()
    }

    override fun getStockCount(): Flow<Int> {
        return stockData.map { it.size }
    }

    override fun getStockBySymbol(symbol: String): Flow<List<StockHistory>> {
        return stockData.map { list -> list.filter { it.symbol == symbol } }
    }
}