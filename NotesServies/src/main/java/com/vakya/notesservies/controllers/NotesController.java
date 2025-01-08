package com.vakya.notesservies.controllers;

import com.vakya.notesservies.dtos.NotesRequestDto;
import com.vakya.notesservies.exceptions.NotesNotFoundException;
import com.vakya.notesservies.models.Notes;
import com.vakya.notesservies.models.SearchCriteria;
import com.vakya.notesservies.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotesController {

    private NotesService noteService;



    @Autowired
    public NotesController(NotesService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/notes")
    public Notes createNote(@RequestBody NotesRequestDto notesRequestDto){
        return noteService.createNote(
                notesRequestDto.getTitle(),
                notesRequestDto.getCategory(),
                notesRequestDto.getContent(),
                notesRequestDto.getTags()
        );
    }

    @PutMapping("notes/{noteId}")
    public Notes updateNote(@PathVariable Long noteId, @RequestBody NotesRequestDto request) {
        Notes note = new Notes();
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());

        // Only set category if it's not null
        if (request.getCategory() != null) {
            note.setCategory(request.getCategory());
        }

        // Only set tags if the list is not empty or null
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            note.setTags(request.getTags());
        }

        // Call the service to update the note
        return noteService.updateNote(noteId, note);
    }

    // Get a note by ID
    @GetMapping("notes/{id}")
    public Notes getNoteById(@PathVariable Long id) throws NotesNotFoundException {
        return noteService.getNoteById(id);
    }

    // Get all notes
    @GetMapping("/notes")
    public List<Notes> getAllNotes() {
        return noteService.getAllNotes();
    }

    // Delete a note by ID
    @DeleteMapping("notes/{id}")
    public void deleteNoteById(@PathVariable Long id) throws NotesNotFoundException {
        noteService.deleteNoteById(id);
    }

    @PostMapping("/notes/search")
    public List<Notes> searchNotes(@RequestBody SearchCriteria searchCriteria) {
        return noteService.searchNotes(searchCriteria);
    }


}
