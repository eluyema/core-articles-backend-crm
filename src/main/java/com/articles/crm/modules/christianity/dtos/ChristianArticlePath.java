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
    private String category;
    private String subcategory;
    private String slug;
    private String language;
}
