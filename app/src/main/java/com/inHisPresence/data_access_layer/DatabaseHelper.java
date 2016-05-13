package com.inHisPresence.data_access_layer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.inHisPresence.model.File;
import com.inHisPresence.model.Tag;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Database helper class for CRUD operations on SQlite db.
 * Created by Betsy on 2/17/2016.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "nearerToThee.sqlite";

    // Table Names
    private static final String TABLE_FILES = "files";
    private static final String TABLE_TAGS = "tags";
    private static final String TABLE_KEYWORDS = "keywords";
    private static final String TABLE_KEYWORD_TAGS = "keyword_tags";

    // Common column names
    private static final String KEY_ID = "_id";

    // FILES Table - column names
    private static final String KEY_FILE_NAME = "file_name";
    private static final String KEY_FILE_TITLE = "title";
    private static final String KEY_FILE_IS_FAVORITE = "is_favorite";
    private static final String KEY_TAG_ID = "tag_id";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // KEYWORDS Table - column names
    private static final String KEY_KEYWORD_NAME = "keyword";

    // KEYWORD_TAGS Table - column names
    private static final String KEY_KEYWORD_ID = "keyword_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * get single file
     */
    public File getFile(long file_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FILES + " WHERE "
                + KEY_ID + " = " + file_id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        File file = new File();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                file.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                file.setFileName((cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME))));
                file.setFileTitle((cursor.getString(cursor.getColumnIndex(KEY_FILE_TITLE))));
                int isFavorite = cursor.getInt(cursor.getColumnIndex(KEY_FILE_IS_FAVORITE));
                file.setIsFavorite((isFavorite == 1) ? true : false);
            }
            cursor.close();
        }

        db.close();
        return file;
    }

    public boolean getIsFavorite(String fileName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FILES + " WHERE "
                + KEY_FILE_NAME + " = '" + fileName + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int isFavorite = cursor.getInt(cursor.getColumnIndex(KEY_FILE_IS_FAVORITE));
                return (isFavorite == 1);
            }
            cursor.close();
        }

        db.close();
        return false;
    }

    /*
     * getting all files
     * */
    public List<File> getAllFiles() {
        List<File> files = new ArrayList<File>();
        String selectQuery = "SELECT  * FROM " + TABLE_FILES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    File file = new File();
                    file.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    file.setFileName((cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME))));
                    file.setFileTitle((cursor.getString(cursor.getColumnIndex(KEY_FILE_TITLE))));
                    int isFavorite = cursor.getInt(cursor.getColumnIndex(KEY_FILE_IS_FAVORITE));
                    file.setIsFavorite((isFavorite == 1) ? true : false);

                    // adding to file list
                    files.add(file);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return files;
    }

    public ArrayList<File> getFilesFromKeyword(String keyword) {

        ArrayList<File> files = new ArrayList<File>();

        if (keyword.trim().equals("") || keyword == null) {
            return files;
        }

        String selectQuery = "SELECT f." + KEY_ID + ", f." + KEY_FILE_NAME + ", f." + KEY_FILE_TITLE + ", f."
                + KEY_FILE_IS_FAVORITE + ", f." + KEY_TAG_ID
                + " FROM " + TABLE_KEYWORDS + " k"
                + " JOIN " + TABLE_KEYWORD_TAGS + " kt ON kt." + KEY_KEYWORD_ID + " = k." + KEY_ID
                + " JOIN " + TABLE_TAGS + " t ON t." + KEY_ID + " = kt." + KEY_TAG_ID
                + " JOIN " + TABLE_FILES + " f ON f." + KEY_TAG_ID + " = t." + KEY_ID
                + " WHERE k." + KEY_KEYWORD_NAME + " LIKE  \"%" + keyword + "%\""
                + " GROUP BY f." + KEY_FILE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    File file = new File();
                    file.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    file.setFileName((cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME))));
                    file.setFileTitle((cursor.getString(cursor.getColumnIndex(KEY_FILE_TITLE))));
                    file.setIsFavorite(cursor.getInt(cursor.getColumnIndex(KEY_FILE_IS_FAVORITE))==1);
                    file.setFileTagId(cursor.getInt(cursor.getColumnIndex(KEY_TAG_ID)));
                    // adding to file list
                    files.add(file);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return files;
    }

    /*
     * getting all files
     * */
    public ArrayList<File> getAllFavoriteFiles() {
        ArrayList<File> files = new ArrayList<File>();
        String selectQuery = "SELECT  * FROM " + TABLE_FILES + " WHERE " +
                KEY_FILE_IS_FAVORITE + " = 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    File file = new File();
                    file.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    file.setFileName((cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME))));
                    file.setFileTitle((cursor.getString(cursor.getColumnIndex(KEY_FILE_TITLE))));
                    int isFavorite = cursor.getInt(cursor.getColumnIndex(KEY_FILE_IS_FAVORITE));
                    file.setIsFavorite((isFavorite == 1));

                    // adding to file list
                    files.add(file);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return files;
    }

    /*
     * getting all files under single tag
     * */
    public ArrayList<File> getAllFilesByTag(String tag_name) {
        ArrayList<File> files = new ArrayList<File>();

        String selectQuery = "SELECT  * FROM " + TABLE_FILES + " tf, "
                + TABLE_TAGS + " tt WHERE tt."
                + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tf." + KEY_TAG_ID + " = "
                + "tt." + KEY_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    File file = new File();
                    file.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    file.setFileName((cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME))));
                    file.setFileTitle((cursor.getString(cursor.getColumnIndex(KEY_FILE_TITLE))));
                    int isFavorite = cursor.getInt(cursor.getColumnIndex(KEY_FILE_IS_FAVORITE));
                    file.setIsFavorite((isFavorite == 1) ? true : false);

                    // adding to file list
                    files.add(file);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
        return files;
    }

    /*
     * Updating a file using fileName
     * isFavorite : 1 -> true
     *              0 -> false
     */
    public int updateFile(String fileName, int isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name should not be null or empty");
        }

        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put(KEY_FILE_IS_FAVORITE, isFavorite);

        String where = KEY_FILE_NAME + " = ?";
        String[] whereArgs = new String[]{ fileName };

        int rowsAffected = db.update(TABLE_FILES, valuesToInsert, where, whereArgs);

        db.close();
        return rowsAffected;
    }

    /**
     * getting all tags
     * */
    public ArrayList<Tag> getAllTags() {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAGS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Tag tag = new Tag();
                    tag.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));
                    tag.setTagName(cursor.getString(cursor.getColumnIndex(KEY_TAG_NAME)));

                    // adding to tags list
                    tags.add(tag);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return tags;
    }

    /**
     * getting all keywords
     * */
    public ArrayList<String> getAllKeywords() {
        ArrayList<String> keywords = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_KEYWORDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                   // adding to keywords list
                   keywords.add(cursor.getString(cursor.getColumnIndex(KEY_KEYWORD_NAME)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return keywords;
    }

    /*
     * Updating a file tag
     */
    public int updateFileTag(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag_id);

        // updating row
        int rowsAffected = db.update(TABLE_FILES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id) });
        db.close();
        return rowsAffected;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
