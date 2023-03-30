package com.example.pantrypal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Inventory {
    /**
     * List of items by location
     */
    private ArrayList<Item> fridgeList;
    private ArrayList<Item> freezerList;
    private ArrayList<Item> cabinetList;

    public Inventory(){
        fridgeList = new ArrayList<Item>();
        freezerList = new ArrayList<Item>();
        cabinetList = new ArrayList<Item>();
    }

    public ArrayList<Item> getfridgeList(){

        return this.fridgeList;
    }

    public ArrayList<Item> getfreezerList(){

        return this.freezerList;
    }

    public ArrayList<Item> getcabinetList(){

        return this.cabinetList;
    }

    public void addFridgeItem(Item item){

        fridgeList.add(item);
    }

    public void addFreezerItem(Item item){
        freezerList.add(item);
    }

    public void addCabinetItem(Item item){

        cabinetList.add(item);
    }

    public Item deleteFridgeItem(int position){
        return fridgeList.remove(position);
    }

    public Item deleteFreezerItem(int position){
        return freezerList.remove(position);
    }

    public Item deleteCabinetItem(int positon){
        return cabinetList.remove(positon);
    }

}
