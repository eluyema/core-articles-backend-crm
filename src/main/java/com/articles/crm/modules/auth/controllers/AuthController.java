package com.articles.crm.modules.auth.controllers;

import com.articles.crm.modules.auth.dtos.JwtAuthenticationResponse;
import com.articles.crm.modules.auth.dtos.SignInRequest;
import com.articles.crm.modules.auth.dtos.SignInRequestDTO;
import com.articles.crm.modules.auth.dtos.SignUpRequest;
import com.articles.crm.modules.auth.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody SignInRequestDTO request) {
        // Manually binding username and password
        return authenticationService.signIn(request);
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAuth() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.ok("Authenticated as: " + authentication.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }
    }
}