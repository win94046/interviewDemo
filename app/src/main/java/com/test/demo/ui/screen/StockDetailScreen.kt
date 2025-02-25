package com.test.demo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.test.demo.data.StockHistory

class StockDetailScreen(private val stock: StockHistory) : Screen {
    @Composable
    override fun Content() {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("股票詳情", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text("股票代號: ${stock.symbol}")
            Text("日期: ${stock.date}")
            Text("開盤價: ${stock.openPrice}")
            Text("收盤價: ${stock.closePrice}")
            Text("交易量: ${stock.volume}")
        }
    }
}