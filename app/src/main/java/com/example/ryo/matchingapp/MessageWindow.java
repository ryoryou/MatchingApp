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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MessageWindow extends AppCompatActivity {

    private ArrayList<Message> messages = new ArrayList<Message>();
    private Message aMessage;
    private User myUser;
    private String myUserEmail;
    private String myUserPassword;
    private String anUserEmail;
    private String anUserPassword;
    private EditText sendingAMessage;

    private ArrayList<Message> loadMessages(String emailA, String emailB){
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        Cursor c = null;

        c = db.query(
                UserContract.Messages.TABLE_NAME,
                null, //fields
                UserContract.Messages.COL_EMAIL + " = ?", //where
                new String[] { emailA }, //where arg
                null, // groupBy
                null, // having
                UserContract.Messages.COL_TIME //order by
        );

        if(c == null) {
            db.close();
            return null;
        }

        ArrayList<Message> messageList = new ArrayList<Message>();

        while(c.moveToNext()){
            Message m = new Message();
            m.setUserName(c.getString(c.getColumnIndex(UserContract.Messages.COL_NAME)));
            m.setUserMessage(c.getString(c.getColumnIndex(UserContract.Messages.COL_MESSAGE)));
            m.setUserTime(c.getString(c.getColumnIndex(UserContract.Messages.COL_TIME)));
//            m.setUserImage(c.getInt(c.getColumnIndex(UserContract.Messages.COL_IMAGE))); //?????????????????????????what should I do?
            m.setUserEmail(c.getString(c.getColumnIndex(UserContract.Messages.COL_EMAIL)));
//            m.setUserPassword(c.getString(c.getColumnIndex(UserContract.Messages.COL_PASSWORD)));
            m.setOtherUserEmail(c.getString(c.getColumnIndex(UserContract.Messages.COL_OTHER_EMAIL)));
//            m.setOtherUserPassword(c.getString(c.getColumnIndex(UserContract.Messages.COL_OTHER_PASSWORD)));
            messageList.add(m);
        }
        db.close();
        c.close();

        ArrayList<Message> mList = new ArrayList<Message>();

        for(int i = 0; i < messageList.size(); i++){
            if(emailB.equals(messageList.get(i).getOtherUserEmail())){
                Message m = new Message();
                m.setUserName(messageList.get(i).getUserName());
                m.setUserMessage(messageList.get(i).getUserMessage());
                m.setUserTime(messageList.get(i).getUserTime());
//                m.setUserImage(messageList.get(i).getUserImage());
                m.setUserEmail(messageList.get(i).getUserEmail());
//                m.setUserPassword(messageList.get(i).getUserPassword());
                m.setOtherUserEmail(messageList.get(i).getOtherUserEmail());
//                m.setOtherUserPassword(messageList.get(i).getOtherUserPassword());
                mList.add(m);
            }
        }

        return mList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_window);

        myUser = new User();
        aMessage = new Message();
        sendingAMessage = (EditText) findViewById(R.id.message_window_texting);

        myUserEmail = MainActivity.EMAIL;
        anUserEmail = MainActivity.OTHER_EMAIL;
        myUser.loadAUserInfoFromUserDB(new UserOpenHelper(this), myUserEmail);

        messages = loadMessages(myUserEmail, anUserEmail);
        MessageAdapter messageAdapter = new MessageAdapter(this, 0, messages);
        final ListView myMessageList = (ListView) findViewById(R.id.message_window_messageList);
        myMessageList.setAdapter(messageAdapter);
    }



    public void onClickSendButton(View view){
        aMessage.setUserEmail(myUserEmail);
        aMessage.setOtherUserEmail(anUserEmail);
        aMessage.setUserName(myUser.getUser_name());
        aMessage.setUserMessage(sendingAMessage.getText().toString());
//        aMessage.setUserImage();
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));
        aMessage.setUserEmail(anUserEmail);
        aMessage.setOtherUserEmail(myUserEmail);
        Toast.makeText(this, "From " + myUserEmail + " to " + anUserEmail, Toast.LENGTH_LONG).show();///////////////////////////////////////
        aMessage.insertAMessageIntoDB(new UserOpenHelper(this));
        sendingAMessage.setText("");

        messages = loadMessages(myUserEmail, anUserEmail);
        MessageAdapter messageAdapter = new MessageAdapter(this, 0, messages);
        final ListView myMessageList = (ListView) findViewById(R.id.message_window_messageList);
        myMessageList.setAdapter(messageAdapter);
    }

    public void onClickListButton(View view){
        Intent intent = new Intent(this, ContactList.class);
        startActivity(intent);
    }

    public class MessageAdapter extends ArrayAdapter<Message> {
        private LayoutInflater layoutInflater;

        public MessageAdapter(Context context, int viewResourceId, ArrayList<Message> messages) {
            super(context, viewResourceId, messages);
            this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.message_row, null);
            }

            aMessage = getItem(position);
//            ImageView userImage = (ImageView) convertView.findViewById(R.id.message_row_userImage);
//            userImage.setImageBitmap(aMessage.getUserImage());
            TextView nameTextView = (TextView) convertView.findViewById(R.id.message_row_userName);
            nameTextView.setText(aMessage.getUserName());
            TextView userTimeTextView = (TextView) convertView.findViewById(R.id.message_row_userTime);
            userTimeTextView.setText(aMessage.getUserTime());
            TextView description = (TextView) convertView.findViewById(R.id.message_row_userMessage);
            description.setText(aMessage.getUserMessage());

            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_window, menu);
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
