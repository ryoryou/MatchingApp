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


public class RegisterAccountActivity extends AppCompatActivity {

    private EditText editUserName;
    private EditText editEmail;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        editUserName = (EditText) findViewById(R.id.registeringUserName);
        editEmail = (EditText) findViewById(R.id.registeringEmail);
        editPassword = (EditText) findViewById(R.id.registeringPassword);
    }

    public void onClickCreateAccountButton(View view){
        User user = new User();

        //store user name into user_name of user object
        user.setUser_name(editUserName.getText().toString());

        //store user email into email of user object
        user.setEmail(editEmail.getText().toString());

        //store user password into password of user object
        user.setPassword(editPassword.getText().toString());

        if(user.getEmail().equals("") || user.getPassword().equals("")){
            Toast.makeText(this, "Please Enter Both", Toast.LENGTH_SHORT).show();
            return;
        }
/*
        if(!existsInDB(user.getEmail(), user.getPassword())) {
            Toast.makeText(this, "This Email or Password exists", Toast.LENGTH_SHORT).show();
            editEmail.setText("");
            editPassword.setText("");
            return;
        }
*/

        MainActivity.EMAIL = user.getEmail();
        MainActivity.PASSWORD = user.getPassword();
        MainActivity.LOG_IN = true;

        //Save user information into Database
        user.insertAnUserInfoIntoDB(new UserOpenHelper(this));
        Toast.makeText(this, "Account has been created ", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
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

    public void onClickBackButton(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_account, menu);
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
