package io.pozhidaev.lomonosov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class Yad2FileStorage extends FileStorage {

    @Bean
    public Path yad2Path(){
        return checkAndCreateDirectory(Paths.get(this.getTempFolder().toString(), "yad2_tmp")).toAbsolutePath();
    }
}
