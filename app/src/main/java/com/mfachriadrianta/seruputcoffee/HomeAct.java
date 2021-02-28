package com.mfachriadrianta.seruputcoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    BillingProcessor bp;

    LinearLayout btn_coffe_gayo, btn_coffee_kintami, btn_coffee_toraja, btn_coffee_java, btn_coffee_flores, btn_coffee_liberika;
    CircleView btn_to_profile;
    TextView nama_lengkap, bio, user_balance;
    ImageView photo_home_user;
    Button btn_top_up;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bp = new BillingProcessor(this, "", this);
        bp.initialize();

        getUsernameLocal();

        btn_top_up = findViewById(R.id.btn_top_up);
        btn_coffee_java = findViewById(R.id.btn_coffee_java);
        btn_coffe_gayo = findViewById(R.id.btn_coffee_gayo);
        btn_coffee_kintami = findViewById(R.id.btn_coffee_kintami);
        btn_coffee_toraja = findViewById(R.id.btn_coffee_toraja);
        btn_coffee_flores = findViewById(R.id.btn_coffee_flores);
        btn_coffee_liberika = findViewById(R.id.btn_coffee_liberika);

        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        user_balance = findViewById(R.id.user_balance);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        btn_top_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(HomeAct.this, "");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_coffee_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocoffee = new Intent(HomeAct.this, CoffeeDetailAct.class);
                // Meletakkan data pada intent
                gotocoffee.putExtra("jenis_coffee", "Java");
                startActivity(gotocoffee);
            }
        });

        btn_coffe_gayo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocoffee = new Intent(HomeAct.this, CoffeeDetailAct.class);
                gotocoffee.putExtra("jenis_coffee", "Gayo");
                startActivity(gotocoffee);
            }
        });

        btn_coffee_kintami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocoffee = new Intent(HomeAct.this, CoffeeDetailAct.class);
                gotocoffee.putExtra("jenis_coffee", "Kintami");
                startActivity(gotocoffee);
            }
        });

        btn_coffee_toraja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocoffee = new Intent(HomeAct.this, CoffeeDetailAct.class);
                gotocoffee.putExtra("jenis_coffee", "Toraja");
                startActivity(gotocoffee);
            }
        });

        btn_coffee_flores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocoffee = new Intent(HomeAct.this, CoffeeDetailAct.class);
                gotocoffee.putExtra("jenis_coffee", "Flores");
                startActivity(gotocoffee);
            }
        });

        btn_coffee_liberika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocoffee = new Intent(HomeAct.this, CoffeeDetailAct.class);
                gotocoffee.putExtra("jenis_coffee", "Liberika");
                startActivity(gotocoffee);
            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(HomeAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(getApplicationContext(),"Successful Purchase", Toast.LENGTH_SHORT).show();

        // Update ke Firebase

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(getApplicationContext(),"Failed to Buy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }
}
