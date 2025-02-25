package com.test.demo.data

data class StockHistoryDataListJson(
    val dataList: List<StockHistoryData> // 存儲時間與成交價格
)

data class StockHistoryData(
    val time: String,  // 時間 (格式: HH:mm:ss)
    val price: Double  // 成交價格
)