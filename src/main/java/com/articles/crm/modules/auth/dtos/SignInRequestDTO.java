package com.articles.crm.modules.auth.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SignInRequestDTO {
    private String username;
    private String password;
}