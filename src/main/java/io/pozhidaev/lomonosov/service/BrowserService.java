package io.pozhidaev.lomonosov.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@Service
@Getter
@Setter
@ConfigurationProperties(prefix = "lomonosov")
public class BrowserService {
    //todo  /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --headless --disable-gpu --dump-dom http://www.yad2.co.il/Nadlan/rent.php 1>> a.t 2>> b.t

    private String chromeBinPath;

    private Path yad2Path;
    private static final String outputName = "out.html";
    private static final String errorName = "e.html";

    @Autowired
    public void setYad2FileStorage(final Path yad2Path) {
        this.yad2Path = yad2Path;
    }

    public void createChromeProcess(){
        log.trace("-----------------------------");
        final String url = "http://www.yad2.co.il/Nadlan/rent.php";
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(chromeBinPath, "--headless", "--disable-gpu", "--dump-dom", url);
        processBuilder.redirectError(Paths.get(yad2Path.toString(), errorName).toFile());
        processBuilder.redirectOutput(Paths.get(yad2Path.toString(), outputName).toFile());
        try {
            processBuilder.start();
        } catch (IOException e) {
            log.error("It's fucked", e);
        }

    }

    @Bean
    CommandLineRunner c (){
        return args -> createChromeProcess();
    }

    public void loadPage(){

    }




}
