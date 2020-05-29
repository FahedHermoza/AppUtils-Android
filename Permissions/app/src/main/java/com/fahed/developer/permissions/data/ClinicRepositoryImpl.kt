package com.fahedhermoza.developer.examplenote01.Models

import android.util.Log
import androidx.lifecycle.LiveData
import com.fahed.developer.permissions.db
import com.fahedhermoza.developer.examplenote01.data.ClinicRepository
import kotlin.concurrent.thread

open class ClinicRepositoryImpl : ClinicRepository {

    private val noteDao: ClinicDao = db.clinicsDao()
    open val allNotes: LiveData<List<Clinic>>

    init {
        allNotes = noteDao.getAllClinics()
    }

    override fun getSavedNotes(): LiveData<List<Clinic>> {
        return allNotes
    }

    override fun insert(clinic: Clinic) {
        thread {
            noteDao.insert(clinic)
            Log.e("TAG", "INSERTADO")
        }
    }

    override fun delete(clinic: Clinic) {
        thread {
            noteDao.delete(clinic.title)
        }
    }

    override fun deleteAll() {
        thread {
            noteDao.deleteAll()
        }
    }

    override fun update(clinic: Clinic) {
        thread {
            noteDao.update(clinic)
            Log.e("TAG", "ACTUALIZADO")
        }
    }

}