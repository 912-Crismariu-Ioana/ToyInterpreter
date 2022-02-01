package model.customExceptions;

public class VariableNameAlreadyDefined extends RuntimeException {
    public VariableNameAlreadyDefined(String name) {
        super("Variable " + name + " is already defined");
    }
}
