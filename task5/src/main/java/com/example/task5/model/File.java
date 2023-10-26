package com.example.task5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("file")
public class File {

    @Id
    private UUID id;
    @JsonIgnore
    private byte[] data;

}
