package com.example.ewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FlashActivity extends AppCompatActivity {

    final static int SPLASH_SCREEN = 5000;

    // variable
    Animation topAnim, bottomAnim;
    private ImageView img;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        //animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //hooks
        img = findViewById(R.id.logo_img);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);

        img.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(FlashActivity.this, LoginActivity.class);
            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(img, "logo_img");
            pairs[1] = new Pair<View, String>(logo, "logo_text");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FlashActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }, SPLASH_SCREEN);
    }
}