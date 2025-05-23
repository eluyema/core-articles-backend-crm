package com.articles.crm.modules.christianity.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChristianArticlePath {
    private String language;
    private String slug;
    private String category;
    private String subcategory;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
}
