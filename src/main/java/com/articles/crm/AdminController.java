package com.articles.crm;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String getAdminPage() {
        return "Welcome to the Admin Page";
    }
}