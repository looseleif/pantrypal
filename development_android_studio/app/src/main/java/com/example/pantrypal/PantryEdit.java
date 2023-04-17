
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

    Inventory inventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_edit);

        inventory = createInventory();

        getSupportActionBar().hide();

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

        RecyclerView cabinetView = (RecyclerView) findViewById(R.id.CabinetList);
        cabinetView.setHasFixedSize(true);
        cabinetView.setLayoutManager(new LinearLayoutManager(this));

        ItemEditAdapter cabinetAdapter = new ItemEditAdapter(this, inventory.getcabinetList());
        cabinetView.setAdapter(cabinetAdapter);


        Button editButton = (Button) findViewById(R.id.done);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryEdit.this, Pantry.class);
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
        fridge.forEach(item->{
            inventory.addFridgeItem(item);
        });
        freezer.forEach(item->{
            inventory.addFreezerItem(item);
        });
        cabinet.forEach(item->{
            inventory.addCabinetItem(item);
        });
        return inventory;
    }
}