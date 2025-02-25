package com.test.demo.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.test.demo.ui.StockListScreenPage
import com.test.demo.viewmodel.StockViewModel

class StockListScreen : Screen {
    @Composable
    override fun Content() {
        StockListScreenPage()
    }
}