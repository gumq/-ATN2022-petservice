package co.kaua.palacepetz.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import co.kaua.palacepetz.Data.model.Pet;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table User(Name text primary key,Password text)");

        db.execSQL("Create table Cart(TenSP text primary key, Gia text, SoLuong text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists User");

        db.execSQL("Drop table if exists Cart");
    }

    public boolean insert(String Name, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", Name);
        contentValues.put("Password", Password);
        long ins = db.insert("User", null,contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public boolean checkName(String Name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where Name=?",new String[]{Name});
        if (cursor.getCount()>0) return false;
        else return true;
    }


    public boolean checklogin(String Name, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where Name=? and Password=?",new String[]{Name,Password});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public int insertAuthor(Pet pet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenSP",pet.getName());
        contentValues.put("Gia",pet.getGia());
        contentValues.put("SoLuong",pet.getSoLuong());
        int result = (int)db.insert("Cart",null,contentValues);
        db.close();
        return result;
    }

    public boolean checkCart(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Cart where TenSP=?",new String[]{name});
        if (cursor.getCount()>0) return true;
        else return false;
    }

    public int updateAuthor(Pet pet, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Gia",pet.getGia());
        contentValues.put("SoLuong",pet.getSoLuong());
        int result = (int)db.update("Cart",contentValues, "TenSP=?", new String[]{name});
        db.close();
        return result;
    }

    public int deleteAuthor(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = (int)db.delete("Cart","TenSP=?", new String[]{name});
        db.close();
        return result;
    }
    public int deleteAllAuthor(){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = (int)db.delete("Cart", null,null);
        db.close();
        return result;
    }


    public ArrayList<Pet> getPet(){
        ArrayList<Pet> petArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Cart", null);
        while (cursor.moveToNext()){
            String ten = cursor.getString(0);
            String gia = cursor.getString(1);
            String soLuong = cursor.getString(2);
            Pet pet = new Pet(ten,gia,soLuong);

            petArrayList.add(pet);
        }
        return petArrayList;
    }
    public boolean checkExisst(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Cart",null);
        if (cursor.getCount()>0) return false;
        else return true;
    }
}
