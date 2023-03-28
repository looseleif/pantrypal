package com.example.pantrypal;

import java.util.HashMap;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Inventory extends RecyclerView.Adapter<Inventory.InventoryViewHolder>{
    /**
     * List of items in this inventory
     */
    private HashMap<Integer, Item> itemMap = new HashMap<Integer, Item>();

    /**
     * ???
     */
    private Context mCtx;

    @Override
    public Inventory.InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new Inventory.InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Inventory.InventoryViewHolder holder, int position) {
        //getting the product of the specified position
        Item product = itemMap.get(position);

        //binding the data with the viewholder views
//        holder.textViewTitle.setText(product.getTitle());
//        holder.textViewShortDesc.setText(product.getShortdesc());
//        holder.textViewRating.setText(String.valueOf(product.getRating()));
//        holder.textViewPrice.setText(String.valueOf(product.getPrice()));


    }

    @Override
    public int getItemCount() {
        return itemMap.size();
    }

    public HashMap<Integer, Item> getItemMap(){
        return this.itemMap;
    }

    public void addItem(Item item){
        itemMap.put(item.getI_Id(), item);
    }

    public Item deleteItem(int id){
        return itemMap.remove(Integer.valueOf(id));
    }

    class InventoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;

        public InventoryViewHolder(View itemView) {
            super(itemView);

//            textViewTitle = itemView.findViewById(R.id.textViewTitle);
//            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
//            textViewRating = itemView.findViewById(R.id.textViewRating);
//            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
