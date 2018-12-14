package com.tectime.johnpaul.orderfood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tectime.johnpaul.orderfood.base.BaseActivity;
import com.tectime.johnpaul.orderfood.model.User;

public class SignUpActivity extends BaseActivity {

    MaterialEditText edtPhone, edtName, edtPassword;
    Button btnSignUp;
    FirebaseDatabase database;
    DatabaseReference table_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initFirebase();
        initViews();
        initActions();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        table_users = database.getReference("User");
    }

    private void initViews() {
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    private void initActions() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Loading...");
                mDialog.show();
                final String phone = edtPhone.getText().toString();
                final String name = edtName.getText().toString();
                final String password = edtPassword.getText().toString();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Check if user was registered
                        if (dataSnapshot.child(phone).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Phone Number already register.", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            User user = new User(name, password);
                            table_users.child(phone).setValue(user);
                            Toast.makeText(SignUpActivity.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
                            finish();
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
