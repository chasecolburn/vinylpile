package com.mindtwisted.vinylpile.helper

import android.content.Context
import com.j256.ormlite.dao.Dao
import com.mindtwisted.vinylpile.model.Record
import java.sql.SQLException

class QueryHelper {
    companion object {
        @JvmStatic
        fun fetchRecords(context : Context): List<Record>? {
            try {
                val dao: Dao<Record, Int> = DatabaseHelper.getSharedDao(context, Record::class.java)
                return dao.queryForAll()
            } catch (e: SQLException) {
                Logger.e(e)
            }
            return ArrayList()
        }

        @JvmStatic
        fun saveRecord(context : Context, record : Record) {
            try {
                val dao: Dao<Record, Int> = DatabaseHelper.getSharedDao(context, Record::class.java)
                dao.create(record)
            } catch (e: SQLException) {
                Logger.e(e)
            }
        }
    }
}