package com.example.pantrypal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;

public class ItemAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_popup);

        Spinner dropdown = findViewById(R.id.addItemLocation);
        String[] items = new String[]{"Fridge", "Freezer", "Cabinet"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        EditText name = (EditText)findViewById(R.id.addItemName);
        EditText amount = (EditText)findViewById(R.id.addItemAmount);
        EditText date = (EditText)findViewById(R.id.addItemDate);

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = dropdown.getSelectedItem().toString();
                if("FridgeList".contains(location)){

                } else if ("FreezerList".contains(location)) {

                }else if ("CabinetList".contains(location)){

                }
            }
        });
    }
}