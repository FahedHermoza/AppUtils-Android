package com.fahedhermoza.developer.examplenote01.data

import androidx.lifecycle.LiveData
import com.fahedhermoza.developer.examplenote01.Models.Clinic

interface ClinicRepository {

    fun getSavedNotes(): LiveData<List<Clinic>>

    fun insert(note: Clinic)

    fun delete(note: Clinic)

    fun deleteAll()

    fun update(note: Clinic)
}