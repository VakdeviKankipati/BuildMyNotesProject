package com.vakya.notesservies.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "search")
@Getter
@Setter
public class SearchCriteria extends BaseModel {
    private String Title;
    private String content;
    private String tag;

}
