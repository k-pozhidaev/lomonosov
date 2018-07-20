package io.pozhidaev.lomonosov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuples;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class Yad2SessionHolder {
    private final String SESSION_FIELD_NAME = "PHPSESSID";
    private final AtomicReference<String> session = new AtomicReference<>();
    private final AtomicBoolean isSessionExpiered = new AtomicBoolean(true);

    private final Yad2 yad2;

    public Yad2SessionHolder(@Autowired final Yad2 yad2) {
        this.yad2 = yad2;
    }

    private void getSession(){
        yad2.createClient()
            .get()
            .uri("/")
            .accept(MediaType.TEXT_HTML, MediaType.IMAGE_PNG, MediaType.APPLICATION_XML, MediaType.APPLICATION_XHTML_XML)
            .exchange()
            .map(clientResponse ->
                    Tuples.of(
                            clientResponse.headers().header(SESSION_FIELD_NAME),
                            clientResponse.headers().header("X-Accel-Expires"),
                            clientResponse.headers().header("spcsrf"),
                            clientResponse.headers().header("UTGv2")

                    )
            ).subscribe()
            ;
    }

}
