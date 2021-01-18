package com.example.ass3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import Adapters.PhotoViewerAdapter;
import Models.ImageEntity;

public class PhotoViewer extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PhotoViewerAdapter adapter;
    private List<ImageEntity> imageList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photo_viewer);
        getSupportActionBar().setTitle("PhotoViewer");
        Bundle bundle = getIntent().getExtras();
        imageList =  (List<ImageEntity>)bundle.getSerializable("imageList");
        viewPager = findViewById(R.id.viewPager2);
        adapter = new PhotoViewerAdapter();
        adapter.setCards(imageList);
        viewPager.setAdapter(adapter);

    }
}
