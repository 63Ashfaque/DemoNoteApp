package com.example.demo_note_app.roomdb

import android.content.Context
import androidx.room.*

@Database(entities = [NoteDataClass::class], version = 1)
@TypeConverters(Converters::class)
abstract class DataBaseName : RoomDatabase() {

    abstract fun interfaceDao(): InterfaceDAO

    companion object {

        @Volatile
        private var INSTANCE: DataBaseName? = null
        fun getDataBase(context: Context): DataBaseName {
            if (INSTANCE == null) {
                synchronized(this)
                {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBaseName::class.java,
                        "DataBaseName"
                    ).build()
                }

            }
            return INSTANCE!!
        }
    }
}