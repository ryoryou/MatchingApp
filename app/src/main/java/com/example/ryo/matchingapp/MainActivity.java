package com.example.ryo.matchingapp;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements LocationListener {

    // ????(??)
    private static final int LOCATION_UPDATE_MIN_TIME = 1;//1800000; // 30 mins
    // ????(??)
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1; //500 m

    private LocationManager mLocationManager;

    private Location loc;

    private User user;

    public static String EMAIL = "";

    public static String PASSWORD = "";

    public static String OTHER_EMAIL;

    public static String OTHER_PASSWORD;

    private static boolean isNew = false;

    public static boolean LOG_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!LOG_IN){
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }

        mLocationManager = (LocationManager) this.getSystemService(Service.LOCATION_SERVICE);
        user = new User();
        requestLocationUpdates();
 /*
        if (isNew) {
            user.setUser_name("Ryo Atsuta");
            user.setUser_summary("I want to become rich!!!");
            user.setEmail(EMAIL);
            user.setPassword(PASSWORD);
            user.insertAnUserInfoIntoDB(new UserOpenHelper(this));
            isNew = false;
*/
//        } else {
        user.loadAUserInfoFromUserDB(new UserOpenHelper(this), EMAIL);
//        }
        TextView nameTextView = (TextView) findViewById(R.id.userName);
        nameTextView.setText(user.getUser_name());

        /*
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

    public void clickedContactButton(View view){
        Intent intent = new Intent(this, Contact.class);
        startActivity(intent);
    }

    public void clickedProfile(View view){
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void clickedListButton(View view){
        Intent intent = new Intent(this, ContactList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onLocationChanged(Location location) {
        loc = location;
        user.setUser_latitude(loc.getLatitude());
        user.setUser_longitude(loc.getLongitude());
        user.updateAUserInfoInUserDB(new UserOpenHelper(this), EMAIL);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                // if the provider is out of service, and this is not expected to change in the near future.
                String outOfServiceMessage = provider + "?????????????????";
                showMessage(outOfServiceMessage);
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                // if the provider is temporarily unavailable but is expected to be available shortly.
                String temporarilyUnavailableMessage = "????" + provider + "?????????????????????????????????";
                showMessage(temporarilyUnavailableMessage);
                break;
            case LocationProvider.AVAILABLE:
                // if the provider is currently available.
                if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                    String availableMessage = provider + "????????????";
                    showMessage(availableMessage);
                    requestLocationUpdates();
                }
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        String message = provider + "??????????";
        showMessage(message);
        if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            requestLocationUpdates();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            String message = provider + "??????????????";
            showMessage(message);
        }
    }

    private void requestLocationUpdates() {
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_MIN_DISTANCE,
                    this);
            loc = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            user.setUser_longitude(loc.getLongitude());
            user.setUser_latitude(loc.getLatitude());
        } else {
            String message = "Network???????????";
            showMessage(message);
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}