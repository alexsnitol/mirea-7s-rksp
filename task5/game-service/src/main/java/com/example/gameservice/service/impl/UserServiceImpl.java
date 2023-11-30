package com.example.gameservice.service.impl;

import com.example.gameservice.model.OAuthProvider;
import com.example.gameservice.model.User;
import com.example.gameservice.model.UserRole;
import com.example.gameservice.repository.UserRepository;
import com.example.gameservice.service.UserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public Mono<OAuth2User> loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return Mono.defer(() -> {
            String externalId = userRequest.getClientRegistration().getClientId();

            OAuthProvider provider;
            ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
            if (providerDetails.getAuthorizationUri().contains("github")) {
                provider = OAuthProvider.GITHUB;
            } else if (providerDetails.getAuthorizationUri().contains("google")) {
                provider = OAuthProvider.GOOGLE;
            } else {
                provider = OAuthProvider.UNKNOWN;
            }

            return userRepository.findByExternalIdAndProvider(externalId, provider)
                    .switchIfEmpty(Mono.defer(() -> {
                        User user = new User();

                        user.setExternalId(externalId);
                        user.setRole(UserRole.USER);
                        user.setProvider(provider);

                        return userRepository.save(user);
                    }));
        });
    }

    @Override
    @Transactional
    public Mono<User> setWalletPrivateKey(User user, String cryptoWalletPrivateKey) {
        return userRepository.findById(user.getId())
                .flatMap(u -> {
                    u.setCryptoWalletPrivateKey(cryptoWalletPrivateKey);
                    return userRepository.save(u);
                });
    }

}
