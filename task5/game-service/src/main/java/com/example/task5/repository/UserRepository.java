package com.example.task5.repository;

import com.example.task5.model.OAuthProvider;
import com.example.task5.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findByExternalIdAndProvider(String externalId, OAuthProvider provider);

}
