
package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;

import android.util.Log;
import android.widget.Button;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Recipe extends AppCompatActivity {
    static String ingredients[] = {"","","","","","","","","",""};
    Inventory inventory;
    public void openLink(View view) {
        String baseUrl = "https://foodcombo.com/find-recipes-by-ingredients";
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
            if (ingredient != "")
            baseUrl += "/" + ingredient;
        }
        System.out.println("trying to open link");
        Uri uri = Uri.parse(baseUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().hide();

        Button findRecipesButton = findViewById(R.id.done);

        // Add an OnClickListener to the button
        findRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(v);
            }
        });

        inventory = createInventory();

        //create recycler view object and set layout info
        RecyclerView fridgeView = (RecyclerView) findViewById(R.id.FridgeList);
        fridgeView.setHasFixedSize(true);
        fridgeView.setLayoutManager(new LinearLayoutManager(this));
        //add list of objects to adapter
        RecipeAdapter fridge = new RecipeAdapter(this, inventory.getfridgeList());
        fridgeView.setAdapter(fridge);

        RecyclerView freezerView = (RecyclerView) findViewById(R.id.FreezerList);
        freezerView.setHasFixedSize(true);
        freezerView.setLayoutManager(new LinearLayoutManager(this));

        RecipeAdapter freezer = new RecipeAdapter(this, inventory.getfreezerList());
        freezerView.setAdapter(freezer);

        RecyclerView cabinetView = (RecyclerView) findViewById(R.id.CabinateList);
        cabinetView.setHasFixedSize(true);
        cabinetView.setLayoutManager(new LinearLayoutManager(this));

        RecipeAdapter cabinet = new RecipeAdapter(this, inventory.getcabinetList());
        cabinetView.setAdapter(cabinet);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.recipe);

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
                        startActivity(new Intent(getApplicationContext(),Pantry.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.recipe:
                        return true;

                }
                return false;
            }
        });
    }

    private Inventory createInventory(){
        Inventory inventory = new Inventory();

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();

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