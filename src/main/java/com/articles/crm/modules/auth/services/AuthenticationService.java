package com.articles.crm.modules.auth.services;

import com.articles.crm.modules.auth.dtos.JwtAuthenticationResponse;
import com.articles.crm.modules.auth.dtos.SignInRequest;
import com.articles.crm.modules.auth.dtos.SignUpRequest;
import com.articles.crm.modules.user.entities.Role;
import com.articles.crm.modules.user.entities.User;
import com.articles.crm.modules.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public JwtAuthenticationResponse signUp(SignUpRequest request) {



        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            logger.info(                    request.getUsername() + " " + request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));

            var user = userService
                    .userDetailsService()
                    .loadUserByUsername(request.getUsername());
            logger.info(                   user.getUsername() + " " + user.getPassword() + " "+ user);

            var jwt = jwtService.generateToken(user);
            return new JwtAuthenticationResponse(jwt);
        } catch (BadCredentialsException e) {
            logger.info("Error: " + e.getMessage());
            throw new RuntimeException("Invalid username or password.");
        }
    }




}
