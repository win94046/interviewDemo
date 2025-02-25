package com.test.demo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "stock_history")
data class StockHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symbol: String,  // 股票代號
    val date: String,    // 交易日期 (可使用 String 或 Date)
    val openPrice: Double,  // 開盤價
    val closePrice: Double, // 收盤價
    val volume: Int        // 交易量
)
