package com.test.demo


import com.test.demo.data.StockDao
import com.test.demo.data.StockHistory
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FakeStockDao @Inject constructor() : StockDao {

    // ✅ 使用 `MutableStateFlow` 模擬 Room Database
    private val stockData = MutableStateFlow<List<StockHistory>>(emptyList())

    override suspend fun insertStock(stock: StockHistory) {
        stockData.value = stockData.value + stock
    }

    override suspend fun insertAll(stocks: List<StockHistory>) {
        stockData.value = stockData.value + stocks
    }

    override suspend fun updateStock(stock: StockHistory) {
        stockData.value = stockData.value.map {
            if (it.symbol == stock.symbol && it.date == stock.date) stock else it
        }
    }

    override suspend fun deleteStock(stock: StockHistory) {
        stockData.value = stockData.value.filterNot { it.symbol == stock.symbol && it.date == stock.date }
    }

    override suspend fun deleteAll() {
        stockData.value = emptyList()
    }

    override fun getStockCount(): Flow<Int> {
        return stockData.map { it.size }
    }

    override fun getAllStocks(): Flow<List<StockHistory>> {
        return stockData.asStateFlow()
    }

    override fun getStockBySymbol(symbol: String): Flow<List<StockHistory>> {
        return stockData.map { list -> list.filter { it.symbol == symbol } }
    }
}
