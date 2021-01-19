package Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ImageEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String imageUrl;
    private String imageTitle;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ImageEntity(String imageUrl,String imageTitle)
    {
        this.imageUrl=imageUrl;
        this.imageTitle=imageTitle;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

}
