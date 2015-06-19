package com.example.ryo.matchingapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ryo on 6/11/2015.
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    public UserOpenHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    public static final String DB_NAME = "matchingApp.db";
    public static final int DB_VERSION = 4;



    public static final String CREATE_USER_TABLE =
            "create table " + UserContract.Users.TABLE_NAME + " (" +
                    UserContract.Users.COL_EMAIL + " text, " +
                    UserContract.Users.COL_PASSWORD + " text, " +
                    UserContract.Users.COL_NAME + " text, " +
                    UserContract.Users.COL_SUMMARY + " text, " +
//                    UserContract.Users.COL_IMAGE + " integer, " +  //??????????????????????????????????????integer or imageBitmap ???
                    UserContract.Users.COL_LONGITUDE + " integer, " +
                    UserContract.Users.COL_LATITUDE + " integer, " +
                    "primary key (" + UserContract.Users.COL_EMAIL + ", " + UserContract.Users.COL_PASSWORD + "))";

    public static final String CREATE_CONTACT_TABLE =
            "create table " + UserContract.Contacts.TABLE_NAME + " (" +
                    UserContract.Contacts.COL_EMAIL + " text, " +
                    UserContract.Contacts.COL_PASSWORD + " text, " +
                    UserContract.Contacts.COL_OTHER_EMAIL + " text, " +
                    UserContract.Contacts.COL_OTHER_PASSWORD + " text," +
                    "primary key (" + UserContract.Contacts.COL_EMAIL + ", " + UserContract.Contacts.COL_PASSWORD +
                    ", " + UserContract.Contacts.COL_OTHER_EMAIL + ", " + UserContract.Contacts.COL_OTHER_PASSWORD + "))";

    public static final String CREATE_MESSAGE_TABLE =
            "create table " + UserContract.Messages.TABLE_NAME + " (" +
                    UserContract.Messages.COL_NAME + " text, " +
                    UserContract.Messages.COL_MESSAGE + " text, " +
                    UserContract.Messages.COL_TIME + " datetime default current_timestamp, " +
//                    UserContract.Messages.COL_IMAGE + " text, " +
                    UserContract.Messages.COL_EMAIL + " text, " +
//                    UserContract.Messages.COL_PASSWORD + " text, " +
                    UserContract.Messages.COL_OTHER_EMAIL + " text) ";
//                    UserContract.Messages.COL_OTHER_PASSWORD + " text)";

    public static final String DROP_USER_TABLE =
            "drop table if exists " + UserContract.Users.TABLE_NAME;
    public static final String DROP_CONTACT_TABLE =
            "drop table if exists " + UserContract.Contacts.TABLE_NAME;
    public static final String DROP_MESSAGE_TABLE =
            "drop table if exists " + UserContract.Messages.TABLE_NAME;


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
        sqLiteDatabase.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        sqLiteDatabase.execSQL(DROP_CONTACT_TABLE);
        sqLiteDatabase.execSQL(DROP_MESSAGE_TABLE);
        onCreate(sqLiteDatabase);
    }


}
