package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.notepad.database.NotesDAO;
import com.example.notepad.database.NotesDB;
import com.example.notepad.model.Note;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
private EditText inputNote;
private NotesDAO dao;
private Note temp;
public static final String NOTE_EXTRA_Key="note_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        inputNote=findViewById(R.id.input_text);
        dao=NotesDB.getInstance(this).notesDAO();

        if (getIntent().getExtras()!=null){
            int id=getIntent().getExtras().getInt(NOTE_EXTRA_Key,0);
            temp=dao.getNoteById(id);
            inputNote.setText(temp.getNoteText());
        }else
            temp=new Note();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edit_note,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id==R.id.save_note){
            onSaveNote();
        }
        return super.onOptionsItemSelected(item);
    }
    private void onSaveNote(){
        // TODO: 2020-02-07 save note
        String text =inputNote.getText().toString();
        if (!text.isEmpty()){
            long date =new Date().getTime();
            temp.setNoteDate(date);
            temp.setNoteText(text);
            if (temp.getId()==-1)
            dao.insertNote(temp);//insereaza si salveaza in baza de date
            else
                dao.updateNote(temp);
            finish(); //te duce inapoi in main activity
        }
    }

}
