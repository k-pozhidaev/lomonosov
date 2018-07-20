package io.pozhidaev.lomonosov.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.Charset;
import java.util.List;

@Service
public class Yad2 {

    public WebClient createClient(){
        return WebClient
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
//                .defaultCookies(stringStringMultiValueMap -> {
//                    stringStringMultiValueMap.add("PHPSESSID", "e1ubc5osetcu2m29q88vpemaf7");
//                })

                .build();
    }
}
