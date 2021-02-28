package com.mfachriadrianta.seruputcoffee;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyCoffeeDetailAct extends AppCompatActivity {

    DatabaseReference reference;
    TextView xnama_coffee, xlokasi, xdate_coffee, xtime_coffee,  xinformasi;
    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coffee_detail);

        xnama_coffee = findViewById(R.id.xnama_coffee);
        xlokasi = findViewById(R.id.xlokasi);
        xdate_coffee = findViewById(R.id.xdate_coffee);
        xtime_coffee = findViewById(R.id.xtime_coffee);
        xinformasi = findViewById(R.id.xketentuan);

        btn_back = findViewById(R.id.btn_back);

        // Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_coffee_baru = bundle.getString("nama_coffee");

        // Mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Coffee").child(nama_coffee_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_coffee.setText(dataSnapshot.child("nama_coffee").getValue().toString());
                xlokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                xdate_coffee.setText(dataSnapshot.child("date_coffee").getValue().toString());
                xtime_coffee.setText(dataSnapshot.child("time_coffee").getValue().toString());
                xinformasi.setText(dataSnapshot.child("informasi").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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