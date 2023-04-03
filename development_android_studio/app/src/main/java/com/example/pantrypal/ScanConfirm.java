package com.example.pantrypal;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ScanConfirm extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        byte[] byteArrayExtra = getIntent().getByteArrayExtra("photo");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length);

        setContentView(R.layout.activity_scan_confirmation);
        imageView = findViewById(R.id.confirm_image);
        imageView.setImageBitmap(bitmap);
        //Bundle bundle = getIntent().getExtras();
        //if (bundle != null) {
            //int resId = bundle.getInt("resId");
            //imageView.setImageResource(resId);
        //}

        Button go_pantry = findViewById(R.id.confirmed);
        go_pantry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        imageView.setImageBitmap(photo);
    }
}
