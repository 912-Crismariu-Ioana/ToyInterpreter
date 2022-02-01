package model.customExceptions;

import model.dataTypes.IType;

public class WrongType extends RuntimeException {
    public WrongType(String object, IType expected, IType found) {
        super(object + ": Expected type " + expected + " received " + found);
    }
}
