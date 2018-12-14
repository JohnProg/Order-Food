package com.tectime.johnpaul.orderfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tectime.johnpaul.orderfood.base.BaseActivity;
import com.tectime.johnpaul.orderfood.common.Common;
import com.tectime.johnpaul.orderfood.model.User;

public class SignInActivity extends BaseActivity {

    EditText edtPhone, edtPassword;
    Button btnSignIn;
    FirebaseDatabase database;
    DatabaseReference table_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initFirebase();
        initViews();
        initActions();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        table_users = database.getReference("User");
    }

    private void initViews() {
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
    }

    private void initActions() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Loading...");
                mDialog.show();
                final String phone = edtPhone.getText().toString();
                final String password = edtPassword.getText().toString();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if user was registered
                        if (dataSnapshot.child(phone).exists()) {
                            // Get User information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(phone).getValue(User.class);
                            user.setPhone(phone);
                            if (user.getPassword().equals(password)) {
                                Toast.makeText(SignInActivity.this, "Sign In successfully.", Toast.LENGTH_SHORT).show();
                                Common.currentUser = user;
                                Intent homeIntent = new Intent(SignInActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "User was not registered.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
