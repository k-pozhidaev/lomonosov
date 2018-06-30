package io.pozhidaev.lomonosov.task;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
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
                            httpHeaders.set(HttpHeaders.USER_AGENT, " Mozilla/5.0 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
                            httpHeaders.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                            httpHeaders.setAcceptCharset(List.of(Charset.forName("windows-1255")));
                            httpHeaders.setConnection("keep-alive");

                        }
                )
                .defaultCookies(stringStringMultiValueMap -> {
                    stringStringMultiValueMap.add("PHPSESSID", "e1ubc5osetcu2m29q88vpemaf7");
                })

                .build();
    }

    @Scheduled(fixedDelay = 5000)
    public void getPagesNum(){
//        log.info("getPagesNum");
    }

    //Nadlan/rentDetails.php?NadlanID=3501376&SubCatID=2

    @Bean
    CommandLineRunner c(){
        return args ->
            client.get()
                    .uri(b -> {
                        URI uri = b
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
                                .queryParam("Page", 1)
                                .queryParam("PriceOnly", 1)
                                .queryParam("ImgOnly", 1)
                                .build();
                        log.info(uri.toString());
                        return uri;
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
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> {
                        clientResponse
                                .headers()
                                .asHttpHeaders()
                                .entrySet()
                                .parallelStream()
                                .forEach(s -> {
                                    log.info(s.getKey());
                                    log.info(String.join(", ", s.getValue()));
                                });
                        log.error("Some server problem: {}",
                                clientResponse.statusCode().toString());
                        return Mono.empty();
                    })
                    .bodyToMono(String.class)
                    .map(this::parsePage)
                    .map(this::eachLog)
                    .subscribe();

    }


    private List<Element> parsePage(final String htmlPage){
//        log.info("First Parse, {}", htmlPage);
        var doc = Jsoup.parse(htmlPage);
        return doc.getElementsByAttributeValueContaining("id", "tr_Ad_");
    }

    private List<Element> eachLog(final List<Element> elements){
        log.info("Second parse, elements num {}", elements.size());
        elements.forEach(e -> {
            log.info(e.id());
//            log.info(e.toString());
        });
        return elements;
    }


}
