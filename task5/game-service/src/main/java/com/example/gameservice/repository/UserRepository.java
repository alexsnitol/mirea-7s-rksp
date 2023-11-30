package com.example.gameservice.repository;

import com.example.gameservice.model.OAuthProvider;
import com.example.gameservice.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findByExternalIdAndProvider(String externalId, OAuthProvider provider);

}
