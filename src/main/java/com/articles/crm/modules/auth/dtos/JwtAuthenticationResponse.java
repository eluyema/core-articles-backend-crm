package com.articles.crm.modules.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class JwtAuthenticationResponse {
    private String token;
}