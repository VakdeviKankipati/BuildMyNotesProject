package com.vakya.notesservies.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notes extends BaseModel{


    private String title;

    @Column(length = 10000)
    private String content;

    @Column(length = 1000)
    private String category;

    @ElementCollection
    private List<String> tags;

    ;
}
