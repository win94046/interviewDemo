package com.test.demo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.demo.data.StockHistory
import com.test.demo.data.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor (private val repository: StockRepository) : ViewModel() {
    val allStocks: Flow<List<StockHistory>> = repository.allStocks

    init {
        insertDefaultStock() // 在 ViewModel 初始化時插入預設數據
    }

    private fun insertDefaultStock() {
        viewModelScope.launch {
            repository.getStockCount().collect { count ->
                if (count == 0) { // 只有當數據庫沒有數據時才插入
                    val stocks = List(50) {
                        StockHistory(
                            symbol = listOf("AAPL", "TSLA", "GOOGL", "AMZN", "MSFT").random(),
                            date = "2024-02-${(1..28).random()}",
                            openPrice = (100..500).random().toDouble(),
                            closePrice = (100..500).random().toDouble(),
                            volume = (1000..10000).random()
                        )
                    }
                    repository.insertAll(stocks)
                }
            }
        }
    }


    fun insert(stock: StockHistory) = viewModelScope.launch {
        repository.insert(stock)
    }

    fun update(stock: StockHistory) = viewModelScope.launch {
        repository.update(stock)
    }

    fun delete(stock: StockHistory) = viewModelScope.launch {
        repository.delete(stock)
    }


    fun getStockBySymbol(symbol: String): Flow<List<StockHistory>> {
        return repository.getStockBySymbol(symbol)
    }
}
