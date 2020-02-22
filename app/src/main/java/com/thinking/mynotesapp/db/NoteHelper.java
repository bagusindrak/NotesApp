package com.thinking.mynotesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.thinking.mynotesapp.db.DatabaseContract.TABLE_NAME;

public class NoteHelper {
   private static final String DATABASE_TABLE = TABLE_NAME;
   private static DatabaseHelper dataBaseHelper;
   private static NoteHelper INSTANCE;

   private static SQLiteDatabase database;

   private NoteHelper(Context context) {
      dataBaseHelper = new DatabaseHelper(context);
   }

   //   menginisiasi database.
   public static NoteHelper getInstance(Context context) {
      if (INSTANCE == null) {
         synchronized (SQLiteOpenHelper.class) {
            if (INSTANCE == null) {
               INSTANCE = new NoteHelper(context);
            }
         }
      }
      return INSTANCE;
   }

   //   membuka dan menutup koneksi ke database-nya.
   public void open() throws SQLException {
      database = dataBaseHelper.getWritableDatabase();
   }

   public void close() {
      dataBaseHelper.close();
      if (database.isOpen())
         database.close();
   }

   //   mengambil data.
   public Cursor queryAll() {
      return database.query(
              DATABASE_TABLE,
              null,
              null,
              null,
              null,
              null,
              _ID + " DESC");
   }

   //   mengambal data dengan id tertentu.
   public Cursor queryById(String id) {
      return database.query(
              DATABASE_TABLE,
              null,
              _ID + " = ?",
              new String[]{id},
              null,
              null,
              null,
              null);
   }

   //   menyimpan data.
   public long insert(ContentValues values) {
      return database.insert(DATABASE_TABLE, null, values);
   }

   //   memperbaharui data.
   public int update(String id, ContentValues values) {
      return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
   }

   //   menghapus data.
   public int deleteById(String id) {
      return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
   }
}
