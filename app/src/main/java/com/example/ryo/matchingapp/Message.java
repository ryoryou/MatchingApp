package com.example.ryo.matchingapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

/**
 * Created by Ryo on 6/11/2015.
 */
public class Message {
    private String userName;
    private String userMessage;
    private Bitmap userImage;
    private String userTime;
    private String userEmail;
    private String userPassword;
    private String otherUserEmail;
    private String otherUserPassword;


    public Message(){
        userName = "";
        userMessage = "";
        userTime = "";
        userEmail = "";
        userPassword = "";
        otherUserEmail = "";
        otherUserPassword = "";
    }


    // Setter
    public void setUserName(String nm)
    {
        userName = nm;
    }

    public void setUserMessage(String m)
    {
        userMessage = m;
    }

    public void setUserImage(Bitmap i) { userImage = i; }

    public void setUserTime(String t) { userTime = t; }

    public void setUserEmail(String e) { userEmail = e; }

    public void setUserPassword(String p){
        userPassword = p;
    }

    public void setOtherUserEmail(String e) { otherUserEmail = e; }

    public void setOtherUserPassword(String p) { otherUserPassword = p; }

    // Getter
    public String getUserName()
    {
        return userName;
    }

    public String getUserMessage()
    {
        return userMessage;
    }

    public Bitmap getUserImage() { return userImage; }

    public String getUserTime() { return userTime; }

    public String getUserEmail() { return userEmail; }

    public String getUserPassword(){
        return userPassword;
    }

    public String getOtherUserEmail() { return otherUserEmail; }

    public String getOtherUserPassword() { return otherUserPassword; }

    public void insertAMessageIntoDB(UserOpenHelper userOpenHelper) {
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        ContentValues newMessage = new ContentValues();

        newMessage.put(UserContract.Messages.COL_NAME, userName);
        newMessage.put(UserContract.Messages.COL_MESSAGE, userMessage);
//        newMessage.put(UserContract.Messages.COL_TIME, userTime);
        //newMessage.put(UserContract.Messages.COL_IMAGE, userImage);
        newMessage.put(UserContract.Messages.COL_EMAIL, userEmail);
//        newMessage.put(UserContract.Messages.COL_PASSWORD, userPassword);
        newMessage.put(UserContract.Messages.COL_OTHER_EMAIL, otherUserEmail);
//        newMessage.put(UserContract.Messages.COL_OTHER_PASSWORD, otherUserPassword);

        long newId = db.insert(
                UserContract.Messages.TABLE_NAME,
                null,
                newMessage
        );
        db.close();
    }
}
