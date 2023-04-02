package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO implement lists for expiring soon and receipts
        ArrayList<Item> recentReceipts = new ArrayList<Item>();
        ArrayList<Item> expiringSoon = new ArrayList<Item>();

        //RecyclerView
        RecyclerView recentReceiptView = (RecyclerView) findViewById(R.id.recentReceiptList);
        recentReceiptView.setHasFixedSize(true);
        recentReceiptView.setLayoutManager(new LinearLayoutManager(this));
        //add list of objects to adapter
        ItemAdapter recentRAdapter = new ItemAdapter(this, recentReceipts);
        recentReceiptView.setAdapter(recentRAdapter);

        RecyclerView expiringView = (RecyclerView) findViewById(R.id.expiringList);
        expiringView.setHasFixedSize(true);
        expiringView.setLayoutManager(new LinearLayoutManager(this));

        ItemAdapter expiringAdapter = new ItemAdapter(this, expiringSoon);
        expiringView.setAdapter(expiringAdapter);

        ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });

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
