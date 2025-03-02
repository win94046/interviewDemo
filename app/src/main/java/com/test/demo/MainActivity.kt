package com.test.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.test.demo.ui.screen.StockListScreen
import com.test.demo.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigator(StockListScreen()) // 設定主畫面
        }
        Log.i("test", "MainActivity Created")
    }

    override fun onStart() {
        super.onStart()
        Log.i("test", "MainActivity onStart")
    }
    override fun onStop() {
        super.onStop()
        Log.i("test", "MainActivity onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("test", "MainActivity onDestroy")
    }
    override fun onPause() {
        super.onPause()
        Log.i("test", "MainActivity onPause")
    }
    override fun onResume() {
        super.onResume()
        Log.i("test", "MainActivity onResume")
    }
    override fun onRestart() {
        super.onRestart()
        Log.i("test", "MainActivity onRestart")
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}