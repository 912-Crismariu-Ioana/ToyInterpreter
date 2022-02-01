package model.customExceptions;

public class FileIOException extends RuntimeException {
    public FileIOException(String fName) {
        super("An error occurred while trying to operate on " + fName);
    }
}
