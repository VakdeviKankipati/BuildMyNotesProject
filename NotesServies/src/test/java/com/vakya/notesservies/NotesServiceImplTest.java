package com.vakya.notesservies;

import com.vakya.notesservies.exceptions.NotesNotFoundException;
import com.vakya.notesservies.models.Notes;
import com.vakya.notesservies.models.SearchCriteria;
import com.vakya.notesservies.repositories.NoteRepository;
import com.vakya.notesservies.services.NotesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class NotesServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NotesServiceImpl notesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNote_Success() {
        Notes note = new Notes();
        note.setTitle("Test Title");
        note.setContent("Test Content");
        note.setCategory("Test Category");
        note.setTags(Arrays.asList("tag1", "tag2"));

        when(noteRepository.save(any(Notes.class))).thenReturn(note);

        Notes createdNote = notesService.createNote(
                "Test Title",
                "Test Content",
                "Test Category",
                Arrays.asList("tag1", "tag2")
        );

        assertNotNull(createdNote, "Created note should not be null");
        assertEquals("Test Title", createdNote.getTitle(), "Title should match");
        assertEquals("Test Content", createdNote.getContent(), "Content should match");
        verify(noteRepository, times(1)).save(any(Notes.class));
    }



    @Test
    public void testUpdateNote_NoteNotFound() {
        Long noteId = 1L;

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            notesService.updateNote(noteId, new Notes());
        });

        assertEquals("Note not found", exception.getMessage(), "Exception message should match");
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    public void testGetNoteById_Success() throws NotesNotFoundException {
        Long noteId = 1L;
        Notes note = new Notes();
        note.setId(noteId);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        Notes foundNote = notesService.getNoteById(noteId);

        assertNotNull(foundNote, "Note should not be null");
        assertEquals(noteId, foundNote.getId(), "Note ID should match");
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    public void testGetNoteById_NoteNotFound() {
        Long noteId = 1L;

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotesNotFoundException.class, () -> {
            notesService.getNoteById(noteId);
        });

        assertEquals("Note not found with id: 1", exception.getMessage(), "Exception message should match");
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    public void testGetAllNotes() {
        Notes note1 = new Notes();
        Notes note2 = new Notes();

        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        List<Notes> notes = notesService.getAllNotes();

        assertNotNull(notes, "Notes list should not be null");
        assertEquals(2, notes.size(), "Notes list size should match");
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteNoteById_Success() throws NotesNotFoundException {
        Long noteId = 1L;
        Notes note = new Notes();
        note.setId(noteId);

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).delete(note);

        notesService.deleteNoteById(noteId);

        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).delete(note);
    }

    @Test
    public void testDeleteNoteById_NoteNotFound() {
        Long noteId = 1L;

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotesNotFoundException.class, () -> {
            notesService.deleteNoteById(noteId);
        });

        assertEquals("Note not found", exception.getMessage(), "Exception message should match");
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    public void testSearchNotes_Success() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setTitle("Title");
        searchCriteria.setContent("Content");
        searchCriteria.setTag("Tag");

        Notes note = new Notes();

        when(noteRepository.findByTitleContainingOrContentContainingOrTagsContaining(
                "Title", "Content", "Tag"))
                .thenReturn(Arrays.asList(note));

        List<Notes> notes = notesService.searchNotes(searchCriteria);

        assertNotNull(notes, "Notes list should not be null");
        assertEquals(1, notes.size(), "Notes list size should match");
        verify(noteRepository, times(1)).findByTitleContainingOrContentContainingOrTagsContaining(
                "Title", "Content", "Tag");
    }
}

