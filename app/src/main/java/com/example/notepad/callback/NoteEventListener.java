package com.example.notepad.callback;

import com.example.notepad.model.Note;

public interface NoteEventListener {
    /**
     *apeleza cand apesi
     * @param note :note item
     */
    void onNoteClick(Note note);

    /**
     * apeleaza cand apesi lung
     * @param note
     */
    void onNoteLongClick(Note note);
}
