
package com.example.pantrypal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PantryEdit extends AppCompatActivity {
    ArrayList<Item> fullInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_edit);

        Inventory inventory = createInventory();

        //create recycler view object and set layout info
        RecyclerView fridgeView = (RecyclerView) findViewById(R.id.FridgeList);
        fridgeView.setHasFixedSize(true);
        fridgeView.setLayoutManager(new LinearLayoutManager(this));
        //add list of objects to adapter
        ItemEditAdapter fridgeAdapter = new ItemEditAdapter(this, inventory.getfridgeList());
        fridgeView.setAdapter(fridgeAdapter);

        RecyclerView freezerView = (RecyclerView) findViewById(R.id.FreezerList);
        freezerView.setHasFixedSize(true);
        freezerView.setLayoutManager(new LinearLayoutManager(this));

        ItemEditAdapter freezerAdapter = new ItemEditAdapter(this, inventory.getfreezerList());
        freezerView.setAdapter(freezerAdapter);

        RecyclerView cabinetView = (RecyclerView) findViewById(R.id.CabinateList);
        cabinetView.setHasFixedSize(true);
        cabinetView.setLayoutManager(new LinearLayoutManager(this));

        ItemEditAdapter cabinetAdapter = new ItemEditAdapter(this, inventory.getcabinetList());
        cabinetView.setAdapter(cabinetAdapter);


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
                fullInventory.forEach(item->{
                    if(item.getI_Location().equals("Cabinet")){
                        inventory.addCabinetItem(item);
                    } else if (item.getI_Location().equals("Fridge")) {
                        inventory.addFridgeItem(item);
                    }else{
                        inventory.addFreezerItem(item);
                    }
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
        editor.apply();

        Log.i("data saved", "Saved data!");
    }
}