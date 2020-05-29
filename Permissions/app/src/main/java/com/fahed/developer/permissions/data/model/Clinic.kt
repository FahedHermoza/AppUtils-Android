package com.fahedhermoza.developer.examplenote01.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "clinic_table")
class Clinic(@PrimaryKey @ColumnInfo(name = "title") val title: String,
             @ColumnInfo(name = "location") val location: String,
             @ColumnInfo(name = "date") val phone: String
)