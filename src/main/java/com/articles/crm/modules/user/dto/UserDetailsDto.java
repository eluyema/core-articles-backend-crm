package com.articles.crm.modules.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDetailsDto {
    private String username;
    private String email;
    private String roles;
}
