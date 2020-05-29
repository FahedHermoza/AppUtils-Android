package com.fahedhermoza.developer.examplenote01.Models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ClinicDao {
    @Query("SELECT * from clinic_table ORDER BY title DESC")
    fun getAllClinics(): LiveData<List<Clinic>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(clinic: Clinic)

    @Query("DELETE FROM clinic_table WHERE title = :title")
    fun delete(title: String?)

    @Query("DELETE FROM clinic_table")
    fun deleteAll()

    @Update
    fun update(clinic: Clinic)
}


