package com.example.fileservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("file")
public class File {

    @Id
    private UUID id;
    private byte[] data;

}
