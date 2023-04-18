package com.example.pantrypal;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScanConfirm extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        byte[] byteArrayExtra = getIntent().getByteArrayExtra("photo");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length);

        setContentView(R.layout.activity_scan_confirmation);
        imageView = findViewById(R.id.confirm_image);
        //imageView.setImageBitmap(bitmap);
        //OR
        imageView.setImageResource(R.drawable.receipt_eg);

        getSupportActionBar().hide();

        //Bundle bundle = getIntent().getExtras();
        //if (bundle != null) {
            //int resId = bundle.getInt("resId");
            //imageView.setImageResource(resId);
        //}

        Button go_pantry = findViewById(R.id.confirmed);
        go_pantry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                Type type = new TypeToken<ArrayList<Item>>() {
                }.getType();
                Gson gson = new Gson();

                String json;
                json = sharedPreferences.getString("CabinetList", null);
                ArrayList<Item> inventory = gson.fromJson(json, type);
                inventory.add(new Item("Apples", "4/16/2023", 7, "Cabinet"));

                String json1;
                json1 = sharedPreferences.getString("FreezerList", null);
                ArrayList<Item> inventory1 = gson.fromJson(json1, type);
                inventory1.add(new Item("Ice Cream", "4/12/2023", 2, "Freezer"));

                String json2;
                json2 = sharedPreferences.getString("FridgeList", null);
                ArrayList<Item> inventory2 = gson.fromJson(json2, type);
                inventory2.add(new Item("Milk", "4/06/2023", 1, "Fridge"));
                inventory2.add(new Item("Chicken", "4/07/2023", 4, "Fridge"));

                SharedPreferences.Editor ed = sharedPreferences.edit();
                String update_json = gson.toJson(inventory);
                ed.putString("CabinetList", update_json);

                String update_json1 = gson.toJson(inventory1);
                ed.putString("FreezerList", update_json1);

                String update_json2 = gson.toJson(inventory2);
                ed.putString("FridgeList", update_json2);

                ed.apply();
//                if ("FridgeList".contains(location) && sharedPreferences.contains("FridgeList")) {
//                    ed.putString("FridgeList", update_json);
//                } else if ("FreezerList".contains(location) && sharedPreferences.contains("FreezerList")) {
//                    ed.putString("FreezerList", update_json);
//                } else if ("CabinetList".contains(location) && sharedPreferences.contains("CabinetList")) {
//                    ed.putString("CabinetList", update_json);
//                }

                Intent goPantryIntent = new Intent(getApplicationContext(), Pantry.class);
                startActivity(goPantryIntent);
            }
        });

        Button go_cam = findViewById(R.id.back_to_cam);
        go_cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        //imageView.setImageBitmap(photo);
        // OR
        imageView.setImageResource(R.drawable.receipt_eg);
    }
}
