package com.example.imgur.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imgur.Adapter.ImageAdapter;
import com.example.imgur.Models.Image;
import com.example.imgur.NetworkUtils.ApiManager;
import com.example.imgur.NetworkUtils.GalleryListener;
import com.example.imgur.NetworkUtils.ImageListener;
import com.example.imgur.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.function.Predicate;

public class MainActivity extends AppCompatActivity {

    ApiManager apiManager;
    ArrayList<Image> images = new ArrayList<>();
    ImageAdapter imageAdapter;

    RecyclerView galleryRecycle;
    EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiManager = new ApiManager(MainActivity.this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10);

        galleryRecycle = findViewById(R.id.main_gallery_recycle);
        searchText = findViewById(R.id.search_edit);
        imageAdapter = new ImageAdapter(this,images);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.COLUMN);
        galleryRecycle.setLayoutManager(new GridLayoutManager(this,2));
        galleryRecycle.setAdapter(imageAdapter);
    }

    public void searchClicked(View view) {

        String text = searchText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this,"Enter Image Id.",Toast.LENGTH_SHORT).show();
            return;
        }
        apiManager.getImage(text, new ImageListener() {
            @Override
            public void imageRecieved(Image image) {
                images.clear();
                images.add(image);
               imageAdapter.notifyDataSetChanged();
            }
        });
    }
}
