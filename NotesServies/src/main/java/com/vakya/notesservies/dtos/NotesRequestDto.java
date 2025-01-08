package com.vakya.notesservies.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotesRequestDto {
    private String title;
    private String content;
    private String category;
    private List<String> tags;
}
