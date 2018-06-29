package io.pozhidaev.lomonosov.exception;

public class DirectoryCreationFailedException extends RuntimeException {
    public DirectoryCreationFailedException() {
        super("File creation was failed");
    }
    public DirectoryCreationFailedException(Throwable throwable) {
        super("File creation was failed", throwable);
    }
}
