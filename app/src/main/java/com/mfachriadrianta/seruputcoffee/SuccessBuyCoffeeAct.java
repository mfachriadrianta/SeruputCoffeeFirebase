package com.mfachriadrianta.seruputcoffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessBuyCoffeeAct extends AppCompatActivity {

    Animation app_splash, btt, ttb;
    Button btn_my_dashboard, btn_view_coffee;
    TextView app_title, app_subtitle;
    ImageView icon_success_coffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_coffee);

        btn_view_coffee = findViewById(R.id.btn_view_coffee);
        btn_my_dashboard = findViewById(R.id.btn_my_dashboard);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        icon_success_coffee = findViewById(R.id.icon_success_coffee);

        // Load Animation
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        // Run Animation
        icon_success_coffee.startAnimation(app_splash);

        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_view_coffee.startAnimation(btt);
        btn_my_dashboard.startAnimation(btt);

        btn_view_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoprofile = new Intent(SuccessBuyCoffeeAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(SuccessBuyCoffeeAct.this, HomeAct.class);
                startActivity(gotohome);
            }
        });

    }
}
