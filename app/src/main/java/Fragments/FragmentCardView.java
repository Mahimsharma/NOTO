package Fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Assignment3.R;

import Models.NotesEntity;
import Utils.NoteDao;
import Utils.NotesDatabase;


public class FragmentCardView extends Fragment {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private int index;
    private NoteDao noteDao;
    private TextView textViewCount;
    private Button buttonUpdate;
    private Button buttonAddNote;
    private ActionBar actionBar;
    private View.OnClickListener onClickListener;
    private Bundle bundle;
    boolean flag;
    
    public FragmentCardView(Bundle bundle){
        this.bundle = bundle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_card_view, container, false);
        Toolbar toolbar =  v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpAction();
            }
        });
        onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonClick(v);
            }
        };
        buttonUpdate = v.findViewById(R.id.button_update);
        buttonAddNote = v.findViewById(R.id.button_addNote);
        buttonUpdate.setOnClickListener(onClickListener);
        buttonAddNote.setOnClickListener(onClickListener);
        editTextTitle = v.findViewById(R.id.editTextTitle);
        editTextDescription = v.findViewById(R.id.editTextDescription);
        textViewCount = v.findViewById(R.id.count);
        flag=true;

        noteDao = NotesDatabase.getInstance(getContext()).getNoteDao();
        NotesEntity card = (NotesEntity) bundle.getSerializable("card");
        if (card != null) {
            actionBar.setTitle("Edit Note");

            index = bundle.getInt("position");
            String description = card.getContent();
            editTextTitle.setText(card.getTitle());
            editTextDescription.setText(description);
            if(description.length()>0) textViewCount.setText(String.valueOf(description.length()));
            else textViewCount.setText("0");
        }
        else {
            actionBar.setTitle("Add Card");
            textViewCount.setText("0");
            Button button_update = v.findViewById(R.id.button_update);
            button_update.setVisibility(View.GONE);
        }


        TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textViewCount.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };

        editTextDescription.addTextChangedListener(mTextEditorWatcher);

        return v;
    }

    public void onUpAction()
    {
        FragmentNotes fragmentNotes = new FragmentNotes();
        getParentFragmentManager().beginTransaction().replace(R.id.containerId,fragmentNotes,null).commit();
    }

    public void buttonClick(View v) {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        NotesEntity card = new NotesEntity();

        if(title.equals("")) card.setTitle("Empty title");
        else card.setTitle(title);

        if(description.equals("")) card.setContent("Empty Description");
        else card.setContent(description);

        if(flag)
        {
            flag=false;
            if (v.getTag() != null) {
                noteDao.insert(card);
                Toast.makeText(getActivity(), "Note added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                noteDao.insert(card);
                card.setNote_id(index);
                noteDao.delete(card);

                Toast.makeText(getActivity(), "Note updated", Toast.LENGTH_SHORT).show();
            }

        }
        onUpAction();

    }

}