package com.android.bg_farmers.data

import androidx.room.*
import com.android.bg_farmers.data.models.Farmer
import kotlinx.coroutines.flow.Flow

@Dao
interface FarmerDAO {

    @Query(value = "SELECT * FROM farmers_table ORDER BY id ASC")
    fun getAllFarmers(): Flow<List<Farmer>>

    @Query(value = "SELECT * FROM farmers_table WHERE id=:farmerId")
    fun getSelectedFarmer(farmerId: Int): Flow<Farmer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFarmer(farmer: Farmer)

    @Update
    suspend fun updateFarmer(farmer: Farmer)

    @Delete
    suspend fun deleteFarmer(farmer: Farmer)

    @Query(value = "DELETE FROM farmers_table")
    suspend fun deleteAllFarmers()

    @Query(value = "SELECT * FROM farmers_table WHERE name LIKE :searchQuery OR cropType LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Farmer>>
}