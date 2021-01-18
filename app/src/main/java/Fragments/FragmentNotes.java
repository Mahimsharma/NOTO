package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ass3.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import Adapters.ToDoAdapter;
import Models.NotesEntity;
import Utils.NoteDao;
import Utils.NotesDatabase;


public class FragmentNotes extends Fragment {

    private RecyclerView recyclerView;
    private ToDoAdapter cardAdapter;
    private List<NotesEntity> cardList;
    private FloatingActionButton fab;
    private ActionBar actionBar;
    boolean flag;
    NoteDao notesDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes, container, false);
        Toolbar toolbar =  v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Notes");
        fab=v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });
        notesDao =  NotesDatabase.getInstance(getActivity()).getNoteDao();
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardAdapter = new ToDoAdapter(this);
        recyclerView.setAdapter(cardAdapter);
        flag=true;
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        flag=true;
        cardList = notesDao.getAll();
        Collections.reverse(cardList);
        cardAdapter.setCards(cardList);

    }

    public void addCard()
    {
        FragmentCardView fragmentCardView = new FragmentCardView(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,fragmentCardView,null).commit();

    }
    public void editCard(int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("card", (Serializable) cardList.get(position));
                bundle.putInt("position",position);
                FragmentCardView fragmentCardView = new FragmentCardView(bundle);
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,fragmentCardView,null).commit();
    }


    public void deleteCard(int position) {

            NotesEntity card = cardList.get(position);
            notesDao.delete(card);
            cardList.remove(position);
            Toast.makeText(getActivity(),"Note Deleted!",Toast.LENGTH_SHORT).show();
            cardAdapter.notifyItemRemoved(position);
            cardAdapter.notifyItemRangeChanged(position,cardList.size());

    }

}