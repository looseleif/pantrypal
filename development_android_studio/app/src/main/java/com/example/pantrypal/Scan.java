
package com.example.pantrypal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;

public class Scan extends AppCompatActivity {
    private static final int pic_id = 100;

    ImageButton camera_open;
    ImageView click_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.scan);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.scan:
                        return true;
                    case R.id.pantry:
                        startActivity(new Intent(getApplicationContext(), Pantry.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.recipe:
                        startActivity(new Intent(getApplicationContext(), Recipe.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        camera_open = findViewById(R.id.camera_button);
        //click_image = findViewById(R.id.click_image);
        camera_open.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(cameraIntent, pic_id);
                startActivityForResult(cameraIntent, 0);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap) data.getExtras().get("data");
//        if (requestCode == pic_id) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            click_image.setImageBitmap(photo);
//        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();

        Intent intent = new Intent(this, ScanConfirm.class);
        intent.putExtra("photo", image);
        startActivity(intent);
    }

    public void receiptHistory(View view) {
        Intent intent = new Intent(this, ReceiptHistoryActivity.class);
        startActivity(intent);
    }
}