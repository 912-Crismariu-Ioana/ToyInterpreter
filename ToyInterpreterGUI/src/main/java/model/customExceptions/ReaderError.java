package model.customExceptions;

public class ReaderError extends RuntimeException {
    public ReaderError(String fName) {
        super("Reader for " + fName + " is null");
    }
}
