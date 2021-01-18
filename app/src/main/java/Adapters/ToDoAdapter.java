package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ass3.R;

import java.util.List;

import Fragments.FirstFragment;
import Fragments.FragmentNotes;
import Models.NotesEntity;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<NotesEntity> cardList;
    private FragmentNotes fragmentNotes;
    private ImageButton imageButton;

    public ToDoAdapter(FragmentNotes fragmentNotes)
    {
        this.fragmentNotes = fragmentNotes;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note_card,parent, false);

        return new ViewHolder(itemView);
    }
    public void onBindViewHolder(ViewHolder holder, int position){

        NotesEntity item = cardList.get(position);
        holder.cardTitle.setText(item.getTitle());
        holder.cardDescription.setText(item.getContent());

    }
    public int getItemCount()
    {
        return cardList.size();
    }

    public void setCards(List<NotesEntity> cardList){
        this.cardList =  cardList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView cardTitle,cardDescription;
        ImageButton deleteButton;

        ViewHolder(View view)
        {
            super(view);
            deleteButton = view.findViewById(R.id.imageButton);
            cardTitle = view.findViewById(R.id.cardTitle);
            cardDescription = view.findViewById(R.id.cardDescription);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentNotes.deleteCard(getAdapterPosition());
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        fragmentNotes.editCard(getAdapterPosition());
                   }

            });
        }
    }
}
