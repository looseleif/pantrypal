
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

        Button findRecipesButton = findViewById(R.id.done);

        // Add an OnClickListener to the button
        findRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(v);
            }
        });

        Inventory inventory = new Inventory();
        ArrayList<Item> fullInventory;

        fullInventory = new ArrayList<Item>();

        Item milk = new Item(0, "Milk", "10/11/2023", "Diary", 1, "Fridge");
        Item ice_cream = new Item(1, "Chicken", "10/12/2023", "Diary", 1, "Freezer");
        Item apples = new Item(2, "Apples", "10/11/2023", "Fruit", 3, "Cabinet");
        inventory.addFridgeItem(milk);
        inventory.addFreezerItem(ice_cream);
        inventory.addCabinetItem(apples);
        fullInventory.add(milk);
        fullInventory.add(ice_cream);
        fullInventory.add(apples);
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
}