package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Item> expiringSoonInventoryList;
    ArrayList<Item> expiringSoonFridgeList;
    ArrayList<Item> expiringSoonFreezerList;
    ArrayList<Item> expiringSoonCabinetList;
    Inventory expiringSoonInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO implement lists for receipts
        ArrayList<Item> recentReceipts = new ArrayList<Item>();
        expiringSoonInventoryList = new ArrayList<Item>();
        expiringSoonFridgeList = new ArrayList<Item>();
        expiringSoonFreezerList = new ArrayList<Item>();
        expiringSoonCabinetList = new ArrayList<Item>();

        //import inventory from file
        expiringSoonInventory = new Inventory();
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json;

        //if the pantry list has items, add them to expiring soon
        //Get data fromm JSON and add to respective lists.
        //TODO add check for approaching expiration dates
        if(sharedPreferences.contains("FridgeList")){
            json = sharedPreferences.getString("FridgeList", null);
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            expiringSoonFridgeList = gson.fromJson(json, type);
            expiringSoonFridgeList.forEach(item->{
                expiringSoonInventory.addFridgeItem(item);
                expiringSoonInventoryList.add(item);
            });
        }
        if(sharedPreferences.contains("FreezerList")){
            json = sharedPreferences.getString("FreezerList", null);
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            expiringSoonFreezerList = gson.fromJson(json, type);
            expiringSoonFreezerList.forEach(item->{
                expiringSoonInventory.addFreezerItem(item);
                expiringSoonInventoryList.add(item);
            });
        }
        if(sharedPreferences.contains("CabinetList")){
            json = sharedPreferences.getString("CabinetList", null);
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            expiringSoonCabinetList = gson.fromJson(json, type);
            expiringSoonCabinetList.forEach(item->{
                expiringSoonInventory.addCabinetItem(item);
                expiringSoonInventoryList.add(item);
            });
        }

        if(expiringSoonInventoryList.isEmpty()){
            Item emptyReceiptsItem = new Item(0, "You have not scanned any receipts yet!", "", "",0 , "");
            recentReceipts.add(emptyReceiptsItem);
        } else {
            Item anyReceiptsItem = new Item(0, "Receipt 1", "4/6/2023", "", 1, "");
            recentReceipts.add(anyReceiptsItem);
        }
        //RECYCLER VIEWS
        //recent receipts recycler view
        RecyclerView recentReceiptView = (RecyclerView) findViewById(R.id.recentReceiptList);
        recentReceiptView.setHasFixedSize(true);
        recentReceiptView.setLayoutManager(new LinearLayoutManager(this));
        //add list of objects to adapter
        ItemAdapter recentRAdapter = new ItemAdapter(this, recentReceipts);
        recentReceiptView.setAdapter(recentRAdapter);

        //expiring soon recycler view
        RecyclerView expiringView = (RecyclerView) findViewById(R.id.expiringList);
        expiringView.setHasFixedSize(true);
        expiringView.setLayoutManager(new LinearLayoutManager(this));
        //add list of objects to adapter
        ItemAdapter expiringAdapter = new ItemAdapter(this, expiringSoonInventoryList);
        expiringView.setAdapter(expiringAdapter);

        //SETTINGS BUTTON
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

        //BOTTOM NAV
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.scan:
                        startActivity(new Intent(getApplicationContext(),Scan.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pantry:
                        startActivity(new Intent(getApplicationContext(),Pantry.class));
                        overridePendingTransition(0,0);
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


}
