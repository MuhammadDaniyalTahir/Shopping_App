package com.example.firebaseassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.firebaseassignment.model.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddItemFragment extends DialogFragment {

    private EditText itemNameEditText, quantityEditText, priceEditText;
    private Button addButton;
    private FirebaseDatabase database;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        itemNameEditText = view.findViewById(R.id.itemNameEditText);
        quantityEditText = view.findViewById(R.id.quantityEditText);
        priceEditText = view.findViewById(R.id.priceEditText);
        addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(v -> addItem());
    }

    private void addItem() {
        String itemName = itemNameEditText.getText().toString().trim();
        String quantityStr = quantityEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();

        if (itemName.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(getContext(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);

        // Add to Realtime Database
        DatabaseReference itemsRef = database.getReference("items")
                .child(mAuth.getCurrentUser().getUid());
        String itemId = itemsRef.push().getKey();

        ShoppingItem item = new ShoppingItem(itemId, itemName, quantity, price);

        // Save to both Realtime Database and Firestore
        itemsRef.child(itemId).setValue(item);
        firestore.collection("shopping_items")
                .document(itemId)
                .set(item)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), R.string.item_added, Toast.LENGTH_SHORT).show();
                    dismiss();
                })
                .addOnFailureListener(e -> 
                    Toast.makeText(getContext(), R.string.add_item_failed, Toast.LENGTH_SHORT).show());
    }
}
