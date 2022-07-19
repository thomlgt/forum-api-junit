package fr.semifir.forumapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Document
public class Discussion {

    @Id
    private String id;
    private String titre;
    private String auteur;
    private Date createdAt;
    private Date updatedAt;
    private List<Message> messages;
}
