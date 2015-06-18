package com.example.ryo.matchingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ryo on 6/11/2015.
 */
public class User implements Parcelable {
    private String user_name;
    private String user_summary;
    private double user_longitude;
    private double user_latitude;
    private Bitmap image;
    private String email;
    private String password;

    public User(){
        user_name = "";
        user_summary = "";
        user_longitude = 0.0;
        user_latitude = 0.0;
        email = "";
        password = "";

    }


    // Setter
    public void setUser_name(String nm)
    {
        user_name = nm;
    }

    public void setUser_summary(String sm)
    {
        user_summary = sm;
    }

    public void setUser_latitude(double ula){ user_latitude = ula; }

    public void setUser_longitude(double ulo){ user_longitude = ulo; }

    public void setImage(Bitmap i) { image = i; }

    public void setEmail(String e) { email = e; }

    public void setPassword(String p){
        password = p;
    }

    // Getter
    public String getUser_name(){
        return user_name;
    }

    public String getUser_summary(){
        return user_summary;
    }

    public double getUser_latitude() { return user_latitude; }

    public double getUser_longitude() { return user_longitude; }

    public Bitmap getImage() { return image; }

    public String getEmail() { return email; }

    public String getPassword () {
        return password;
    }

    //Finished
    public void insertAnUserInfoIntoDB(UserOpenHelper userOpenHelper) {
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        ContentValues newUser = new ContentValues();

        newUser.put(UserContract.Users.COL_NAME, user_name);
        newUser.put(UserContract.Users.COL_SUMMARY, user_summary);
        newUser.put(UserContract.Users.COL_LONGITUDE, user_longitude);
        newUser.put(UserContract.Users.COL_LATITUDE, user_latitude);
        //newUser.put(UserContract.Users.COL_IMAGE, image);
        newUser.put(UserContract.Users.COL_EMAIL, email);
        newUser.put(UserContract.Users.COL_PASSWORD, password);


        long newId = db.insert(
                UserContract.Users.TABLE_NAME,
                null,
                newUser
        );
        db.close();
    }

    public void updateAUserInfoInUserDB(UserOpenHelper userOpenHelper, String email){
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        ContentValues userUpdate = new ContentValues();
        userUpdate.put(UserContract.Users.COL_NAME, user_name);
        userUpdate.put(UserContract.Users.COL_SUMMARY, user_summary);
        userUpdate.put(UserContract.Users.COL_LONGITUDE, user_longitude);
        userUpdate.put(UserContract.Users.COL_LATITUDE, user_latitude);
        //userUpdate.put(UserContract.Users.COL_IMAGE, image);
        userUpdate.put(UserContract.Users.COL_EMAIL, email);
        userUpdate.put(UserContract.Users.COL_PASSWORD, password);


        long updatedCount = db.update(
                UserContract.Users.TABLE_NAME,
                userUpdate,
                UserContract.Users.COL_EMAIL + " = ?",
                new String[] { email }
        );
        db.close();
    }

    //get user information based on Email, FB, or LinkedIn
    //Finished
    public void loadAUserInfoFromUserDB(UserOpenHelper userOpenHelper, String email){
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        Cursor c = null;

        c = db.query(
                UserContract.Users.TABLE_NAME,
                null, //fields
                UserContract.Users.COL_EMAIL + " = ?", //where COL_EMAIL = email
                new String[] { email }, //where arg
                null, // groupBy
                null, // having
                null //order by
        );

        if(c == null) {
            db.close();
            c.close();
            return;
        }
        while(c.moveToNext()){
            setUser_name(c.getString(c.getColumnIndex(UserContract.Users.COL_NAME)));
            setUser_summary(c.getString(c.getColumnIndex(UserContract.Users.COL_SUMMARY)));
            setUser_longitude(c.getDouble(c.getColumnIndex(UserContract.Users.COL_LONGITUDE)));
            setUser_latitude(c.getDouble(c.getColumnIndex(UserContract.Users.COL_LATITUDE)));
//          setImage(c.getInt(c.getColumnIndex(UserContract.Users.COL_IMAGE))); //?????????????????????????what should I do?
            setEmail(c.getString(c.getColumnIndex(UserContract.Users.COL_EMAIL)));
            setPassword(c.getString(c.getColumnIndex(UserContract.Users.COL_PASSWORD)));
        }
        db.close();
        c.close();
    }

    public int describeContents(){
        return 0;
    }
    public void writeToParcel(Parcel out,int flags){
        out.writeString(user_name);
        out.writeString(user_summary);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){
        public User createFromParcel(Parcel in){
            return new User(in);
        }
        public User[] newArray(int size){
            return new User[size];
        }
    };
    private User(Parcel in){
        user_name = in.readString();
        user_summary = in.readString();
    }
}
