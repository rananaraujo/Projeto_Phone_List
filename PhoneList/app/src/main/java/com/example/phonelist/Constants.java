package com.example.phonelist;

public class Constants {
    public static final String DATABASE_NAME = "CONTACT_DB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "CONTACT_TABLE";
    public static final String C_ID = "ID";
    public static final String C_NAME = "NAME";
    public static final String C_APELIDO = "APELIDO";
    public static final String C_NUMERO = "NUMERO";
    public static final String C_EMAIL = "EMAIL";
    public static final String C_CPF = "CPF";
    public static final String C_ADDED_TIME_CADASTRO = "ADDED_TIME_CADASTRO";
    public static final String C_UPDATED_TIME_ALTERADO = "UPDATED_TIME_ALTERADO";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT, "
            + C_APELIDO + " TEXT, "
            + C_NUMERO + " TEXT, "
            + C_EMAIL + " TEXT, "
            + C_CPF + " TEXT, "
            + C_ADDED_TIME_CADASTRO + " TEXT, "
            + C_UPDATED_TIME_ALTERADO + " TEXT"
            + " );";
}