package com.articles.crm.modules.verses.services;

import com.articles.crm.modules.verses.entities.Verse;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VerseRepository extends JpaRepository<Verse, Long> {
    @Query("""
           select v
           from Verse v
           join v.noticed n
           where n.versePost is null
           order by function('random')
           """)
    List<Verse> findRandomWhereNoticedVersePostIsNull(Pageable pageable);

    default Optional<Verse> findRandomWhereNoticedVersePostIsNull() {
        return findRandomWhereNoticedVersePostIsNull(PageRequest.of(0, 1))
                .stream()
                .findFirst();
    }
}
