package com.articles.crm.modules.christianity.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateChristianityArticle {
    private String subcategory;
    private String slug;
    private Boolean active;
}
