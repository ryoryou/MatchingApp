package com.example.ryo.matchingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {

    private User myUser;
    private User anUser;
    private String myUserEmail;
    ArrayList<User> users;

    public final static String EXTRA_OTHER_EMAIL = "com.example.ryo.matchingapp.OTHER_EMAIL";

    private ArrayList<User> keepAllUsersWithin(ArrayList<User> users, int km) {

        ArrayList<User> userList = new ArrayList<User>();
        GPS gps = new GPS();

        //???????????
        double myLongitude = myUser.getUser_longitude();
        double myLatitude = myUser.getUser_latitude();

        double otherLongitude;
        double otherLatitude;

        if(users.size() == 0){
            return null;
        }

        for(int i = 0; i < users.size(); i++){

            //?????????????
            otherLongitude = users.get(i).getUser_longitude();
            otherLatitude = users.get(i).getUser_latitude();

            //???????????
            double resultDist = gps.calculateTwoGPS(myLongitude, myLatitude, otherLongitude, otherLatitude);
            int resultDist_int = gps.conversionFromMiToKm(resultDist);

            if(resultDist_int <= km)
                userList.add(users.get(i));
        }
        return userList;
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

    public void onClickBackButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        myUser = new User();
        anUser = new User();

        // load user information from database
        myUserEmail = MainActivity.EMAIL;
        myUser.loadAUserInfoFromUserDB(new UserOpenHelper(this), myUserEmail);
        users = loadAllUsers();
        users = keepAllUsersWithin(users, 5);
        UserAdapter userAdapter = new UserAdapter(this, 0, users);
        final ListView myContactList = (ListView) findViewById(R.id.myContactList);
        myContactList.setAdapter(userAdapter);

        myContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Contact.this, UserProfile.class);
                MainActivity.OTHER_EMAIL = users.get(i).getEmail();
                MainActivity.OTHER_PASSWORD = users.get(i).getPassword();
                Toast.makeText(Contact.this, MainActivity.OTHER_EMAIL + " " + MainActivity.OTHER_PASSWORD, Toast.LENGTH_LONG).show();//////////////////
                startActivity(intent);
            }
        });
    }



    public class UserAdapter extends ArrayAdapter<User> {
        private LayoutInflater layoutInflater;

        public UserAdapter(Context context, int viewResourceId, ArrayList<User> users) {
            super(context, viewResourceId, users);
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.row, null);
            }

            anUser = getItem(position);
//            ImageView userImage = (ImageView) convertView.findViewById(R.id.row_profileImage);
//            userImage.setImageBitmap(anUser.getImage());
            TextView nameTextView = (TextView) convertView.findViewById(R.id.row_profileName);
            nameTextView.setText(anUser.getUser_name());


            //******* ????????????? ******* //
            GPS gps = new GPS();

            //???????????
            double myLongitude = myUser.getUser_longitude();
            double myLatitude = myUser.getUser_latitude();

            //?????????????
            double otherLongitude = anUser.getUser_longitude();
            double otherLatitude = anUser.getUser_latitude();



            //???????????
            double resultDist = gps.calculateTwoGPS(myLatitude, myLongitude, otherLatitude, otherLongitude);
            Integer resultDist_int = gps.conversionFromMiToKm(resultDist);

            if(resultDist_int == 0){
                resultDist_int = 1;
            }

            TextView distance = (TextView) convertView.findViewById(R.id.row_distance);
            distance.setText(resultDist_int.toString() + " km");
            TextView description = (TextView) convertView.findViewById(R.id.row_description);
            description.setText(anUser.getUser_summary());

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
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
