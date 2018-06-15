package io.jedaev.lomonosov.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class AYad2FileStorage extends FileStorage {

    public Path getRempFolder(){
        return checkAndCreateDirectory(Paths.get(this.getTempFolder().toString(), "yad2_tmp"));
    }
}
