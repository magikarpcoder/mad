package com.example.wallpaperchangeapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat; // For ContextCompat.getDrawable

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button wallpaperChange;
    Timer mytimer;
    WallpaperManager wpm;
    int prev = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mytimer = new Timer();
        wpm = WallpaperManager.getInstance(this);

        wallpaperChange = findViewById(R.id.button1);
        wallpaperChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaper();
            }
        });
    }

    private void setWallpaper() {
        Toast.makeText(this, "Setting wallpaper, please wait.", Toast.LENGTH_LONG).show();

        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Get the drawable resource based on the current index
                Drawable drawable;
                if (prev == 1) {
                    drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.one);
                    prev = 2;
                } else if (prev == 2) {
                    drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.two);
                    prev = 3;
                } else if (prev == 3) {
                    drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.three);
                    prev = 4;
                } else if (prev == 4) {
                    drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.four);
                    prev = 5;
                } else if (prev == 5) {
                    drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.five);
                    prev = 1;
                } else {
                    return;
                }

                // Convert drawable to bitmap and set as wallpaper
                if (drawable instanceof BitmapDrawable) {
                    Bitmap wallpaper = ((BitmapDrawable) drawable).getBitmap();
                    try {
                        // Run wallpaper setting on the main thread
                        new Handler(getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    wpm.setBitmap(wallpaper);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 8000); // Set to change every 30 seconds
    }
}
