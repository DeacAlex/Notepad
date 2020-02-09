package com.example.notepad.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notepad.model.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)// daca exista notita, o inlocuieste
    void insertNote(Note note);
    @Delete
    void deleteNote(Note note);
    @Update
    void updateNote(Note note);
    //listeaza toate notitele din baza de date
@Query("SELECT * FROM notes")
List<Note> getNotes();
@Query("SELECT * FROM notes WHERE id=:noteId")//ofera notitele dupa id
Note getNoteById(int noteId);
@Query("DELETE FROM notes WHERE id=:noteId")
void deleteNoteById(int noteId);
}
