package com.example.demo_note_app.roomdb

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun fromDateToLong(value: Date): Long {
        return value.time
    }

    @TypeConverter
    fun formLongToDate(value: Long): Date {
        return Date(value)
    }
}