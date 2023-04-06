package com.example.pantrypal;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//public class ProductAdapter extends RecyclerView.Adapter<com.example.assignment3.ProductAdapter.ProductViewHolder> {

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mCtx;
    private List<Item> itemList;

    public ItemAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewDate = itemView.findViewById(R.id.item_exp_date);
            textViewAmount = itemView.findViewById(R.id.item_amount);
        }

    }
}
