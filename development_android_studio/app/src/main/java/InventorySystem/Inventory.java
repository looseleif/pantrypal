package InventorySystem;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    /**
     * List of items in this inventory
     */
    private HashMap<Integer, Item> itemMap = new HashMap<Integer, Item>();

    public HashMap<Integer, Item> getItemMap(){
        return this.itemMap;
    }

    public void addItem(Item item){
        itemMap.put(item.getI_Id(), item);
    }

    public Item deleteItem(int id){
        return itemMap.remove(Integer.valueOf(id));
    }






}
