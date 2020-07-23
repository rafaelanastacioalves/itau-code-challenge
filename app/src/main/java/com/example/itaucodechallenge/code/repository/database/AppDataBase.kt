package com.example.itaucodechallenge.code.repository.database;

import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase;
import com.example.itaucodechallenge.code.domain.entities.Repository;


@Database(entities = [Repository::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDAO(): DAO

    companion object {

        val databaseName : String = "applicationDB"
        private lateinit var context: Context
        private val INSTANCE: AppDataBase by lazy {
            synchronized(this) {
                buildDatabase(context)
            }
        }

        fun setupAtApplicationStartup(context: Context) {
            this.context =context
        }

        fun getInstance(): AppDataBase {
            return INSTANCE
        }


        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context.applicationContext,
                    AppDataBase::class.java,
                    databaseName).build()
        }
    }
}
