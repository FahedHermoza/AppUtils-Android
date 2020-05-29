package com.fahed.developer.permissions.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.fahedhermoza.developer.examplenote01.Models.Clinic
import com.fahedhermoza.developer.examplenote01.Models.ClinicRepositoryImpl
import com.fahedhermoza.developer.examplenote01.data.ClinicRepository
import java.util.HashSet

class ClinicsListViewModel(private val repository: ClinicRepository = ClinicRepositoryImpl()): ViewModel() {

    private val allClinics = MediatorLiveData<List<Clinic>>()

    init{
        getAllClinics()
    }

    fun getSavedNotes() = allClinics

    private fun getAllClinics() {
        allClinics.addSource(repository.getSavedNotes()) { notes ->
            allClinics.postValue(notes)
        }
    }

    fun insertAllItems(clinics: MutableList<Clinic>){
        for(clinic in clinics){
            repository.insert(clinic)
        }
    }

    fun deleteTapped(selectedNotes: HashSet<*>) {
        for (clinic in selectedNotes) {
            repository.delete(clinic as Clinic)
        }
    }

    fun deleteAllItems() {
        repository.deleteAll()
    }
}