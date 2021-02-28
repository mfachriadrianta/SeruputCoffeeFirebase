package com.mfachriadrianta.seruputcoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class CoffeeCheckOutAct extends AppCompatActivity {

    Button btn_pay_now, btnmines, btnplus;
    TextView textjumlahcoffee, texttotalharga, textmybalance, nama_coffee, lokasi, informasi;
    LinearLayout btn_back;

    Integer valuejumlahcoffee = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargaCoffee = 0;
    ImageView notice_uang;
    Integer sisa_balance = 0;

    DatabaseReference reference, reference2,  reference3, reference4, ref_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_coffee = "";
    String time_coffee = "";

    // Generate / menghasilkan nomor integer secara random / acak
    // Membuat transaksi seacara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_check_out);

        getUsernameLocal();

        //Mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_coffee_baru = bundle.getString("jenis_coffee");

        btnmines = findViewById(R.id.btnmines);
        btnplus = findViewById(R.id.btnplus);
        btn_pay_now = findViewById(R.id.btn_pay_now);
        notice_uang = findViewById(R.id.notice_uang);
        btn_back = findViewById(R.id.btn_back);

        textjumlahcoffee = findViewById(R.id.textjumlahcoffee);
        textmybalance = findViewById(R.id.textmybalance);
        texttotalharga = findViewById(R.id.texttotalharga);

        nama_coffee = findViewById(R.id.nama_coffee);
        lokasi = findViewById(R.id.lokasi);
        informasi = findViewById(R.id.informasi);



        // Secara Default kita hide button minus
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        // Mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                     textmybalance.setText("US$ " + mybalance +"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Coffee").child(jenis_coffee_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Menimpa data yang ada dengan yang baru
               nama_coffee.setText(dataSnapshot.child("nama_coffee").getValue().toString());
               lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
               informasi.setText(dataSnapshot.child("informasi").getValue().toString());

               date_coffee = dataSnapshot.child("date_coffee").getValue().toString();
               time_coffee = dataSnapshot.child("time_coffee").getValue().toString();

               valuehargaCoffee = Integer.valueOf(dataSnapshot.child("harga_coffee").getValue().toString());

               valuetotalharga = valuehargaCoffee * valuejumlahcoffee;
               texttotalharga.setText("US$ " + valuetotalharga +"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahcoffee+=1;
                textjumlahcoffee.setText(valuejumlahcoffee.toString());
                if (valuejumlahcoffee > 1){
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }
                valuetotalharga = valuehargaCoffee * valuejumlahcoffee;
                texttotalharga.setText("US$ " + valuetotalharga +"");

                if (valuetotalharga > mybalance){
                    btn_pay_now.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_pay_now.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#CA4748"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valuejumlahcoffee-=1;
                textjumlahcoffee.setText(valuejumlahcoffee.toString());
                if (valuejumlahcoffee < 2){
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }
                valuetotalharga = valuehargaCoffee * valuejumlahcoffee;
                texttotalharga.setText("US$ " + valuetotalharga +"");

                if (valuetotalharga < mybalance){
                    btn_pay_now.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_pay_now.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#FFCA6E"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Menyimpan data user kepada firebase dan membuat tabel baru "MyCoffee"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyCoffee").child(username_key_new).child("coffee").child(nama_coffee.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_coffee").setValue(nama_coffee.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_coffee").setValue(nama_coffee.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("informasi").setValue(informasi.getText().toString());
                        reference3.getRef().child("jumlah_coffee").setValue(valuejumlahcoffee.toString());
                        reference3.getRef().child("date_coffee").setValue(date_coffee);
                        reference3.getRef().child("time_coffee").setValue(time_coffee);


                        Intent gotosuccesscoffee = new Intent(CoffeeCheckOutAct.this, SuccessBuyCoffeeAct.class);
                        startActivity(gotosuccesscoffee);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                ref_username = FirebaseDatabase.getInstance().getReference().child("MyCoffee").child(username_key_new).child("username");
                ref_username.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ref_username.getRef().setValue(username_key_new);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // Update data balance kepada users (Yang saat ini login)
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}
