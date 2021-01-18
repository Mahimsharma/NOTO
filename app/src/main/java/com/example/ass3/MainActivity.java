package com.example.ass3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Adapters.ViewPagerFragmentAdapter;
import Fragments.FirstFragment;
import Fragments.SecondFragment;
import Fragments.ThirdFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ViewPagerFragmentAdapter adapter;
    public static String[] titles = new String[]{"Notes", "Photos", "Profile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        setFragment(new FirstFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.notes:
                        setFragment(new FirstFragment());
                        break;
                    case R.id.photos:
                        setFragment(new SecondFragment());
                        break;
                    case R.id.profile:
                        setFragment(new ThirdFragment());
                        break;
                    default:
                        setFragment(new FirstFragment());
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