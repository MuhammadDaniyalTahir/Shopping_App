package com.example.shopping_app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Test Firebase connection
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> testData = new HashMap<>();
        testData.put("test", "test");
        
        db.collection("test").document("test")
                .set(testData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Connection successful!");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error: ", e);
                });
    }
}
