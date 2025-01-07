package com.android.bg_farmers.data.repositories

import com.android.bg_farmers.data.FarmerDAO
import com.android.bg_farmers.data.models.Farmer
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class FarmerRepository @Inject constructor(
    private val farmerDAO: FarmerDAO
) {

    val getAllFarmers: Flow<List<Farmer>> = farmerDAO.getAllFarmers()

    fun getSelectedFarmer(farmerId: Int) : Flow<Farmer> {
        return farmerDAO.getSelectedFarmer(farmerId)
    }

    suspend fun addFarmer(farmer: Farmer) {
        farmerDAO.addFarmer(farmer)
    }

    suspend fun updateFarmer(farmer: Farmer) {
        farmerDAO.updateFarmer(farmer)
    }

    suspend fun deleteFarmer(farmer: Farmer) {
        farmerDAO.deleteFarmer(farmer)
    }

    suspend fun deleteAllFarmers() {
        farmerDAO.deleteAllFarmers()
    }

    fun searchDatabase(searchQuery: String) : Flow<List<Farmer>> {
        return farmerDAO.searchDatabase(searchQuery)
    }

}