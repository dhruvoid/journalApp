package com.example.Journal.Entry;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "User")
@Data
public class User {
    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;

    private List<String> roles;

    private LocalDateTime date;

    private LocalDateTime updateDate;
}
