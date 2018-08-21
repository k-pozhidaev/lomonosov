package io.pozhidaev.lomonosov.task;

import io.pozhidaev.lomonosov.domain.RentUrlQuery;
import io.pozhidaev.lomonosov.service.Yad2;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class Yad2CrawlerScheduledTask {

    private WebClient client;

    private Yad2CrawlerScheduledTask(@Autowired final Yad2 yad2) {
        this.client = yad2.createClient();
    }

    @Scheduled(fixedDelay = 5000)
    public void getPagesNum1() {
        client.get()
                .uri(this::buildUri)
                .accept(MediaType.TEXT_HTML, MediaType.IMAGE_PNG, MediaType.APPLICATION_XML, MediaType.APPLICATION_XHTML_XML)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse -> {
                            log.error("Some problem: {}",
                                    clientResponse.statusCode().toString());
                            return Mono.empty();
                        })
                .onStatus(HttpStatus::is3xxRedirection, clientResponse -> {
                    clientResponse
                            .headers()
                            .asHttpHeaders()
                            .forEach((key, value) -> {
                                log.trace(key);
                                log.trace(String.join(", ", value));
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


    private List<Element> parsePage(final String htmlPage) {
        var doc = Jsoup.parse(htmlPage);
        return doc.getElementsByAttributeValueContaining("id", "tr_Ad_");
    }

    private List<Element> eachLog(final List<Element> elements) {
        log.trace("Second parse, elements num {}", elements.size());
        elements.forEach(e -> {
            log.trace(e.id());
//            log.info(e.toString());
        });
        return elements;
    }

    private URI buildUri(final UriBuilder uriBuilder) {
        final URI uri = RentUrlQuery.builder()
                .fromRooms(3.5F)
                .untilRooms(5.5F)
                .priceOnly(1)
                .priceType(1)
                .imgOnly(1)
                .build()
                .generateUrl(uriBuilder);
        log.trace(uri.toString());
        return uri;
    }


}
