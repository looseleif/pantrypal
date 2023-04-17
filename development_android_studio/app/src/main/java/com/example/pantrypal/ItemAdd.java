package com.example.pantrypal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import android.util.Log;

public class ItemAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_popup);

        Spinner dropdown = findViewById(R.id.addItemLocation);
        String[] items = new String[]{"Fridge", "Freezer", "Cabinet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        EditText name = (EditText) findViewById(R.id.addItemName);
        EditText amount = (EditText) findViewById(R.id.addItemAmount);
        EditText date = (EditText) findViewById(R.id.addItemDate);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = dropdown.getSelectedItem().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                String json;
                if ("FridgeList".contains(location) && sharedPreferences.contains("FridgeList")) {
                    json = sharedPreferences.getString("FridgeList", null);
                } else if ("FreezerList".contains(location) && sharedPreferences.contains("FreezerList")) {
                    json = sharedPreferences.getString("FreezerList", null);
                } else if ("CabinetList".contains(location) && sharedPreferences.contains("CabinetList")) {
                    json = sharedPreferences.getString("CabinetList", null);
                } else {
                    json = null;
                }

                Type type = new TypeToken<ArrayList<Item>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<Item> inventory = gson.fromJson(json, type);
                int amount_int = 0;
                try{
                    amount_int = Integer.valueOf(amount.getText().toString());
                }catch(Exception e){
                    amount_int = 0;
                }
                inventory.add(new Item(name.getText().toString(), date.getText().toString(), amount_int, location));

                SharedPreferences.Editor ed = sharedPreferences.edit();
                String update_json = gson.toJson(inventory);

                if ("FridgeList".contains(location) && sharedPreferences.contains("FridgeList")) {
                    ed.putString("FridgeList", update_json);
                } else if ("FreezerList".contains(location) && sharedPreferences.contains("FreezerList")) {
                    ed.putString("FreezerList", update_json);
                } else if ("CabinetList".contains(location) && sharedPreferences.contains("CabinetList")) {
                    ed.putString("CabinetList", update_json);
                }
                ed.apply();
                Intent intent = new Intent(ItemAdd.this, Pantry.class);
                startActivity(intent);
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemAdd.this, Pantry.class);
                startActivity(intent);
            }
        });
    }
}