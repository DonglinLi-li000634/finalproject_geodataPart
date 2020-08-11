package com.example.finalproject_geodatapart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBOpener extends SQLiteOpenHelper {
    protected final static String DB_NAME="GeoDB";
    protected final static int DB_Version=2;
    public final static String TABLE_NAME="GeoTable";
    public final static String COL_COUNTRY="COUNTRY";
    public final static String COL_CITY="CITY",COL_REGION="REGION",COL_CURRENCY="CURRENCY",COL_LAT="LAT",COL_LON="LON",COL_INFO="INFO";
    public final static String COL_ID="_ID";

    public MyDBOpener(Context ctx){
        super(ctx,DB_NAME,null,DB_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_COUNTRY+" TEXT,"+COL_CITY+" TEXT,"+COL_REGION+" TEXT,"+
                COL_CURRENCY+" TEXT,"+COL_LAT+" DOUBLE(5,4),"+COL_LON+" DOUBLE(5,4))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("DB Upgrade ","Old: "+i+" ,New: "+i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        Log.i("DB Downgrade ","Old: "+i+" ,New: "+i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
