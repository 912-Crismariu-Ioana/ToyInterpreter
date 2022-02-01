package model.customExceptions;

public class DivisionByZero extends RuntimeException {
    public DivisionByZero() {
        super("You attempted to divide by 0");
    }

}
