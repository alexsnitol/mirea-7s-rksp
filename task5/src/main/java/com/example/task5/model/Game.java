package com.example.task5.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("game")
public class Game {

    @Id
    private UUID id;
    private String title;
    private UUID coverFileId;

}
