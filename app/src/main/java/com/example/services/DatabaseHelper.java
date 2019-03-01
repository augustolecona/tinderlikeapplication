package com.example.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String  TAG = "DatabaseHelper";
    private static final String TableName="people_table";
    private static final String COL1= "ID";
    private static final String  COL2="name";
    private static final String  COL3="gender";
    private static final String  COL4="location";
    private static final String  COL5="email";
    private static final String  COL6="phone";

    public DatabaseHelper(Context context)
    {
        super(context,TableName,null,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +  COL3 + " TEXT, " +
                COL4 + " TEXT, " + COL5 + " TEXT, " + COL6 + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + TableName;


        db.execSQL(dropTable);

        onCreate (db);
    }


    public boolean SaveUser(String[] item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item[0]);
        contentValues.put(COL3, item[1]);
        contentValues.put(COL4, item[2]);
        contentValues.put(COL5, item[3]);
        contentValues.put(COL6, item[4]);
        Log.d(TAG, "Saving User: " + item + " to " + TableName);
        long result = db.insert(TableName, null, contentValues);
        db.close();
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectUsers ="SELECT * FROM " +  TableName;
        Cursor users =db.rawQuery(selectUsers, null);
        return users;
    }

    public Cursor getUserData( String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectuserbyID ="SELECT * FROM " +  TableName + " WHERE " + COL2 + " = '" + name + "'";
        Cursor userdata =db.rawQuery(selectuserbyID, null);
        return userdata;
    }

}
