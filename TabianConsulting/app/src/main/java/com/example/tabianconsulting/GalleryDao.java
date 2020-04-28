package com.example.tabianconsulting;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GalleryDao {
    @Insert
    void insert(Category cat);

    @Update
    void updateCategory(Category cat);

    @Query("DELETE FROM Category ")
    void deleteAllCategory();

    @Query("SELECT * FROM Category ")
    LiveData<List<Category>> getAllCategory();
    @Delete
    void deleteCategory(Category cat);
}
