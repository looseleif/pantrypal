package com.example.pantrypal;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;

//public class ProductAdapter extends RecyclerView.Adapter<com.example.assignment3.ProductAdapter.ProductViewHolder> {

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mCtx;
    private List<Item> itemList;

    RecyclerView mRecyclerView;

    public ItemAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_row, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ItemViewHolder holder, int position) {
        //getting the product of the specified position
        Item item = itemList.get(position);
        holder.textViewName.setText(item.getI_Name());
        holder.textViewDate.setText(item.getI_Date());
        holder.textViewAmount.setText(String.valueOf(item.getI_Amount()));
        String name = mCtx.getResources().getResourceEntryName(mRecyclerView.getId());
        if(name.equals("recentReceiptList") || name.equals("expiringList")){
            holder.deleteButton.setVisibility(View.GONE);
            holder.deleteButton.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mCtx.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                String name = mCtx.getResources().getResourceEntryName(mRecyclerView.getId());
                String json;
                if((name.equals("FridgeList") || name.equals("CabinetList") || name.equals("FreezerList")) && sharedPreferences.contains(name)) {
                    json = sharedPreferences.getString(name, null);
                }else{
                    json = null;
                }

                Type type = new TypeToken<ArrayList<Item>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<Item> inventory = gson.fromJson(json, type);

                inventory.remove(holder.getBindingAdapterPosition());

                SharedPreferences.Editor ed = sharedPreferences.edit();
                String update_json = gson.toJson(inventory);

                ed.putString(name, update_json);
                Log.i("data saved", update_json);
                ed.apply();
                ((Pantry)mCtx).recreate();
            }
        });

        //binding the data with the viewholder views
//        holder.textViewTitle.setText(product.getTitle());
//        holder.textViewShortDesc.setText(product.getShortdesc());
//        holder.textViewRating.setText(String.valueOf(product.getRating()));
//        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
//
//        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDate, textViewAmount;
        ImageButton deleteButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewDate = itemView.findViewById(R.id.item_exp_date);
            textViewAmount = itemView.findViewById(R.id.item_amount);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

    }
}
