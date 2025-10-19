package com.articles.crm.modules.verses.services;

import com.articles.crm.modules.verses.entities.VersePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VersePostRepository extends JpaRepository<VersePost, Long> {
    @Query("""
           select distinct p
           from VersePost p
           join fetch p.translations t
           where p.slug = :slug and t.language = :lang
           """)
    Optional<VersePost> findBySlugAndLangFetchOneTranslation(@Param("slug") String slug,
                                                             @Param("lang") String lang);
}
