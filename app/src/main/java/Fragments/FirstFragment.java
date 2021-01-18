package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ass3.R;

public class FirstFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        FragmentNotes fragmentNotes = new FragmentNotes();
        getChildFragmentManager().beginTransaction().add(R.id.fragment_container_view_tag, fragmentNotes, null).commit();
        return v;
    }

}