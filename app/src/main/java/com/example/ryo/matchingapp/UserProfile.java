package com.example.ryo.matchingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class UserProfile extends AppCompatActivity {

    private User myUser;

    private User anUser;

    private String myUserEmail;

    private String myUserPassword;

    private String anUserEmail;

    private String anUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Intent intent = getIntent();
//        myUserEmail = intent.getStringExtra(MainActivity.EXTRA_EMAIL);
        anUserEmail = intent.getStringExtra(Contact.EXTRA_OTHER_EMAIL);
        myUser = new User();
        anUser = new User();
        myUserEmail = MainActivity.EMAIL;
        myUserPassword = MainActivity.PASSWORD;
        anUserEmail = MainActivity.OTHER_EMAIL;
        anUserPassword = MainActivity.OTHER_PASSWORD;

        anUser.loadAUserInfoFromUserDB(new UserOpenHelper(this), anUserEmail);
        myUser.loadAUserInfoFromUserDB(new UserOpenHelper(this), myUserEmail);
        //Setting Display
        //ImageView iv = (ImageView) findViewById(R.id.userImage);
        //iv.setImageBitmap(user.getImage());
        TextView userNameTextView = (TextView) findViewById(R.id.profile_userName);
        userNameTextView.setText(anUser.getUser_name());

        GPS gps = new GPS();

        //???????????
        double myLongitude = myUser.getUser_longitude();
        double myLatitude = myUser.getUser_latitude();

        //?????????????
        double otherLongitude = anUser.getUser_longitude();
        double otherLatitude = anUser.getUser_latitude();

        //???????????
        double resultDist = gps.calculateTwoGPS(myLongitude, myLatitude, otherLongitude, otherLatitude);
        Integer resultDist_int = gps.conversionFromMiToKm(resultDist);

        if (resultDist_int == 0) {
            resultDist_int = 1;
        }

        TextView userDistTextView = (TextView) findViewById(R.id.profile_userDistance);
        userDistTextView.setText(resultDist_int.toString() + " km");

        TextView userDescTextView = (TextView) findViewById(R.id.profile_userDescription);
        userDescTextView.setText(anUser.getUser_summary());
        userDescTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void clickedBackButton(View view) {
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }

    public void clickedMeetButton(View view) {
        ContactedUser cu = new ContactedUser();
        cu.setEmail(myUserEmail);
        cu.setPassword(myUserPassword);
        cu.setOther_email(anUserEmail);
        cu.setOther_password(anUserPassword);
        //check if this other_email and other_password exists. if so, do not insert///////////////////////////////////////////////////////////////
        cu.insertAContactedUserIntoDB(new UserOpenHelper(this));

        Intent intent = new Intent(this, MessageWindow.class);
        intent.putExtra(Contact.EXTRA_OTHER_EMAIL, anUserEmail);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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

