package com.ss_technology.hdmobilebackground.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Helper extends SQLiteOpenHelper {

    private static final String dbName="imageshd.db";
    private static final int dbVersion=1;
    private static final String tbName="fav";

    public DB_Helper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS fav(id INTEGER PRIMARY KEY,object blob not null unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+dbName);
        onCreate(db);
    }

    public boolean setData(int id ,byte[] employeeAsBytes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("object", employeeAsBytes);
        Long result= db.insert("fav", null, contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from fav;", null );
        return res;
    }

    public void delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("fav",null,null);
    }

    public void deleteCart()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart",null,null);
    }

    public void updateQty(int qty,String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("qty",qty);
        db.update("cart",values,"id = ?", new String[]{id});
    }

    public boolean DeleteSingleItem(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("fav", "id" + "=" + id, null) > 0;
    }

    public boolean DeleteSingleItemCart(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cart", "id" + "=" + id, null) > 0;
    }

    public void close()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }


}
