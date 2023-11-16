package com.example.task5.service;

import com.example.task5.model.User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveOAuth2UserService<OAuth2UserRequest, OAuth2User> {

    Mono<User> setWalletPrivateKey(User user, String cryptoWalletPrivateKey);

}
