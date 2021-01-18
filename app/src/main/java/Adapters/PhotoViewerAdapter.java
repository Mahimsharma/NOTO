package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass3.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.ImageEntity;

public class PhotoViewerAdapter extends RecyclerView.Adapter<PhotoViewerAdapter.PhotoHolder> {


    private List<ImageEntity> imageList;
    Context parentContext;
    // data is passed into the constructor
    public PhotoViewerAdapter() {

    }

    // inflates the cell layout from xml when needed
    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_card,parent,false);
        parentContext = parent.getContext();
        return new PhotoHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder( PhotoHolder holder, int position) {
        ImageEntity currentImage = imageList.get(position);
//        holder.imageView.setImageResource(currentImage.getImage());
        Picasso.with(parentContext).load(currentImage.getImageUrl()).fit().centerInside().into(holder.imageView);
    }

    public void setCards(List<ImageEntity> imageList){
        this.imageList =  imageList;
        notifyDataSetChanged();
    }
    // total number of cells
    @Override
    public int getItemCount() {
        return imageList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class PhotoHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        PhotoHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
       }
    }



    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}