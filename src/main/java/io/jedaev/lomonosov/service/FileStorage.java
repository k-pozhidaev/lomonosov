package io.jedaev.lomonosov.service;

import io.jedaev.lomonosov.exception.DirectoryCreationFailedException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
abstract class FileStorage {

    private Path getRootFolder(){
        return Paths.get(".").toAbsolutePath();
    }

    Path getTempFolder() {
        return Paths.get(getRootFolder().toString(), "tmp");
    }

    Path checkAndCreateDirectory(final Path directory) {
        if (!Files.exists(directory)){
            try {
                return Files.createDirectories(directory);
            } catch (IOException e) {
                log.error("Directory creating was failed");
                throw new DirectoryCreationFailedException(e);
            }
        }
        return directory;
    }

}
