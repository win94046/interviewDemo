package com.test.demo

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.test.demo.data.StockHistory
import com.test.demo.di.AppModule
import com.test.demo.ui.StockListScreenPageTestable
import com.test.demo.viewmodel.StockViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@UninstallModules(AppModule::class) // 避免 StockRepository 重複綁定
@HiltAndroidTest
class StockListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this) // 初始化 Hilt 測試規則

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule() // 設定 Compose 測試環境

    @Before
    fun setup() {
        hiltRule.inject() // 初始化 Hilt 測試
    }


    /**
     *  測試 `StockListScreen` 是否顯示正確的股票數據
     */
    @Test
    fun testStockListScreenDisplaysStockItems() {
        // 模擬數據
        val mockStockList = listOf(
            StockHistory(symbol = "AAPL", date = "2024-02-25", openPrice = 150.0, closePrice = 155.0, volume = 10000, stockHistoryDataListJson = ""),
            StockHistory(symbol = "TSLA", date = "2024-02-26", openPrice = 700.0, closePrice = 710.0, volume = 5000, stockHistoryDataListJson = "")
        )

        // 設定 Compose 渲染
        composeTestRule.setContent {
            StockListScreenPageTestable(mockStockList)
        }

        // 確保 `股票歷史` 標題存在
        composeTestRule.onNode(hasText("股票歷史")).assertExists()

        // 確保股票數據顯示
        composeTestRule.onNode(hasText("股票代號: AAPL")).assertExists()
        composeTestRule.onNode(hasText("股票代號: TSLA")).assertExists()
    }

    /**
     *  測試 `StockListScreen` 當沒有數據時，顯示 `空畫面`
     */
    @Test
    fun testStockListScreenDisplaysEmptyState() {
        composeTestRule.setContent {
            StockListScreenPageTestable(emptyList()) // 測試空數據
        }

        // 應該找不到任何 `股票代號`
        composeTestRule.onNode(hasText("股票代號")).assertDoesNotExist()
    }

}
