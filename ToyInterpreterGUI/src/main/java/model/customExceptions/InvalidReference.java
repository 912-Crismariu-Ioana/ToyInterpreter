package model.customExceptions;

import model.dataTypes.IType;

public class InvalidReference extends RuntimeException {
    public InvalidReference(String object) {
        super(object + " is not a valid reference");
    }
}
