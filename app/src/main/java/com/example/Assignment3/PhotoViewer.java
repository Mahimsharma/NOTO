package com.example.Assignment3;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PhotoViewer extends AppCompatActivity {

//    private ViewPager2 viewPager;
//    private PhotoViewerAdapter adapter;
    private String imageURL;
    private ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_photo_viewer);
        imageView=findViewById(R.id.imageView);
        getSupportActionBar().setTitle("PhotoViewer");
        imageURL = getIntent().getStringExtra("imageURL");
//        viewPager = findViewById(R.id.viewPager2);
//        adapter = new PhotoViewerAdapter();
//        adapter.setCards(imageList);
//        viewPager.setAdapter(adapter);

        Picasso.with(this).load(imageURL).fit().centerInside().into(imageView);

    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return false;
//    }
}
