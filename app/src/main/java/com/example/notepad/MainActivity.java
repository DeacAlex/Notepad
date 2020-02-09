package com.example.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.notepad.adapter.NotesAdapter;
import com.example.notepad.callback.NoteEventListener;
import com.example.notepad.database.NotesDAO;
import com.example.notepad.database.NotesDB;
import com.example.notepad.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

import static com.example.notepad.EditNoteActivity.NOTE_EXTRA_Key;

public class MainActivity extends AppCompatActivity implements NoteEventListener {
    private static final String TAG="MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note>notes;
    private NotesAdapter adapter;
    private NotesDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //init recycl view
        recyclerView=findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



//init buton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-02-07 add new note
                onAddNewNote();
            }
        });
        dao= NotesDB.getInstance(this).notesDAO();
    }

    private void loadNotes() {
        this.notes=new ArrayList<>();
        List<Note> list = dao.getNotes();// ia toate notitele din baza de date
        this.notes.addAll(list);
        this.adapter =new NotesAdapter(this.notes, this);
        //set listener to adapter
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
    }

    private void onAddNewNote() {
        // TODO: 2020-02-07 start EditNoteACtivity
        startActivity(new Intent(this,EditNoteActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
            loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {

        // TODO: 2020-02-07 note click :edit note
Intent edit=new Intent(this,EditNoteActivity.class);
edit.putExtra(NOTE_EXTRA_Key,note.getId());
startActivity(edit);
    }

    @Override
    public void onNoteLongClick(final Note note) {
// TODO: 2020-02-07 note long clicked : delete, share
        new AlertDialog.Builder(this).setTitle(R.string.app_name)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 2020-02-07 //delete note and refresh
                        dao.deleteNote(note);
                        loadNotes();
                    }
                })
                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO: 2020-02-07 //share note
                        Intent share= new Intent(Intent.ACTION_SEND);
                        String text=note.getNoteText()+"\n Create on:"+Data.dateFromLong(note.getNoteDate());
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT,text);
                        startActivity(share);

                    }
                })
                .create()
                .show();
    }
}
