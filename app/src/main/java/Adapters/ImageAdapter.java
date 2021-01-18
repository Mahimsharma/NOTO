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

import Fragments.FragmentPhotos;
import Models.ImageEntity;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private List<ImageEntity> imageList;
    private FragmentPhotos fragmentPhotos;
    Context parentContext;
    // data is passed into the constructor
    public ImageAdapter(FragmentPhotos fragmentPhotos, List<ImageEntity> imageList) {
        this.imageList=imageList;
        this.fragmentPhotos = fragmentPhotos;
    }

    // inflates the cell layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_image_card,parent,false);
        parentContext = parent.getContext();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        ImageEntity currentImage = imageList.get(position);
//        holder.imageView.setImageResource(currentImage.getImage());
        Picasso.with(parentContext).load(currentImage.getImageUrl()).fit().centerInside().into(holder.imageView);
    }

    public void setCards(List<ImageEntity> imageList){
        this.imageList =  imageList;
        notifyDataSetChanged();
    }
    public void addCards(List<ImageEntity> imageList){
        this.imageList =  imageList;
        notifyDataSetChanged();
    }
    // total number of cells
    @Override
    public int getItemCount() {
        return imageList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(parentContext,"Clicked"+String.valueOf(getAdapterPosition()),Toast.LENGTH_SHORT).show();
                    fragmentPhotos.click();
                }
            });
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