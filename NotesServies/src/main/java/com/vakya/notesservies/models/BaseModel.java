package com.vakya.notesservies.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;
    private boolean isDeleted;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();  // set createdAt to the current date and time
        lastUpdatedAt = new Date();  // initialize lastUpdatedAt when created
    }

    // This method will be called before the entity is updated in the database
    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = new Date();  // update lastUpdatedAt on update
    }
}

