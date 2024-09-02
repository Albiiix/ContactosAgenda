package com.example.contactosagenda;

public class Database {

    public static final String DATABASE_NAME = "CONTACTS_DB";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "CONTACT_TABLE";
    public static final String ID = "ID";
    public static final String IMAGE = "IMAGE";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String EMAIL = "EMAIL";
    public static final String DIR = "DIR";
    public static final String NOTE = "NOTE";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + IMAGE + " TEXT, "
            + NAME + " TEXT NOT NULL, "
            + PHONE + " TEXT NOT NULL, "
            + EMAIL + " TEXT, "
            + DIR + " TEXT, "
            + NOTE + " TEXT"+ ");";
}
