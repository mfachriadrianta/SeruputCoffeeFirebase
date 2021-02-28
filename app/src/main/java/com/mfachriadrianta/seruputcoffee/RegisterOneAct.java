package com.mfachriadrianta.seruputcoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue1;
    EditText username, password, email_address;
    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        btn_back = findViewById(R.id.btn_back);
        btn_continue1 = findViewById(R.id.btn_continue1);

        btn_continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_continue1.setEnabled(false);
                btn_continue1.setText("Loading ...");

                // Mengambil username pada Firebase Database
                reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Jika username tersedia
                        if (dataSnapshot.exists()){
                            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();

                            //Button menjadi aktif
                            btn_continue1.setEnabled(true);
                            btn_continue1.setText("Continue");
                        }
                        else {
                            // Menyimpan data kepada local storage (handphone)
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key, username.getText().toString());
                            editor.apply();

                            // Simpan kepada database
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                    dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                    dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                    dataSnapshot.getRef().child("user_balance").setValue(25);

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            // Berpindah Activity
                            Intent gotonextregister =new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                            startActivity(gotonextregister);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                final String xusername  = username.getText().toString();
                final String xpassword = password.getText().toString();
                final String xemail_address = email_address.getText().toString();


                if (xusername.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                    btn_continue1.setEnabled(true);
                    btn_continue1.setText("CONTINUE");
                }
                else {
                    if (xpassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                        btn_continue1.setEnabled(true);
                        btn_continue1.setText("CONTINUE");
                    }
                    else {
                        if (xemail_address.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Email Empty", Toast.LENGTH_SHORT).show();
                            btn_continue1.setEnabled(true);
                            btn_continue1.setText("CONTINUE");
                        }

                    }
                }

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
