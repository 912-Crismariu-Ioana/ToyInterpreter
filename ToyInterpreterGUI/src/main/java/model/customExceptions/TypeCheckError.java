package model.customExceptions;

import model.dataTypes.IType;

public class TypeCheckError extends RuntimeException {
    public TypeCheckError(String object) {
        super(object);
    }
}
