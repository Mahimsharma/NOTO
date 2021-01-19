package com.example.Assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Fragments.FragmentNotes;
import Fragments.FragmentPhotos;
import Fragments.ThirdFragment;

public class MainActivity extends AppCompatActivity {
    public static String[] titles = new String[]{"Notes", "Photos", "Profile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.purple_700));
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        setFragment(new FragmentNotes());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.notes:
                        setFragment(new FragmentNotes());
                        break;
                    case R.id.photos:
                        setFragment(new FragmentPhotos());
                        break;
                    case R.id.profile:
                        setFragment(new ThirdFragment());
                        break;

                }
                return true;
            }
        });
//        viewPager = findViewById(R.id.view_pager);
//        viewPager.setUserInputEnabled(false);
//        adapter = new ViewPagerFragmentAdapter(this);
//        viewPager.setAdapter(adapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        new TabLayoutMediator(tabs, viewPager,
//                (tab, position) -> tab.setText(titles[position])
//        ).attach();
    }

    private void setFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerId,fragment).commit();
    }
}