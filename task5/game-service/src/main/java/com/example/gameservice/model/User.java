package com.example.gameservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

import static java.util.Objects.nonNull;

@Data
@Table("\"user\"")
public class User implements OAuth2User {

    @Id
    private UUID id;
    private String externalId;
    private String login;
    private String password;
    private OAuthProvider provider;
    private UserRole role;
    private String cryptoWalletPrivateKey;


    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();

        if (nonNull(id)) {
            attributes.put("id", id);
        } else if (nonNull(login)) {
            attributes.put("login", login);
        } else if (nonNull(provider)) {
            attributes.put("provider", provider);
        } else if (nonNull(role)) {
            attributes.put("role", role);
        }

        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getName() {
        return id.toString();
    }

}
