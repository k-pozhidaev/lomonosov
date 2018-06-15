package io.jedaev.lomonosov.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Yad2CrawlerScheduledTask {

    @Scheduled(fixedDelay = 5000)
    public void getPagesNum(){
        log.info("getPagesNum");
    }

}
