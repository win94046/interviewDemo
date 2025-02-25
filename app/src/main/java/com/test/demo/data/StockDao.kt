package com.test.demo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stocks: List<StockHistory>)

    @Update
    suspend fun updateStock(stock: StockHistory)

    @Delete
    suspend fun deleteStock(stock: StockHistory)

    @Query("DELETE FROM stock_history")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM stock_history")
    fun getStockCount(): Flow<Int>

    @Query("SELECT * FROM stock_history ORDER BY date DESC")
    fun getAllStocks(): Flow<List<StockHistory>>

    @Query("SELECT * FROM stock_history WHERE symbol = :symbol ORDER BY date DESC")
    fun getStockBySymbol(symbol: String): Flow<List<StockHistory>>
}
