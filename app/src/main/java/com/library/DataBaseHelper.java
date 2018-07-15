package com.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class DataBaseHelper extends SQLiteOpenHelper{
    private Context context;
    private SQLiteDatabase db;
    private final static String DB_NAME = "Library.db";
    private static String DB_PATH;
    public final static int DB_VERSION = 1;
    private static DataBaseHelper databaseHelper;

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    public static DataBaseHelper getInstance(Context context) {
        if(databaseHelper == null) {
            databaseHelper = new DataBaseHelper(context);
        }
        return databaseHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");
        }

    }
    public void openDatabase() {
        try {
            String path = DB_PATH;
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void closeDatabase() {
        if(db!=null)
            db.close();
        super.close();
    }
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        Cursor c = null;
        try{
            String path = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            checkDB = null;
            e.printStackTrace();
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }
    public void createDatabase() {
        if(!checkDataBase()) {
            this.getWritableDatabase();
            try {
                this.close();
                InputStream myInput = context.getAssets().open(DB_NAME);
                String outFileName = DB_PATH;
                OutputStream myOutput = new FileOutputStream(outFileName);
                int ch;
                while((ch=myInput.read())!=-1) {
                    myOutput.write(ch);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List<String> getCategories() {
        this.openDatabase();
        try {
            List<String> list = new ArrayList<>();
            Cursor c = db.rawQuery("SELECT category FROM cate_gory ORDER BY category", null);
            int category = c.getColumnIndex("category");
            c.moveToFirst();
            do {
                list.add(c.getString(category));
            }
            while (c.moveToNext());
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.closeDatabase();
        return null;
    }
    public LinkedHashMap<String, List<String>> fetchAll() {
        this.openDatabase();
        try {
            LinkedHashMap<String, List<String>> bookByCategory = new LinkedHashMap<>();
            List<String> categories = getCategories();
            for (String category : categories) {
                List<String> books = new ArrayList<>();
                Cursor c = db.rawQuery("SELECT category, bookname FROM book INNER JOIN cate_gory ON cate_gory._id=book.cate_id where cate_gory.category=" +"'" + category + "'" + "", null);
                int category_name = c.getColumnIndex("category");
                int bookname = c.getColumnIndex("bookname");
                c.moveToFirst();
                do {
                    books.add(c.getString(bookname));
                } while (c.moveToNext());

                c.moveToFirst();
                bookByCategory.put(c.getString(category_name), books);
            }
            return bookByCategory;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.closeDatabase();
        return null;
    }
}
