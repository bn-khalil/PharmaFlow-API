package com.pharmaflow.demo.Services.Impl;

import com.pharmaflow.demo.Entities.User;
import com.pharmaflow.demo.Enums.Role;
import com.pharmaflow.demo.Repositories.UserRepository;
import com.pharmaflow.demo.Security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        User user = userRepository.findUserByEmail(oAuth2User.getAttribute("email")).orElseGet( () -> {
            User newUser = new User();
                newUser.setFirstName(oAuth2User.getAttribute("given_name"));
                newUser.setLastName(oAuth2User.getAttribute("family_name"));
                newUser.setEmail(oAuth2User.getAttribute("email"));
                newUser.setActive(true);
                newUser.setRole(Role.PHARMACIST);
                newUser.setPassword(null);
                newUser.setPasswordChanged(true);
                return this.userRepository.save(newUser);
        });
        // remove user caches before login again
        Objects.requireNonNull(cacheManager.getCache("user_auth")).evict(user.getId());
        return new UserSecurity(user, oAuth2User.getAttributes());
    }
}
