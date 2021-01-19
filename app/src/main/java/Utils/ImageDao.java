package Utils;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.ImageEntity;

@Dao
public interface ImageDao {

    @Query("select * from imageentity")
    List<ImageEntity> getAll();
    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ImageEntity imageCache);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ImageEntity> imageCache);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(ImageEntity repos);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(ImageEntity imageCache);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(List<ImageEntity> imageCache);      // Note... is varargs, here note is an array

    @Query("DELETE FROM imageentity")
    void delete();
}


