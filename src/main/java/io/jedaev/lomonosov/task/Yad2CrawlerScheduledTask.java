package io.jedaev.lomonosov.task;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.util.List;

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
                    .accept(MediaType.TEXT_HTML, MediaType.IMAGE_PNG, MediaType.APPLICATION_XML, MediaType.APPLICATION_XHTML_XML)
                    .acceptCharset(Charset.forName("windows-1255"))
                    .headers(httpHeaders -> httpHeaders.set("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1"))
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMapIterable(this::parsePage)
                    .map(this::eachLog)
                    .subscribe();
//            AreaID:
//            City:
//            HomeTypeID:
//            fromRooms:
//            untilRooms: 5.5
//            fromPrice:
//            untilPrice:
//            PriceType: 1
//            FromFloor:
//            ToFloor:
//            EnterDate:
//            Info:
//            Page: 55
        };
    }


    private List<Element> parsePage(final String htmlPage){
        var doc = Jsoup.parse(htmlPage);
        return doc.getElementsByAttributeValueContaining("id", "tr_Ad_");
    }

    private Element eachLog(final Element element){
        log.info(element.attr("id"));
        log.info(element.toString());
        return element;
    }

}
