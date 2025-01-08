package com.vakya.notesservies.services;

import com.vakya.notesservies.exceptions.NotesNotFoundException;
import com.vakya.notesservies.models.Notes;
import com.vakya.notesservies.models.SearchCriteria;

import java.util.List;

public interface NotesService {
    Notes createNote(String title, String content, String category, List<String> tags);
   Notes getNoteById(Long id) throws NotesNotFoundException;
    Notes updateNote(Long noteId, Notes notes);
    List<Notes> getAllNotes();
    void deleteNoteById(Long id) throws NotesNotFoundException;

    List<Notes> searchNotes(SearchCriteria searchCriteria);

}
