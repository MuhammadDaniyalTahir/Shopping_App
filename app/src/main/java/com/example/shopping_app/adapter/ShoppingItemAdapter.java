package com.example.firebaseassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseassignment.R;
import com.example.firebaseassignment.model.ShoppingItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;

public class ShoppingItemAdapter extends FirebaseRecyclerAdapter<ShoppingItem, ShoppingItemAdapter.ShoppingItemViewHolder> {

    public ShoppingItemAdapter(@NonNull FirebaseRecyclerOptions<ShoppingItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position, @NonNull ShoppingItem model) {
        // Set the item name
        holder.itemNameTextView.setText(model.getItemName());
        
        // Set the quantity with label
        String quantityText = holder.itemView.getContext().getString(
                R.string.quantity_label) + " " + model.getQuantity();
        holder.quantityTextView.setText(quantityText);
        
        // Set the price with currency format
        String priceText = holder.itemView.getContext().getString(
                R.string.price_label) + String.format("%.2f", model.getPrice());
        holder.priceTextView.setText(priceText);

        // Handle delete button click
        holder.deleteButton.setOnClickListener(v -> {
            getRef(position).removeValue()
                    .addOnSuccessListener(aVoid -> Toast.makeText(holder.itemView.getContext(),
                            "Item deleted successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(),
                            "Failed to delete item", Toast.LENGTH_SHORT).show());
        });
    }

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping, parent, false);
        return new ShoppingItemViewHolder(view);
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        super.onError(error);
        // Handle any errors that occur
        System.out.println("Error: " + error.getMessage());
    }

    static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView quantityTextView;
        TextView priceTextView;
        ImageButton deleteButton;

        public ShoppingItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
} 
