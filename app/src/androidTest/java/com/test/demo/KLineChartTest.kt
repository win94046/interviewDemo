package com.test.demo

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.test.demo.data.StockHistoryData
import com.test.demo.ui.screen.KLineChart
import org.junit.Rule
import org.junit.Test

class KLineChartTest {

    @get:Rule
    val composeTestRule = createComposeRule() // 設定 Compose 測試環境

    @Test
    fun testKLineChartRendersCorrectly() {
        val testData = listOf(
            StockHistoryData("09:30:00", 150.0),
            StockHistoryData("10:00:00", 155.5),
            StockHistoryData("10:30:00", 158.0),
            StockHistoryData("11:00:00", 157.5)
        )

        composeTestRule.setContent {
            KLineChart(testData)
        }

        // 確保 K 線圖正確顯示
        composeTestRule.onRoot().printToLog("ComposeTree")
        composeTestRule.waitUntil(2000) {
            try {
                composeTestRule.onNode(hasText("成交價格走勢")).assertExists()
                true
            }catch (e: AssertionError){
                false
            }
        }

    }

    @Test
    fun testKLineChartHandlesEmptyData() {
        composeTestRule.setContent {
            KLineChart(emptyList())
        }

        // 應該顯示錯誤訊息
        composeTestRule.onNode(hasText("❌ 沒有數據可顯示")).assertExists()
    }



}
