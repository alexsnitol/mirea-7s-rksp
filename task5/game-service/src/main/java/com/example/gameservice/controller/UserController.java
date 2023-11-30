package com.example.gameservice.controller;

import com.example.gameservice.model.User;
import com.example.gameservice.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping("/game-service/api/users")
@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/current")
    public Mono<User> getCurrentUser(
            @Parameter(hidden = true) @AuthenticationPrincipal User user
    ) {
        return Mono.just(user);
    }

    @PutMapping("/current/crypto-wallet-private-key")
    public Mono<User> setCryptoWalletPrivateKey(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @RequestParam String cryptoWalletPrivateKey
    ) {
        return userService.setWalletPrivateKey(user, cryptoWalletPrivateKey);
    }


}
