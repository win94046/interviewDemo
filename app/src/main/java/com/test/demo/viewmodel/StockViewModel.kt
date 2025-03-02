package com.test.demo.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.test.demo.data.StockHistory
import com.test.demo.data.StockHistoryData
import com.test.demo.data.StockHistoryDataListJson
import com.test.demo.data.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor (private val repository: StockRepository) : ViewModel() {
    val allStocks: Flow<List<StockHistory>> = repository.allStocks

    // 用於控制當前的 UI 狀態 (列表畫面 or 詳細畫面)
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.ListScreen)
    val screenState: StateFlow<ScreenState> = _screenState

    // 選中的股票，當畫面切換到 `StockDetailScreen` 時使用
    private val _selectedStock = MutableStateFlow<StockHistory?>(null)
    val selectedStock: StateFlow<StockHistory?> = _selectedStock

    init {
        insertDefaultStock() // 在 ViewModel 初始化時插入預設數據
    }

    private fun insertDefaultStock() {
        viewModelScope.launch {
            repository.getStockCount().collect { count ->
                if (count == 0) { // 只有當數據庫沒有數據時才插入
                    val stocks = List(50) {
                        // 生成隨機交易數據
                        val historyDataList = List((5..15).random()) { // 每個股票 5~15 筆歷史記錄
                            StockHistoryData(
                                time = "${(9..16).random()}:${(0..59).random().toString().padStart(2, '0')}:${(0..59).random().toString().padStart(2, '0')}",
                                price = (100..500).random().toDouble()
                            )
                        }

                        // 轉換為 JSON 字串
                        val historyDataJson = Gson().toJson(StockHistoryDataListJson(historyDataList))

                        // 創建 StockHistory 物件
                        StockHistory(
                            symbol = listOf("AAPL", "TSLA", "GOOGL", "AMZN", "MSFT").random(),
                            date = "2024-02-${(1..28).random()}",
                            openPrice = (100..500).random().toDouble(),
                            closePrice = (100..500).random().toDouble(),
                            volume = (1000..10000).random(),
                            stockHistoryDataListJson = historyDataJson // 存入 JSON 格式的歷史數據
                        )
                    }
                    repository.insertAll(stocks) // 批量插入 50 筆
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

    // 切換到詳細畫面
    fun selectStock(stock: StockHistory) {
        _selectedStock.value = stock
        _screenState.value = ScreenState.DetailScreen
    }

    // 返回股票列表
    fun backToList() {
        _selectedStock.value = null
        _screenState.value = ScreenState.ListScreen
    }

}

// 畫面狀態的 Enum
sealed class ScreenState {
    object ListScreen : ScreenState()
    object DetailScreen : ScreenState()
}