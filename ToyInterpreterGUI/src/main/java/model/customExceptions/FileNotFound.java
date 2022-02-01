package model.customExceptions;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String fName) {
        super("File " + fName + " cannot be opened, as it does not exist");
    }
}
