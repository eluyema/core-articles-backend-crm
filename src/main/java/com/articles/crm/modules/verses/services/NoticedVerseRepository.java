package com.articles.crm.modules.verses.services;

import com.articles.crm.modules.verses.entities.NoticedVerse;
import com.articles.crm.modules.verses.entities.Verse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticedVerseRepository extends JpaRepository<NoticedVerse, Long> {

}
