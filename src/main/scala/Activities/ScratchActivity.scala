package com.scratch

import android.app.Activity
import android.app.ListActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SimpleCursorAdapter
import android.app.LoaderManager
import android.content.Loader
import android.database.Cursor
import android.content.CursorLoader
import android.content.ContentValues


class ScratchActivity extends ListActivity with TypedActivity
    with OptionsMenu with LoaderManager.LoaderCallbacks[Cursor]{

    val URL_LOADER = 0
    lazy val cursorAdapter = new SimpleCursorAdapter(this, 
        android.R.layout.simple_list_item_1, null, 
        Array(DbHelper.COLUMN_COMMENT), 
        Array(android.R.id.text1))
        
    override def onCreate(bundle: Bundle) {
        super.onCreate(bundle)
        setContentView(R.layout.main)

        setListAdapter(cursorAdapter)
        getLoaderManager().initLoader(URL_LOADER, null, this)

    }

    override def onCreateLoader(loaderId: Int, bundle: Bundle): Loader[Cursor] = {
        new CursorLoader(this, 
            DataModel.CONTENT_URI, // table URL
            Array(DbHelper.COLUMN_ID, DbHelper.COLUMN_COMMENT), 
            null, null, null)
    }

    override def onLoadFinished(loader: Loader[Cursor], cursor: Cursor) {
        cursorAdapter changeCursor cursor
    }

    override def onLoaderReset(loader: Loader[Cursor]) {
        cursorAdapter changeCursor null
    }
}

trait OptionsMenu extends Activity {
    override def onCreateOptionsMenu(menu: Menu): Boolean = {
        menu.addSubMenu(10, 0, 0, "Test")
        true
    }

    override def onOptionsItemSelected(menuItem: MenuItem): Boolean = menuItem.getItemId match {
        case 0 => {
            val contentValues = new ContentValues()
            contentValues.put(DbHelper.COLUMN_COMMENT, "Hello")
            getContentResolver.insert(DataModel.CONTENT_URI, contentValues)
            true
        }
        case _ => false
    }

}
