package com.example.pantrypal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//public class ProductAdapter extends RecyclerView.Adapter<com.example.assignment3.ProductAdapter.ProductViewHolder> {

public class ItemEditAdapter extends RecyclerView.Adapter<ItemEditAdapter.ItemEditorViewHolder> {
    private Context mCtx;
    private List<Item> itemList;

    public ItemEditAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @Override
    public ItemEditorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_row, null);
        return new ItemEditorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemEditAdapter.ItemEditorViewHolder holder, int position) {
        //getting the product of the specified position
        Item item = itemList.get(position);
        holder.textViewName.setText(item.getI_Name());
        holder.textViewDate.setText(item.getI_Date());

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

        TextView textViewName, textViewDate;

        public ItemEditorViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewDate = itemView.findViewById(R.id.item_exp_date);
        }

    }
}
