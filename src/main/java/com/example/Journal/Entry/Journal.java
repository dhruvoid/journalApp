package com.example.Journal.Entry;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
public class Journal {
    @Id
    private ObjectId id;
    
    @Indexed
    @DBRef
    private User user;

    private String title;

    private String context;

    private LocalDateTime date;

    private LocalDateTime updateDate;
}
