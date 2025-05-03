package com.articles.crm.modules.user.controllers;

import com.articles.crm.modules.auth.dtos.JwtAuthenticationResponse;
import com.articles.crm.modules.auth.dtos.SignInRequest;
import com.articles.crm.modules.auth.dtos.SignUpRequest;
import com.articles.crm.modules.auth.services.AuthenticationService;
import com.articles.crm.modules.user.dto.UserDetailsDto;
import com.articles.crm.modules.user.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/me")
    public ResponseEntity<String> checkAuth() throws JsonProcessingException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        User user = (User) authentication.getPrincipal();

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        UserDetailsDto details = new UserDetailsDto();

        details.setUsername(user.getUsername());
        details.setEmail(user.getEmail());
        details.setRoles(user.getRole().name());

        return ResponseEntity.ok(objectMapper.writeValueAsString(details));
    }
}