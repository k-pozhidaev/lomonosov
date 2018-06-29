package io.pozhidaev.lomonosov.task;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@Service
public class Yad2CrawlerScheduledTask {

    private WebClient client;

    private Yad2CrawlerScheduledTask() {

        this.client = WebClient
                .builder()
                .baseUrl("http://www.yad2.co.il")
                .defaultHeaders(
                        httpHeaders -> {
                            httpHeaders.set(HttpHeaders.HOST, "www.yad2.co.il");
                            httpHeaders.set(HttpHeaders.PRAGMA, "no-cache");
                            httpHeaders.set(HttpHeaders.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1");
                            httpHeaders.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                            httpHeaders.setAcceptCharset(Charset.forName("windows-1255");
                        }
                )
                .build();
    }

    @Scheduled(fixedDelay = 5000)
    public void getPagesNum(){
//        log.info("getPagesNum");
    }

    @Bean
    CommandLineRunner c(){
        return args -> {
            log.info("Started --------------------");

            client.get()
                    .uri(b -> {
                        URI build = b
                                .path("Nadlan/rent.php")
                                .queryParam("AreaID", "")
                                .queryParam("City", "")
                                .queryParam("HomeTypeID", "")
                                .queryParam("fromRooms", 3.5)
                                .queryParam("untilRooms", 5.5)
                                .queryParam("fromPrice", "")
                                .queryParam("untilPrice", "")
                                .queryParam("PriceType", 1)
                                .queryParam("FromFloor", "")
                                .queryParam("ToFloor", "")
                                .queryParam("EnterDate", "")
                                .queryParam("Info", "")
//                                .queryParam("Page", 2)
                                .build();
                        log.info(build.toString());
                        return build;
                    })
                    .accept(MediaType.TEXT_HTML, MediaType.IMAGE_PNG, MediaType.APPLICATION_XML, MediaType.APPLICATION_XHTML_XML)
                    .retrieve()
                    .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> {
                            log.error("Some client problem: {}",
                                    clientResponse.statusCode().toString());
                            return Mono.empty();
                        })
                    .onStatus(
                        HttpStatus::is5xxServerError,
                        clientResponse -> {
                            log.error("Some server problem: {}",
                                    clientResponse.statusCode().toString());
                            return Mono.empty();
                        })
                    .bodyToMono(String.class)
                    .map(this::parsePage)
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
        log.info("First Parse");
        log.info(htmlPage);
        var doc = Jsoup.parse(htmlPage);
        return doc.getElementsByAttributeValueContaining("id", "tr_Ad_");
    }

    private List<Element> eachLog(final List<Element> elements){
        log.info("Second parse, num {}", elements.size());

        elements.forEach(e -> {
            log.info(e.id());
            log.info(e.toString());
        });
        return elements;
    }

    private Element eachLog(final Element element){
        log.info(element.attr("id"));
        log.info(element.toString());
        return element;
    }


}
