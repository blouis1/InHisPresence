package com.nearerToThee.data_access_layer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Betsy on 2/17/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "nearerToTheeDB.db";
    public static final String TABLE_FILES = "files";
    public static final String TABLE_TAGS = "tags";
    public static final String TABLE_FILE_TAGS = "files_tags";
    // common column names
    public static final String COLUMN_ID = "_id";

    // FILES table - column names
    public static final String COLUMN_FILENAME = "file_name";
    public static final String COLUMN_TITLE = "title";
    public static final boolean COLUMN_IS_FAVORITE = false;

    // TAGS table - column names
    public static final String COLUMN_TAGNAME = "tag_name";

    // FILES_TAGS table - column names
    public static final String COLUMN_FILEID = "file_id";
    public static final String COLUMN_TAGID = "tag_id";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);

        // create new tables
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FILES_TABLE = "CREATE TABLE " +
                TABLE_FILES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_FILENAME
                + " TEXT," + COLUMN_TITLE + " TEXT" + ")";
        db.execSQL(CREATE_FILES_TABLE);
        String CREATE_TAGS_TABLE = "CREATE TABLE " +
                TABLE_FILES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_FILENAME
                + " TEXT," + COLUMN_TITLE + " TEXT" + ")";
        db.execSQL(CREATE_TAGS_TABLE);
        String CREATE_FILES_TAGS_TABLE = "CREATE TABLE " +
                TABLE_FILES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_FILENAME
                + " TEXT," + COLUMN_TITLE + " TEXT" + ")";
        db.execSQL(CREATE_FILES_TAGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILE_TAGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILES);
        onCreate(db);
    }

    public void addProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FILENAME, product.getProductName());
        values.put(COLUMN_TITLE, product.getQuantity());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public Product findProduct(String productname) {
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_FILENAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setID(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        } else {
            product = null;
        }
        db.close();
        return product;
    }

    public boolean deleteProduct(String productname) {

        boolean result = false;

        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_FILENAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(product.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public void deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PRODUCTS, null, null);

        db.close();
    }

    public int updateProduct(String productname, int quantity) {

        int rowsAffected = 0;

        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_FILENAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));

            ContentValues valuesToInsert = new ContentValues();
            valuesToInsert.put(COLUMN_FILENAME, productname);
            valuesToInsert.put(COLUMN_TITLE, quantity);

            String where = COLUMN_ID + " = ?";
            String[] whereArgs = new String[]{ String.valueOf(product.getID()) };

            db.update(TABLE_PRODUCTS, valuesToInsert, where, whereArgs);
            rowsAffected = product.getID();
            cursor.close();

        }
        db.close();
        return rowsAffected;


        /*if (cursor.moveToFirst()) {
            ContentValues valuesToInsert = new ContentValues();
            valuesToInsert.put(COLUMN_PRODUCTNAME, productname);
            valuesToInsert.put(COLUMN_QUANTITY, quantity);

            String where = COLUMN_ID + " = ?";
            String[] whereArgs = new String[]{ String.valueOf(productid) };

            rowsAffected = db.update(TABLE_PRODUCTS, valuesToInsert, where, whereArgs);

            cursor.close();
        } else {
            rowsAffected = 0;
        }*/


    }
}
