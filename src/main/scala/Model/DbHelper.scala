package com.scratch

import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.Context

object DbHelper {
    val DATABASE_VERSION = 1
    val DATABASE_NAME = "scratch.db"
    val TABLE_NAME = "scratch"
    
    val COLUMN_ID = "_id"
    val COLUMN_COMMENT = "comment"

    val DATABASE_CREATE = 
        s"create table $TABLE_NAME ($COLUMN_ID integer primary key autoincrement, $COLUMN_COMMENT text not null);"
}

class DbHelper(context: Context) extends 
    SQLiteOpenHelper(context, DbHelper.DATABASE_NAME, 
        null, DbHelper.DATABASE_VERSION) {

    override def onCreate(database: SQLiteDatabase) = {
        database execSQL DbHelper.DATABASE_CREATE
    }

    override def onUpgrade(database: SQLiteDatabase, 
        oldVersion: Int, newVersion: Int) = {

        database.execSQL("DROP TABLE IF EXISTS " + DbHelper.TABLE_NAME)
        onCreate(database)
    }

}

