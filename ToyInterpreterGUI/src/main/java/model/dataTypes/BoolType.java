package model.dataTypes;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {
    //In order to avoid creating too many BoolType objects unnecessarily, we will instead keep one as a static field
    // and access it when needed
    public static final BoolType T = new BoolType();

    private BoolType() {
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IValue getDefaultValue() {
        return BoolValue.FALSE;
    }

    @Override
    public IType deep_copy() {
        return new BoolType();
    }
}
