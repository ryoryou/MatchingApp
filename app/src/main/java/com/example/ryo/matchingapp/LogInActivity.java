package com.example.ryo.matchingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class LogInActivity extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailET = (EditText) findViewById(R.id.logIn_email);
        passwordET = (EditText) findViewById(R.id.logIn_password);

        /*
        User user = new User();
        user.setUser_name("Atsuta Ryo");
        user.setUser_summary("I want to become rich!!!");
        user.setEmail("aaa@aaa.com");
        user.setPassword("aaaa");
        user.insertAnUserInfoIntoDB(new UserOpenHelper(this));

        user.setUser_name("Matsumoto Yuki");
        user.setUser_summary("I want to become rich!!!");
        user.setEmail("bbb@bbb.com");
        user.setPassword("bbbb");
        user.insertAnUserInfoIntoDB(new UserOpenHelper(this));

        user.setUser_name("Aomori Ken");
        user.setUser_summary("I am Prefecture!!!");
        user.setEmail("ccc@ccc.com");
        user.setPassword("cccc");
        user.insertAnUserInfoIntoDB(new UserOpenHelper(this));

        user.setUser_name("Kagawa Ken");
        user.setUser_summary("I am Prefecture?");
        user.setEmail("ddd@ddd.com");
        user.setPassword("dddd");
        user.insertAnUserInfoIntoDB(new UserOpenHelper(this));





        Message aMessage = new Message();

        aMessage.setUserEmail("aaa@aaa.com");
        aMessage.setOtherUserEmail("bbb@bbb.com");
        aMessage.setUserName("Ryo Atsuta");
        aMessage.setUserMessage("This is Test");
//        aMessage.setUserTime();
//        aMessage.setUserImage();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));
        aMessage.setUserEmail("bbb@bbb.com");
        aMessage.setOtherUserEmail("aaa@aaa.com");
//        Toast.makeText(this, anUserEmail + " " + myUserEmail, Toast.LENGTH_LONG).show();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));


        aMessage.setUserEmail("aaa@aaa.com");
        aMessage.setOtherUserEmail("ccc@ccc.com");
        aMessage.setUserName("Ryo Atsuta");
        aMessage.setUserMessage("This is Test");
//        aMessage.setUserTime();
//        aMessage.setUserImage();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));
        aMessage.setUserEmail("ccc@ccc.com");
        aMessage.setOtherUserEmail("aaa@aaa.com");
//        Toast.makeText(this, anUserEmail + " " + myUserEmail, Toast.LENGTH_LONG).show();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));

        aMessage.setUserEmail("aaa@aaa.com");
        aMessage.setOtherUserEmail("ddd@ddd.com");
        aMessage.setUserName("Ryo Atsuta");
        aMessage.setUserMessage("This is Test");
//        aMessage.setUserTime();
//        aMessage.setUserImage();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));
        aMessage.setUserEmail("ddd@ddd.com");
        aMessage.setOtherUserEmail("aaa@aaa.com");
//        Toast.makeText(this, anUserEmail + " " + myUserEmail, Toast.LENGTH_LONG).show();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));
*/
    }

    public void onClickLogInButton(View view){
        String myEmail = emailET.getText().toString();
        String myPassword = passwordET.getText().toString();
        if(!existsInDB(myEmail, myPassword)) {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
            emailET.setText("");
            passwordET.setText("");
            return;
        }

        MainActivity.EMAIL = emailET.getText().toString();
        MainActivity.PASSWORD = passwordET.getText().toString();
        MainActivity.LOG_IN = true;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickRegisterAccountButton(View view){
        Intent intent = new Intent(this, RegisterAccountActivity.class);
        startActivity(intent);
    }

    private ArrayList<User> loadAllUsers(){
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        Cursor c = null;

        c = db.query(
                UserContract.Users.TABLE_NAME,
                null, //fields
                null, //where
                null, //where arg
                null, // groupBy
                null, // having
                null //order by
        );

        if(c == null) {
            db.close();
            c.close();
            return null;
        }

        ArrayList<User> userList = new ArrayList<User>();

        while(c.moveToNext()){
            User u = new User();
            u.setUser_name(c.getString(c.getColumnIndex(UserContract.Users.COL_NAME)));
            u.setUser_summary(c.getString(c.getColumnIndex(UserContract.Users.COL_SUMMARY)));
            u.setUser_longitude(c.getDouble(c.getColumnIndex(UserContract.Users.COL_LONGITUDE)));
            u.setUser_latitude(c.getDouble(c.getColumnIndex(UserContract.Users.COL_LATITUDE)));
//            u.setImage(c.getInt(c.getColumnIndex(UserContract.Users.COL_IMAGE))); //?????????????????????????what should I do?
            u.setEmail(c.getString(c.getColumnIndex(UserContract.Users.COL_EMAIL)));
            u.setPassword(c.getString(c.getColumnIndex(UserContract.Users.COL_PASSWORD)));
            userList.add(u);
        }
        db.close();
        c.close();
        return userList;
    }

    private boolean existsInDB(String email, String password) {
        ArrayList<User> users;
        users = loadAllUsers();
        if(users == null){
            return false;
        }
        for(int i = 0; i < users.size(); i++){
            if(email.equals(users.get(i).getEmail()) && password.equals(users.get(i).getPassword())){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
