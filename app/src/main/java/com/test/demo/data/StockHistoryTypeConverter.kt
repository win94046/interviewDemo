package com.test.demo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StockHistoryTypeConverter {
    // 待新增使用
    private val gson = Gson()

    @TypeConverter
    fun fromStockHistoryDataList(dataList: StockHistoryDataListJson): String {
        return gson.toJson(dataList) // 轉換為 JSON 字串
    }

    @TypeConverter
    fun toStockHistoryDataList(json: String): StockHistoryDataListJson {
        val type = object : TypeToken<StockHistoryDataListJson>() {}.type
        return gson.fromJson(json, type) // 轉回 StockHistoryDataListJson
    }
}