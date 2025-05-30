package com.articles.crm.modules.christianity.dbProjections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DbChristianArticlePath {
    private String language;
    private String slug;
    private String category;
    private String subcategory;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
