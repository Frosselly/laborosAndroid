package com.example.lab5;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface PersonDAO {
    @Insert
    void insert(Person person);

    @Query("Delete from Person")
    void deleteAll();

    @Query("Select * from Person Order by first_name ASC")
    List<Person> getAllPersons();
}

