package com.SynCore.Syncore.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Document(collection = "syncore_entries")
public class SynCoreEntry {
    @Id
    private ObjectId                 id;
    @NonNull
    private String                title;
    private String          description;
    private LocalDate              date;
}