package com.mfachriadrianta.seruputcoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account = findViewById(R.id.btn_new_account);

        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  gotoregisterone = new Intent(SigninAct.this, RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("LOADING ...");


                if (username.isEmpty()){

                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }
                else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    }
                    else {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    // Ambil data dari firebase
                                    String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                    // Validasi password dengan password firebase
                                    if (password.equals(passwordFromFirebase)) {

                                        // Simpan username kepada local (Handphone)
                                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(username_key, xusername.getText().toString());
                                        editor.apply();


                                        // Berpindah activity
                                        Intent gotohome = new Intent(SigninAct.this, HomeAct.class);
                                        startActivity(gotohome);
                                        finish();


                                        if (username.equals(passwordFromFirebase)){
                                            btn_sign_in.setEnabled(true);
                                            btn_sign_in.setText("SIGN IN");
                                        }

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Password Wrong", Toast.LENGTH_SHORT).show();
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Username Nothing", Toast.LENGTH_SHORT).show();
                                    btn_sign_in.setEnabled(true);
                                    btn_sign_in.setText("SIGN IN");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
                btn_sign_in.setEnabled(true);
                btn_sign_in.setText("SIGN IN");
            }
        });

    }
}
