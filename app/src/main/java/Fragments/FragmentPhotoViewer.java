package Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ass3.R;

import java.util.List;

import Adapters.PhotoViewerAdapter;
import Models.ImageEntity;


public class FragmentPhotoViewer extends Fragment {

    private ViewPager2 viewPager;
    private PhotoViewerAdapter adapter;
    private List<ImageEntity> imageList;
    private Bundle bundle;

    public FragmentPhotoViewer(Bundle bundle)
    {
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_viewer, container, false);

        imageList =  (List<ImageEntity>)bundle.getSerializable("imageList");
        viewPager = v.findViewById(R.id.viewPager2);
        adapter = new PhotoViewerAdapter();
        adapter.setCards(imageList);
        viewPager.setAdapter(adapter);
        return v;
    }
}