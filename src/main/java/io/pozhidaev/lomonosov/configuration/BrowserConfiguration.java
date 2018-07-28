package io.pozhidaev.lomonosov.configuration;

import lombok.Getter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "lomonosov")
public class BrowserConfiguration{

    @Setter
    @Getter
    private String chromeBinPath;

    @PostConstruct
    private void init(){
        log.trace(chromeBinPath);
    }

}