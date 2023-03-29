
package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Pantry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        Inventory inventory = createInventory();

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

        RecyclerView cabinetView = (RecyclerView) findViewById(R.id.CabinateList);
        cabinetView.setHasFixedSize(true);
        cabinetView.setLayoutManager(new LinearLayoutManager(this));

        ItemAdapter cabinetAdapter = new ItemAdapter(this, inventory.getcabinetList());
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
    }
    
    private Inventory createInventory(){
        Inventory inventory = new Inventory();

        Item milk = new Item(0, "Milk", "10/11/2023", "Diary", 1, "Fridge");
        Item ice_cream = new Item(1, "Ice Cream", "10/12/2023", "Diary", 1, "Freezer");
        Item apples = new Item(2, "Apples", "10/11/2023", "Fruit", 10, "Cabinet");

        inventory.addFridgeItem(milk);
        inventory.addFreezerItem(ice_cream);
        inventory.addCabinetItem(apples);

        return inventory;
    }
}