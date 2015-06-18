package com.example.ryo.matchingapp;

import android.provider.BaseColumns;

/**
 * Created by Ryo on 6/11/2015.
 */
public final class UserContract {

    public UserContract() {}

    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD = "password";
        public static final String COL_NAME = "name";
        public static final String COL_SUMMARY = "summary";
        //        public static final String COL_IMAGE = "image";
        public static final String COL_LONGITUDE = "longitude";
        public static final String COL_LATITUDE = "latitude";
    }

    public static abstract class Contacts implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD = "password";
        public static final String COL_OTHER_EMAIL = "other_email";
        public static final String COL_OTHER_PASSWORD = "other_password";
    }

    public static abstract class Messages implements BaseColumns {
        public static final String TABLE_NAME = "messages";
        public static final String COL_NAME = "name";
        public static final String COL_MESSAGE = "message";
        public static final String COL_TIME = "time";
        //        public static final String COL_IMAGE = "image";
        public static final String COL_EMAIL = "email";
        //        public static final String COL_PASSWORD = "password";
        public static final String COL_OTHER_EMAIL = "other_email";
//        public static final String COL_OTHER_PASSWORD = "other_password";
    }

}
