package com.example.pantrypal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

//public class ProductAdapter extends RecyclerView.Adapter<com.example.assignment3.ProductAdapter.ProductViewHolder> {

public class ItemEditAdapter extends RecyclerView.Adapter<ItemEditAdapter.ItemEditorViewHolder> {
    private Context mCtx;
    private List<Item> itemList;

    RecyclerView mRecyclerView;

    public ItemEditAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
    @Override
    public ItemEditorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_row_edit, null);
        return new ItemEditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemEditAdapter.ItemEditorViewHolder holder, int position) {
        //getting the product of the specified position
        Item item = itemList.get(position);
        holder.textViewName.setText(item.getI_Name());
        holder.editViewAmount.setText(String.valueOf(item.getI_Amount()), TextView.BufferType.EDITABLE);
        holder.editViewDate.setText(item.getI_Date(), TextView.BufferType.EDITABLE);

        holder.editViewAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
                        int amount_int;
                        try{
                           amount_int =  Integer.valueOf(editable.toString()).intValue();
                        }catch(Exception e){
                            amount_int = 0;
                        }

                        inventory.get(holder.getBindingAdapterPosition()).setI_Amount(amount_int);

                        SharedPreferences.Editor ed = sharedPreferences.edit();
                        String update_json = gson.toJson(inventory);
                        ed.putString(name, update_json);
                        ed.apply();

                        Log.i("Update", update_json);
//                        Log.i("Update 2", );

            }
        });

        holder.editViewDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
                inventory.get(holder.getBindingAdapterPosition()).setI_Date(editable.toString());

                SharedPreferences.Editor ed = sharedPreferences.edit();
                String update_json = gson.toJson(inventory);
                ed.putString(name, update_json);
                ed.apply();

                Log.i("Update", update_json);
//                        Log.i("Update 2", );

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

    class ItemEditorViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        EditText editViewAmount, editViewDate;

        public ItemEditorViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
            editViewAmount = itemView.findViewById(R.id.editAmount);
            editViewDate = itemView.findViewById(R.id.editDate);
        }
    }
}
