package com.mfachriadrianta.seruputcoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CoffeeDetailAct extends AppCompatActivity {

    Button btn_buy_coffee;
    LinearLayout btn_back;
    TextView title_coffee, location_coffee, photo_spot_coffee, warning_coffee,original_coffee,short_desc_coffee ;
    ImageView header_coffee_detail;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);

        btn_buy_coffee = findViewById(R.id.btn_buy_coffee);
        header_coffee_detail = findViewById(R.id.header_coffee_detail);
        btn_back = findViewById(R.id.btn_back);

        title_coffee = findViewById(R.id.title_coffee);
        location_coffee = findViewById(R.id.location_coffee);
        photo_spot_coffee = findViewById(R.id.photo_spot_coffee);
        warning_coffee = findViewById(R.id.warning_coffee);
        original_coffee = findViewById(R.id.original_coffee);
        short_desc_coffee = findViewById(R.id.short_desc_coffee);

        //Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_coffee_baru = bundle.getString("jenis_coffee");

        // Mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Coffee").child(jenis_coffee_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Menimpa data yang ada dengan yang baru
                title_coffee.setText(dataSnapshot.child("nama_coffee").getValue().toString());
                location_coffee.setText(dataSnapshot.child("lokasi").getValue().toString());
                photo_spot_coffee.setText(dataSnapshot.child("is_character_coffee").getValue().toString());
                warning_coffee.setText(dataSnapshot.child("is_warning").getValue().toString());
                original_coffee.setText(dataSnapshot.child("is_original").getValue().toString());
                short_desc_coffee.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.with(CoffeeDetailAct.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header_coffee_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_buy_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocheckout = new Intent(CoffeeDetailAct.this, CoffeeCheckOutAct.class);
                // Meletakkan data pada intent
                gotocheckout.putExtra("jenis_coffee", jenis_coffee_baru);
                startActivity(gotocheckout);
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
