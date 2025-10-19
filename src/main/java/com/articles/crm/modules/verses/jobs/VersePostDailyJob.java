package com.articles.crm.modules.verses.jobs;

import com.articles.crm.modules.verses.useCases.AddVersePostUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VersePostDailyJob {

    private final AddVersePostUseCase addVersePostUseCase;

    @Scheduled(cron = "${jobs.verse-post.cron:0 0 6 * * *}", zone = "Europe/Kyiv")
    public void run() {
        try {
            addVersePostUseCase.handle();
            log.info("Verse post generated successfully.");
        } catch (Exception e) {
            log.error("Failed to generate verse post", e);
        }
    }
}