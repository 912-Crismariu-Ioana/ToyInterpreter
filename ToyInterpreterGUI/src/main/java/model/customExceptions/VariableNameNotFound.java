package model.customExceptions;

public class VariableNameNotFound extends RuntimeException {
    public VariableNameNotFound(String identifier) {
        super("Variable name " + identifier + " not found in symbol table.");
    }
}
