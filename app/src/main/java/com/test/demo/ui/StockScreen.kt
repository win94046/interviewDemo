package com.test.demo.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.test.demo.data.StockHistory
import com.test.demo.ui.screen.StockDetailScreen
import com.test.demo.ui.screen.StockListScreen
import com.test.demo.viewmodel.StockViewModel


@Composable
fun StockListScreenPage() {
    val stockViewModel: StockViewModel = hiltViewModel() // 使用 Hilt 自動提供 ViewModel
    val stockList by stockViewModel.allStocks.collectAsState(initial = emptyList())

    val navigator = LocalNavigator.currentOrThrow

    StockListScreenContent(stockList, navigator)
}

@Composable
fun StockListScreenPageTestable(testStockList: List<StockHistory>) {
    Navigator(StockListScreen()) { navigator ->
        StockListScreenContent(testStockList, navigator)
    }
}

@Composable
fun StockListScreenContent(stockList: List<StockHistory>, navigator: Navigator) {
    val stockViewModel: StockViewModel = hiltViewModel() // 使用 Hilt 自動提供 ViewModel

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("股票歷史", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            items(stockList) { stock ->
                StockItem(stock) {
                    Log.i("test", "StockItem clicked: ${stock.symbol}")
                    stockViewModel.recordStock(stock)
                    navigator.push(StockDetailScreen()) // 正確使用 navigator
                    Log.i("test", "Current screen after push: ${navigator.lastItem}")
                }
            }
        }
    }
}

@Composable
fun StockItem(stock: StockHistory, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }, // 點擊觸發 onClick
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("股票代號: ${stock.symbol}")
            Text("日期: ${stock.date}")
            Text("開盤: ${stock.openPrice}, 收盤: ${stock.closePrice}")
            Text("交易量: ${stock.volume}")
        }
    }
}
