package com.example.ryo.matchingapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by yuki19920716 on 2015/05/29.
 */
public class ContactedUser {
    private String email;
    private String password;
    private String other_email;
    private String other_password;

    public ContactedUser(){
        email = "";
        password = "";
        other_email = "";
        other_password = "";
    }

    // Setter
    public void setEmail(String s){
        email = s;
    }
    public void setOther_email(String s){
        other_email = s;
    }
    public void setPassword(String s){
        password = s;
    }
    public void setOther_password(String s){
        other_password = s;
    }

    // Getter
    public String getEmail(){
        return email;
    }
    public String getOther_email(){
        return other_email;
    }
    public String getPassword(){
        return password ;
    }
    public String getOther_password(){
        return other_password;
    }
    public void insertAContactedUserIntoDB(UserOpenHelper userOpenHelper){
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        ContentValues newContactedUser = new ContentValues();

        newContactedUser.put(UserContract.Contacts.COL_EMAIL, email);
        newContactedUser.put(UserContract.Contacts.COL_PASSWORD, password);
        newContactedUser.put(UserContract.Contacts.COL_OTHER_EMAIL, other_email);
        newContactedUser.put(UserContract.Contacts.COL_OTHER_PASSWORD, other_password);

        long newId = db.insert(
                UserContract.Contacts.TABLE_NAME,
                null,
                newContactedUser
        );
        db.close();
    }
}

