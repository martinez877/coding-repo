package com.cmarti21.shoppinglist.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

@Database(entities = [Item::class], version = 2)
@TypeConverters(Converters::class)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {

        val defaultUUID: String = UUID.randomUUID().toString()
        database.execSQL(
            "ALTER TABLE items_table ADD COLUMN uuid TEXT NOT NULL DEFAULT '$defaultUUID'"
        )
    }
}