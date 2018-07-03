package com.example.job.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditEvent extends AppCompatActivity {

    private EditText mFeelingsEditText;
    private EditText mTitle;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private String mUsername ;
    private static final String ANONYMOUS = "anonymous";
    private static final int RC_PHOTO_PICKER =  2;

    //firebase instance
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        //initialize firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        //initialize views
        mFeelingsEditText = (EditText) findViewById(R.id.eventEditText);
        mTitle = (EditText) findViewById(R.id.eventTitle);
        mFeelingsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if (key==null){
            setTitle(R.string.activity_newEvent);
        }else {
            setTitle(R.string.activity_edit_event);
        }
        mFeelingsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        if (mChildEventListener == null){
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Intent intent = getIntent();
                    String key = intent.getStringExtra("key");
                    if (key!=null){
                        Feelings feelings = dataSnapshot.getValue(Feelings.class);
                        String title = feelings.getmTitle();
                        String event = feelings.getmEvent();
                        mTitle.setText(title);
                        mFeelingsEditText.setText(event);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }


    private void saveEvent(){
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        if (key==null){
            Feelings friendlyMessage = new Feelings(mTitle.getText().toString(),mFeelingsEditText.getText().toString());
            mDatabaseReference.push().setValue(friendlyMessage);
        }else {
            mDatabaseReference.child("messages").child(key);
            Feelings friendlyMessage = new Feelings(mTitle.getText().toString(),mFeelingsEditText.getText().toString());
            mDatabaseReference.push().setValue(friendlyMessage);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                String title =  mTitle.getText().toString();
                String event = mFeelingsEditText.getText().toString();
                if (TextUtils.isEmpty(title) && TextUtils.isEmpty(event)){
                    Toast.makeText(EditEvent.this, "You can't save Empty fields", Toast.LENGTH_LONG).show();
                }else {saveEvent();
                    finish();

                }
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }
}
