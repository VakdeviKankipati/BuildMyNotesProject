package com.vakya.notesservies.repositories;

import com.vakya.notesservies.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Notes, Long> {

    List<Notes> findByTitleContainingOrContentContainingOrTagsContaining(String title, String content, String tag);
    //List<Notes> findByUserId(Long userId);
}
