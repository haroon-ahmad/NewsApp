package com.example.assignment_1;

/**
 * Created by Haroon on 10/22/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class DatabaseHandler extends SQLiteOpenHelper implements Serializable{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "savednews";

    // Contacts table name
    private static final String TABLE_SAVEDNEWS = "news";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LINK = "link";
    private static final String KEY_DATE = "date";

    Context c;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SAVEDNEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT," + KEY_LINK + " TEXT," + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Toast.makeText(c, "In On Create", Toast.LENGTH_LONG).show();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVEDNEWS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addNews(myDataClass object) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, object.getTitle()); // Title
        values.put(KEY_DESCRIPTION, object.getDes()); // Description
        values.put(KEY_LINK, object.getLink()); // Link
        values.put(KEY_DATE, object.getDate()); // Date



        try
        {
            // Inserting Row
            db.insert(TABLE_SAVEDNEWS, null, values);
            Toast.makeText(c,"Inserted",Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        db.close(); // Closing database connection
    }

    // Getting single contact
   /* myDataClass getnews(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SAVEDNEWS, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESCRIPTION,KEY_LINK,KEY_DATE }, KEY_ID + "=? and" + KEY_TITLE + "=?" +KEY_DESCRIPTION + "=?" +KEY_LINK + "=?" +KEY_DATE + "=?" ,
                new String[] { String.valueOf(id),"abc" }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        myDataClass object = new myDataClass(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return object;
    }*/

    // Getting All Contacts
    public ArrayList<myDataClass> getAllNews()
    {
        ArrayList<myDataClass> newsList = new ArrayList<myDataClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SAVEDNEWS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                myDataClass news = new myDataClass();
                news.setID(Integer.parseInt(cursor.getString(0)));
                news.setTitle(cursor.getString(1));
                news.setDes(cursor.getString(2));
                news.setLink(cursor.getString(3));
                news.setDate(cursor.getString(4));
                // Adding contact to list
                newsList.add(news);
            } while (cursor.moveToNext());
            Toast.makeText(c,"in select all",Toast.LENGTH_LONG).show();
        }
        db.close();

        // return contact list
        return newsList;
    }

    // Updating single contact
   /* public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });

    }*/

    // Deleting single contact
   /* public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }*/

    // Getting contacts Count
   /* public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }*/
}
