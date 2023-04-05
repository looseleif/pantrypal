
package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.util.Log;

import java.util.ArrayList;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Pantry extends AppCompatActivity {
    ArrayList<Item> fullInventory;
    Inventory inventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        inventory = createInventory();

        //create recycler view object and set layout info
        RecyclerView fridgeView = (RecyclerView) findViewById(R.id.FridgeList);
        fridgeView.setHasFixedSize(true);
        fridgeView.setLayoutManager(new LinearLayoutManager(this));
        //add list of objects to adapter
        ItemAdapter fridgeAdapter = new ItemAdapter(this, inventory.getfridgeList());
        fridgeView.setAdapter(fridgeAdapter);

        RecyclerView freezerView = (RecyclerView) findViewById(R.id.FreezerList);
        freezerView.setHasFixedSize(true);
        freezerView.setLayoutManager(new LinearLayoutManager(this));

        ItemAdapter freezerAdapter = new ItemAdapter(this, inventory.getfreezerList());
        freezerView.setAdapter(freezerAdapter);

        RecyclerView cabinetView = (RecyclerView) findViewById(R.id.CabinetList);
        cabinetView.setHasFixedSize(true);
        cabinetView.setLayoutManager(new LinearLayoutManager(this));

        ItemAdapter cabinetAdapter = new ItemAdapter(this, inventory.getcabinetList());
        cabinetView.setAdapter(cabinetAdapter);

        Button recipeButton = (Button) findViewById(R.id.find_recipe);
        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantry.this, Recipe.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("itemList", fullInventory);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button editButton = (Button) findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantry.this, PantryEdit.class);
//                Bundle bundle = new Bundle();
//                bundle.putParcelableArrayList("itemList", fullInventory);
//                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.pantry);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(),Scan.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pantry:
                        return true;
                    case R.id.recipe:
                        startActivity(new Intent(getApplicationContext(),Recipe.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        Log.i("data saved", "Saved not data!");
    }
    
    private Inventory createInventory(){
            Inventory inventory = new Inventory();

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list", null);
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            fullInventory = gson.fromJson(json, type);

        ArrayList<Item> fridge = new ArrayList<Item>();
        ArrayList<Item> freezer = new ArrayList<Item>();
        ArrayList<Item> cabinet = new ArrayList<Item>();

        if(sharedPreferences.contains("FridgeList")){
            json = sharedPreferences.getString("FridgeList", null);
            type = new TypeToken<ArrayList<Item>>() {}.getType();
            fridge = gson.fromJson(json, type);
        }
        if(sharedPreferences.contains("FreezerList")){
            json = sharedPreferences.getString("FreezerList", null);
            type = new TypeToken<ArrayList<Item>>() {}.getType();
            freezer = gson.fromJson(json, type);
        }
        if(sharedPreferences.contains("CabinetList")){
            json = sharedPreferences.getString("CabinetList", null);
            type = new TypeToken<ArrayList<Item>>() {}.getType();
            cabinet = gson.fromJson(json, type);
        }

            if (fullInventory == null) {
                Item milk = new Item(0, "Milk", "10/11/2023", "Diary", 1, "Fridge");
                Item ice_cream = new Item(1, "Ice Cream", "10/12/2023", "Diary", 1, "Freezer");
                Item apples = new Item(2, "Apples", "10/11/2023", "Fruit", 10, "Cabinet");
                fullInventory = new ArrayList<Item>();
                fullInventory.add(milk);
                fullInventory.add(ice_cream);
                fullInventory.add(apples);

                inventory.addFridgeItem(milk);
                inventory.addFreezerItem(ice_cream);
                inventory.addCabinetItem(apples);
            }
            else{
//                fullInventory.forEach(item->{
//                    if(item.getI_Location().equals("Cabinet")){
//                        inventory.addCabinetItem(item);
//                    } else if (item.getI_Location().equals("Fridge")) {
//                        inventory.addFridgeItem(item);
//                    }else{
//                        inventory.addFreezerItem(item);
//                    }
//                });
                fridge.forEach(item->{
                    inventory.addFridgeItem(item);
                });
                freezer.forEach(item->{
                    inventory.addFreezerItem(item);
                });
                cabinet.forEach(item->{
                    inventory.addCabinetItem(item);
                });
            }
        return inventory;
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(fullInventory);
        editor.putString("task list", json);

        json = gson.toJson(inventory.getfridgeList());
        editor.putString("FridgeList", json);

        json = gson.toJson(inventory.getfreezerList());
        editor.putString("FreezerList", json);

        json = gson.toJson(inventory.getcabinetList());
        editor.putString("CabinetList", json);
        editor.apply();

        Log.i("data saved", "Saved data!");
    }
}