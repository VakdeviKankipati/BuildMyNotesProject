package com.vakya.notesservies.services;

import com.vakya.notesservies.exceptions.NotesNotFoundException;
import com.vakya.notesservies.models.Notes;
import com.vakya.notesservies.models.SearchCriteria;
import com.vakya.notesservies.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService{

    private  NoteRepository notesRepository;
    private RestTemplate restTemplate;

    public NotesServiceImpl(NoteRepository notesRepository, RestTemplate restTemplate) {
        this.notesRepository = notesRepository;
        this.restTemplate=restTemplate;
    }


    @Override
    public Notes createNote(String title, String content, String category, List<String> tags) {
        Notes note = new Notes();
        note.setTitle(title);
        note.setContent(content);
        note.setCategory(category);
        note.setTags(tags);

        return notesRepository.save(note);
    }

    @Override
    public Notes updateNote(Long noteId, Notes notes) {
        Notes note = notesRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Update note details
        note.setTitle(note.getTitle());
        note.setContent(note.getContent());
        note.setCategory(note.getCategory());
        note.setTags(note.getTags());

        return notesRepository.save(note);
    }

    @Override
    public Notes getNoteById(Long id) throws NotesNotFoundException {
        return notesRepository.findById(id)
                .orElseThrow(() -> new NotesNotFoundException("Note not found with id: " + id));
    }

    @Override
    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    @Override
    public void deleteNoteById(Long id) throws NotesNotFoundException {
        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new NotesNotFoundException("Note not found"));

        notesRepository.delete(note);
    }

    @Override
    public List<Notes> searchNotes(SearchCriteria searchCriteria) {
        return notesRepository.findByTitleContainingOrContentContainingOrTagsContaining(
                searchCriteria.getTitle(),
                searchCriteria.getContent(),
                searchCriteria.getTag()
        );
    }
}
