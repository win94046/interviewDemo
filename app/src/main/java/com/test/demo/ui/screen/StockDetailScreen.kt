package com.test.demo.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.test.demo.data.StockHistory
import com.test.demo.data.StockHistoryData
import com.test.demo.data.StockHistoryDataListJson

class StockDetailScreen(private val stock: StockHistory) : Screen {
    @Composable
    override fun Content() {
        Log.i("test", "StockDetailScreen Created")
        val navigator = LocalNavigator.currentOrThrow // 取得 Voyager Navigator
        Log.i("test", "Navigator is available in StockDetailScreen")
        // 解析 JSON 轉換為 List<StockHistoryData>
        val historyDataList = remember(stock.stockHistoryDataListJson) {
            val type = TypeToken.getParameterized(StockHistoryDataListJson::class.java).type
            Gson().fromJson<StockHistoryDataListJson>(stock.stockHistoryDataListJson, type)
        }?.dataList ?: emptyList()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 返回按鈕
            Button(
                onClick = { navigator.pop() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("返回", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 股票詳情卡片
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "股票詳情",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF2196F3)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    StockDetailRow("股票代號", stock.symbol)
                    StockDetailRow("交易日期", stock.date)
                    StockDetailRow("開盤價", "$${stock.openPrice}")
                    StockDetailRow("收盤價", "$${stock.closePrice}")
                    StockDetailRow("交易量", stock.volume.toString())

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "數據僅供參考，請勿作為投資決策依據。",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 繪製簡易 K 線圖
            KLineChart(historyDataList)
        }
    }

    /**
     * 股票詳細資訊的 Row 排列
     */
    @Composable
    private fun StockDetailRow(label: String, value: String) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                Text(text = value, fontWeight = FontWeight.Medium)
            }
            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }
    }
}


@Composable
fun KLineChart(historyData: List<StockHistoryData>) {
    Text("成交價格走勢", style = MaterialTheme.typography.headlineSmall)
    if (historyData.isEmpty()) {
        Text("❌ 沒有數據可顯示", color = Color.Red)
        return
    }
    // 排序時間，確保 K 線圖時間順序正確
    val sortedData = historyData.sortedBy { it.time }

    // 設定最大 & 最小價格，讓圖表有適當的縮放
    val maxPrice = sortedData.maxOf { it.price }
    val minPrice = sortedData.minOf { it.price }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // 調整高度，讓 X 軸標記有空間
            .background(Color.White)
            .padding(start = 35.dp , top = 8.dp , bottom = 8.dp)
    ) {
        val widthStep = size.width / (sortedData.size - 1).coerceAtLeast(1)
        val heightRange = maxPrice - minPrice
        val heightScale = size.height * 0.8f / (heightRange.coerceAtLeast(1.0)).toFloat()

        // 定義 X/Y 軸
        val axisColor = Color.Gray
        val labelColor = Color.Black

        // 繪製 Y 軸（價格軸）
        drawLine(
            color = axisColor,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2.dp.toPx()
        )

        // 繪製 X 軸（時間軸）
        drawLine(
            color = axisColor,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = 2.dp.toPx()
        )

        // 繪製 K 線圖
        sortedData.forEachIndexed { index, data ->
            if (index < sortedData.size - 1) {
                val startX = index * widthStep
                val startY = size.height - ((data.price - minPrice) * heightScale)

                val nextX = (index + 1) * widthStep
                val nextY = size.height - ((sortedData[index + 1].price - minPrice) * heightScale)

                drawLine(
                    color = Color.Green,
                    start = Offset(startX, startY.toFloat()),
                    end = Offset(nextX, nextY.toFloat()),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // 繪製 Y 軸價格刻度
        val priceStep = (maxPrice - minPrice) / 5 // 5 個標記點
        for (i in 0..5) {
            val price = minPrice + (priceStep * i)
            val yPos = size.height - ((price - minPrice) * heightScale)

            drawLine(
                color = axisColor,
                start = Offset(-10f, yPos.toFloat()),
                end = Offset(10f, yPos.toFloat()),
                strokeWidth = 1.dp.toPx()
            )

            drawIntoCanvas { canvas ->
                val text = "%.2f".format(price)
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 30f
                    textAlign = android.graphics.Paint.Align.RIGHT
                }
                canvas.nativeCanvas.drawText(text, -20f, yPos.toFloat(), textPaint)
            }
        }

        // 繪製 X 軸時間刻度
        val timeStep = sortedData.size / 5.coerceAtLeast(1) // 5 個標記點
        for (i in 0..5) {
            val dataIndex = (i * timeStep).coerceAtMost(sortedData.size - 1)
            val time = sortedData[dataIndex].time
            val xPos = dataIndex * widthStep

            drawLine(
                color = axisColor,
                start = Offset(xPos, size.height - 10f),
                end = Offset(xPos, size.height + 10f),
                strokeWidth = 1.dp.toPx()
            )

            drawIntoCanvas { canvas ->
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 30f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                canvas.nativeCanvas.drawText(time, xPos, size.height + 30f, textPaint)
            }
        }
    }
}

