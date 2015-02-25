package com.scratch

import android.content.ContentValues
import android.content.Context
import android.content.ContentProvider
import android.database.Cursor
import android.net.Uri
import android.database.sqlite.SQLiteQueryBuilder


object DataModel {
    val AUTHORITY = "com.scratch.datamodel"
    val BASE_PATH = "comments"
    val CONTENT_URI = Uri.parse(s"content://$AUTHORITY/$BASE_PATH");
}

class DataModel extends ContentProvider {

    lazy val dbHelper = new DbHelper(getContext())

    override def onCreate(): Boolean = {
        dbHelper
        false
    }

    override def query(uri: Uri, projection: Array[String], selection: String,
        selectionArgs: Array[String], sortOrder: String): Cursor = {

        val queryBuilder = new SQLiteQueryBuilder
        queryBuilder setTables DbHelper.TABLE_NAME

        val cursor = queryBuilder.query(dbHelper.getWritableDatabase, 
            projection, selection, selectionArgs,
            null, null, sortOrder)

        cursor.setNotificationUri(getContext().getContentResolver(), uri)
        cursor
    }

    override def insert(uri: Uri, contentValues: ContentValues): Uri = {
        dbHelper.getWritableDatabase.insert(DbHelper.TABLE_NAME, null, contentValues)
        getContext().getContentResolver().notifyChange(uri, null)
        null
    }
    override def getType(uri: Uri): String = null
    override def delete(uri: Uri, selection: String, selectionArgs: Array[String]): Int = 0
    override def update(uri: Uri, values: ContentValues, selection: String, selectionArgs: Array[String]): Int = 0
}