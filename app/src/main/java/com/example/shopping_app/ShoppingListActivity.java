package com.example.firebaseassignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseassignment.adapter.ShoppingItemAdapter;
import com.example.firebaseassignment.model.ShoppingItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ShoppingListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShoppingItemAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Initialize database reference
        dbRef = FirebaseDatabase.getInstance().getReference()
                .child("items")
                .child(mAuth.getCurrentUser().getUid());
        
        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        setupRecyclerView();
        
        fabAdd.setOnClickListener(v -> {
            AddItemFragment addItemFragment = new AddItemFragment();
            addItemFragment.show(getSupportFragmentManager(), "ADD_ITEM");
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        
        // Create Firebase query
        Query query = dbRef.orderByChild("itemName");

        // Configure recycler adapter options
        FirebaseRecyclerOptions<ShoppingItem> options = 
                new FirebaseRecyclerOptions.Builder<ShoppingItem>()
                        .setQuery(query, ShoppingItem.class)
                        .build();

        // Initialize adapter
        adapter = new ShoppingItemAdapter(options);
        recyclerView.setAdapter(adapter);

        // Add data change listener
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
