package io.jedaev.lomonosov.task;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class Yad2CrawlerScheduledTask {
    private String url = "http://www.yad2.co.il/Nadlan/rent.php?AreaID=&City=&HomeTypeID=1&fromRooms=4&untilRooms=5.5&fromPrice=&untilPrice=&PriceType=1&FromFloor=&ToFloor=&EnterDate=&Info=";

    @Scheduled(fixedDelay = 5000)
    public void getPagesNum(){
//        log.info("getPagesNum");
    }

//    "/Nadlan/rent.php?AreaID=&City=&HomeTypeID=1&fromRooms=4&untilRooms=5.5&fromPrice=&untilPrice=&PriceType=1&FromFloor=&ToFloor=&EnterDate=&Info="
    @Bean
    CommandLineRunner c(){
        return args -> {
            log.info("Started --------------------");
            var client = WebClient.create("http://www.yad2.co.il");
            client.get()
                    .uri("/Nadlan/rent.php?AreaID=&City=&HomeTypeID=1&fromRooms=4&untilRooms=5.5&fromPrice=&untilPrice=&PriceType=1&FromFloor=&ToFloor=&EnterDate=&Info=")
                    .accept(MediaType.TEXT_HTML)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(this::parsePage)
                    .doOnError(e -> log.error("Something goes wrong", e))
                    .subscribe();
        };
    }

    private void parsePage(final String htmlPage){
        log.info(htmlPage);
        var doc = Jsoup.parse(htmlPage);
        var el = doc.getElementsByAttributeValueContaining("id", "tr_Ad_");
        log.info("Elements count {}",  el.size());
    }

}
