package com.example.pantrypal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Item> mItemList;
    private final LayoutInflater mInflater;

    public RecipeAdapter(Context context, List<Item> itemList) {
        mInflater = LayoutInflater.from(context);
        mItemList = itemList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recipe_select, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Item currentItem = mItemList.get(position);

        holder.itemNameTextView.setText(currentItem.getI_Name());
        holder.itemExpirationTextView.setText(currentItem.getI_Date());
        holder.checkBox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final TextView itemNameTextView;
        private final TextView itemExpirationTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemExpirationTextView = itemView.findViewById(R.id.itemExpirationTextView);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item currentItem = mItemList.get(getBindingAdapterPosition());
                    String itemName = currentItem.getI_Name();
                    for (int i=0; 1<Recipe.ingredients.length; i++){
                        if (Recipe.ingredients[i] == itemName){
                            Recipe.ingredients[i] = "";
                            checkBox.setChecked(false);
                            break;}
                        else if(Recipe.ingredients[i] == ""){
                            Recipe.ingredients[i] = itemName;
                            checkBox.setChecked(true);
                            break;}
                    }
                }
            });
        }
    }
}
