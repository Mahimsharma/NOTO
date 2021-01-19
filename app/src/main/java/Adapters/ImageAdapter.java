package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Assignment3.PhotoViewer;
import com.example.Assignment3.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Fragments.FragmentPhotos;
import Models.ImageEntity;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private List<ImageEntity> imageList;
    Context parentContext;

    // data is passed into the constructor
    public ImageAdapter(Context context, List<ImageEntity> imageList) {
        this.imageList = imageList;
        parentContext = context;
    }

    // inflates the cell layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentContext).inflate(R.layout.single_image_card, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / FragmentPhotos.layoutSpan;
        view.setLayoutParams(lp);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageEntity currentImage = imageList.get(position);
        ((ImageAdapter.ViewHolder) holder).updateUI(currentImage);
//        holder.imageView.setImageResource(currentImage.getImage());
//        Picasso.with(parentContext).load(currentImage.getImageUrl()).fit().centerInside().into(holder.imageView);
    }

    public void setCards(List<ImageEntity> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public void addCards(List<ImageEntity> images) {
        imageList.addAll(images);
        notifyItemRangeChanged(0, imageList.size());
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


        }

        public void updateUI(ImageEntity imageEntity) {
            Picasso.with(parentContext).load(imageEntity.getImageUrl()).fit().centerInside().into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(parentContext,"Clicked"+String.valueOf(getAdapterPosition()),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(parentContext, PhotoViewer.class);
                    intent.putExtra("imageURL", imageEntity.getImageUrl());
                    parentContext.startActivity(intent);

                }
            });
        }

    }

}