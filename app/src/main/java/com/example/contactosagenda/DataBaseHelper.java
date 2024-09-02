package com.example.contactosagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper{

    public DataBaseHelper(@Nullable Context context) {
        super(context, Database.DATABASE_NAME, null, Database.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Database.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Database.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertarContacto(String  name, String image, String phone, String email, String dir, String note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.NAME, name);
        contentValues.put(Database.IMAGE, image);
        contentValues.put(Database.PHONE, phone);
        contentValues.put(Database.EMAIL, email);
        contentValues.put(Database.DIR, dir);
        contentValues.put(Database.NOTE, note);

        long id= db.insert(Database.TABLE_NAME, null, contentValues);

        db.close();
        return id;
    }

    public void actualizarContacto(String id, String  name, String image, String phone, String email, String dir, String note){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.NAME, name);
        contentValues.put(Database.IMAGE, image);
        contentValues.put(Database.PHONE, phone);
        contentValues.put(Database.EMAIL, email);
        contentValues.put(Database.DIR, dir);
        contentValues.put(Database.NOTE, note);

        db.update(Database.TABLE_NAME, contentValues, Database.ID + " =? ",new String[]{id});
        db.close();
    }

    public void eliminarContacto(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Database.TABLE_NAME, Database.ID + " =? ",new String[]{id});
        db.close();
    }

    public ArrayList<ModelContact> buscarContacto(String s){

        ArrayList<ModelContact> lista_resultado = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query= "SELECT * FROM " + Database.TABLE_NAME + " WHERE "+ Database.NAME+ " LIKE '%" + s + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                ModelContact modelContact = new ModelContact(
                        ""+ cursor.getInt(cursor.getColumnIndexOrThrow(Database.ID)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.NAME)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.PHONE)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Database.DIR)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Database.NOTE))
                );
                lista_resultado.add(modelContact);

            }while (cursor.moveToNext());
        }
        db.close();
        return lista_resultado;
    }

    public ArrayList<ModelContact> getData(){

        ArrayList<ModelContact> arrayList = new ArrayList<>();
        String query = "SELECT * FROM " + Database.TABLE_NAME + " ORDER BY " + Database.NAME + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ModelContact modelContact = new ModelContact(
                        ""+ cursor.getInt(cursor.getColumnIndexOrThrow(Database.ID)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.NAME)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.PHONE)),
                        ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Database.DIR)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Database.NOTE))
                );
                arrayList.add(modelContact);

            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
