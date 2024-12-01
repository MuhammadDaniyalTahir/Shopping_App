package com.example.firebaseassignment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2000;
    private ImageView logoImageView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        mAuth = FirebaseAuth.getInstance();
        logoImageView = findViewById(R.id.logoImageView);

        // Load animations
        Animation translateAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up);

        // Start translate animation
        logoImageView.startAnimation(translateAnim);

        // Start scale animation after translate
        translateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logoImageView.startAnimation(scaleAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Navigate after animations
        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            Intent intent;
            if (currentUser != null) {
                intent = new Intent(SplashActivity.this, ShoppingListActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}
