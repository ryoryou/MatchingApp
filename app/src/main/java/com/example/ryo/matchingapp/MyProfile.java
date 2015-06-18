package com.example.ryo.matchingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MyProfile extends AppCompatActivity {

    public final static String EXTRA_MY_PROFILE = "com.example.ryo.matchingapp.MY_PROFILE";
    private User user;
    private String myUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();
        myUserEmail = MainActivity.EMAIL;
        setContentView(R.layout.activity_my_profile);
        setDefaultProfile();
    }

    public void setDefaultProfile(){
        user.loadAUserInfoFromUserDB(new UserOpenHelper(this), myUserEmail);
        ImageView imageView = (ImageView)findViewById(R.id.profile_myImage);
        imageView.setImageBitmap(user.getImage());
        EditText editText = (EditText)findViewById(R.id.profile_myName);
        editText.setText(user.getUser_name());
        editText = (EditText) findViewById(R.id.profile_description);
        editText.setText(user.getUser_summary());
    }

    public void clickedBackButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void clickedSaveButton(View view){

        //store user image into user_image of userInfo object
//        ImageView userImage = (ImageView) findViewById(R.id.profile_myImage);
//        userInfo.setImage(userImage.getImage()); //?????????????????????????? how to get Image?
        //and how to deal with storing image? int? bitmap?

        //store user name into user_name of userInfo object
        EditText myEditText = (EditText) findViewById(R.id.profile_myName);
        user.setUser_name (myEditText.getText().toString());

        //store profile description into user_summary of userInfo object
        EditText profileDesc = (EditText) findViewById(R.id.profile_description);
        user.setUser_summary(profileDesc.getText().toString());


        //Save user information into Database
        user.updateAUserInfoInUserDB(new UserOpenHelper(this), myUserEmail);
        Toast.makeText(this, "Data has been saved ", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_profile, menu);
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
