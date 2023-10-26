package com.example.task5.repository;

import com.example.task5.model.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface GameRepository extends ReactiveCrudRepository<Game, UUID> {

}
