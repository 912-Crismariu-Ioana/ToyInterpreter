package model.customExceptions;

public class FileNotOpened extends RuntimeException {
    public FileNotOpened(String fName) {
        super(fName + " has not yet been opened by the current program");
    }
}
