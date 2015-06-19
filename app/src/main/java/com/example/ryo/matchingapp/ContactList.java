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

import java.util.ArrayList;


public class ContactList extends AppCompatActivity {

    private User myUser;
    private User anUser;
    private ArrayList<User> users;
    private String myUserEmail;
    private String myUserPassword;
//    public final static String OTHER_PASSWORD_EXTRA = "com.example.ryo.matchingapp.OTHER_PASSWORD";

    public ArrayList<User> loadAllContactedUsers(UserOpenHelper userOpenHelper, String myEmail, String myPassword){
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        Cursor c = null;

        User user = new User();

        c = db.query(
                UserContract.Contacts.TABLE_NAME,
                null, //fields
                UserContract.Contacts.COL_EMAIL + " = ?", //where
                new String[] { myEmail }, //where args
                null, // groupBy
                null, // having
                null //order by
        );

        if(c == null) {
            db.close();
            return null;
        }


        ArrayList<ContactedUser> contactedUserList = new ArrayList<ContactedUser>();

        while(c.moveToNext()){
            ContactedUser cu = new ContactedUser();
            cu.setEmail(c.getString(c.getColumnIndex(UserContract.Contacts.COL_EMAIL)));
            cu.setPassword(c.getString(c.getColumnIndex(UserContract.Contacts.COL_PASSWORD)));
            cu.setOther_email(c.getString(c.getColumnIndex(UserContract.Contacts.COL_OTHER_EMAIL)));
            cu.setOther_password(c.getString(c.getColumnIndex(UserContract.Contacts.COL_OTHER_PASSWORD)));
            contactedUserList.add(cu);
        }
        c.close();


        ArrayList<ContactedUser> cuList = new ArrayList<ContactedUser>();


        for(int i = 0; i < contactedUserList.size(); i++){
            if(myPassword.equals(contactedUserList.get(i).getPassword())){
                ContactedUser cu = new ContactedUser();
                cu.setEmail(contactedUserList.get(i).getEmail());
                cu.setPassword(contactedUserList.get(i).getPassword());
                cu.setOther_email(contactedUserList.get(i).getOther_email());
                cu.setOther_password(contactedUserList.get(i).getOther_password());
                cuList.add(cu);
            }
        }


        if(cuList.size() == 0){
            db.close();
            return null;
        }

        Cursor c2 = null;

        c2 = db.query(
                UserContract.Users.TABLE_NAME,
                null, //fields
                null, //where
                null, //where args
                null, // groupBy
                null, // having
                null //order by
        );

        if(c2 == null) {
            db.close();
            return null;
        }

        ArrayList<User> uList = new ArrayList<User>();

        while(c2.moveToNext()){
            User u = new User();
            u.setUser_name(c2.getString(c2.getColumnIndex(UserContract.Users.COL_NAME)));
            u.setUser_summary(c2.getString(c2.getColumnIndex(UserContract.Users.COL_SUMMARY)));
            u.setUser_longitude(c2.getDouble(c2.getColumnIndex(UserContract.Users.COL_LONGITUDE)));
            u.setUser_latitude(c2.getDouble(c2.getColumnIndex(UserContract.Users.COL_LATITUDE)));
//            u.setImage(c2.getInt(c2.getColumnIndex(UserContract.Users.COL_IMAGE))); //?????????????????????????what should I do?
            u.setEmail(c2.getString(c2.getColumnIndex(UserContract.Users.COL_EMAIL)));
            u.setPassword(c2.getString(c2.getColumnIndex(UserContract.Users.COL_PASSWORD)));
            uList.add(u);
        }
        db.close();
        c2.close();

        ArrayList<User> userList = new ArrayList<User>();

        for(int i = 0; i < cuList.size(); i++){
            for(int j = 0; j < uList.size(); j++){
                if(cuList.get(i).getOther_email().equals(uList.get(j).getEmail()) && cuList.get(i).getOther_password().equals(uList.get(j).getPassword())){
                    User u = new User();
                    u.setEmail(uList.get(j).getEmail());
                    u.setPassword(uList.get(j).getPassword());
                    u.setUser_name(uList.get(j).getUser_name());
                    u.setUser_summary(uList.get(j).getUser_summary());
//                    u.setImage(uList.get(j).getImage());
                    u.setUser_longitude(uList.get(j).getUser_longitude());
                    u.setUser_latitude(uList.get(j).getUser_latitude());
                    userList.add(u);
                }
            }
        }
        return userList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        myUser = new User();
        anUser = new User();
        myUserEmail = MainActivity.EMAIL;
        myUserPassword = MainActivity.PASSWORD;
        myUser.loadAUserInfoFromUserDB(new UserOpenHelper(this), myUserEmail);
        users = new ArrayList<User>();
        users = loadAllContactedUsers(new UserOpenHelper(this), myUserEmail, myUserPassword);
        final ListView myContactList = (ListView) findViewById(R.id.contactList_list);
        if(users != null) {
            UserAdapter userAdapter = new UserAdapter(this, 0, users);
            myContactList.setAdapter(userAdapter);

            myContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //???intent
                    Intent intent = new Intent(ContactList.this, MessageWindow.class);
                    MainActivity.OTHER_EMAIL = users.get(i).getEmail();
                    MainActivity.OTHER_PASSWORD = users.get(i).getPassword();
                    startActivity(intent);
                }
            });
        }

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
                convertView = layoutInflater.inflate(R.layout.list_row, null);
            }

            anUser = getItem(position);
//            ImageView userImage = (ImageView) convertView.findViewById(R.id.list_row_profileImage);
//            userImage.setImageBitmap(anUser.getImage());
            TextView nameTextView = (TextView) convertView.findViewById(R.id.list_row_profileName);
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

            TextView distance = (TextView) convertView.findViewById(R.id.list_row_distance);
            distance.setText(resultDist_int.toString() + " km");
            TextView description = (TextView) convertView.findViewById(R.id.list_row_description);
            description.setText(anUser.getUser_summary());

            return convertView;
        }
    }

    public void onClickBackButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
