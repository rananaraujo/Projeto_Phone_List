package com.example.phonelist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertContact(String name, String apelido, String numero, String cpf, String email,
                              String time_cadastro, String time_update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_APELIDO, apelido);
        contentValues.put(Constants.C_NUMERO, numero);
        contentValues.put(Constants.C_CPF, cpf);
        contentValues.put(Constants.C_EMAIL, email);
        contentValues.put(Constants.C_ADDED_TIME_CADASTRO, time_cadastro);
        contentValues.put(Constants.C_UPDATED_TIME_ALTERADO, time_update);

        long id = db.insert(Constants.TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.C_NAME + " ASC", null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Constants.C_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(Constants.C_NAME)));
                contact.setApelido(cursor.getString(cursor.getColumnIndex(Constants.C_APELIDO)));
                contact.setNumber(cursor.getString(cursor.getColumnIndex(Constants.C_NUMERO)));
                contact.setCpf(cursor.getString(cursor.getColumnIndex(Constants.C_CPF)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL)));
                contact.setTimeCadastro(cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIME_CADASTRO)));
                contact.setTimeUpdate(cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIME_ALTERADO)));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateTime = String.valueOf(System.currentTimeMillis());

        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME, contact.getName());
        values.put(Constants.C_APELIDO, contact.getApelido());
        values.put(Constants.C_NUMERO, contact.getNumber());
        values.put(Constants.C_CPF, contact.getCpf());
        values.put(Constants.C_EMAIL, contact.getEmail());
        values.put(Constants.C_UPDATED_TIME_ALTERADO, updateTime);

        return db.update(Constants.TABLE_NAME, values, Constants.C_ID + " = ?", new String[]{String.valueOf(contact.getId())});
    }

    public void deleteContact(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @SuppressLint("Range")
    public List<Contact> getContactsOrderedByRegistrationDate() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.C_ADDED_TIME_CADASTRO + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Constants.C_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(Constants.C_NAME)));
                contact.setApelido(cursor.getString(cursor.getColumnIndex(Constants.C_APELIDO)));
                contact.setNumber(cursor.getString(cursor.getColumnIndex(Constants.C_NUMERO)));
                contact.setCpf(cursor.getString(cursor.getColumnIndex(Constants.C_CPF)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL)));
                contact.setTimeCadastro(cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIME_CADASTRO)));
                contact.setTimeUpdate(cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIME_ALTERADO)));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contacts;
    }

    @SuppressLint("Range")
    public List<Contact> getContactsOrderedByUpdateDate() {
        List<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + Constants.C_UPDATED_TIME_ALTERADO + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(cursor.getColumnIndex(Constants.C_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(Constants.C_NAME)));
                contact.setApelido(cursor.getString(cursor.getColumnIndex(Constants.C_APELIDO)));
                contact.setNumber(cursor.getString(cursor.getColumnIndex(Constants.C_NUMERO)));
                contact.setCpf(cursor.getString(cursor.getColumnIndex(Constants.C_CPF)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL)));
                contact.setTimeCadastro(cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIME_CADASTRO)));
                contact.setTimeUpdate(cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIME_ALTERADO)));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contacts;
    }
}