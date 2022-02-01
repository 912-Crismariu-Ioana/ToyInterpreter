package model.dataTypes;

import model.values.IntValue;
import model.values.IValue;

public class IntType implements IType {
    //In order to avoid creating too many IntType objects unnecessarily, we will instead keep one as a static field
    // and access it when needed
    public static final IntType T = new IntType();

    private IntType() {
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public IType deep_copy() {
        return new IntType();
    }
}
