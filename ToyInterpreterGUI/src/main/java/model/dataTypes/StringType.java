package model.dataTypes;

import model.values.StringValue;
import model.values.IValue;

public class StringType implements IType {
    public static final StringType T = new StringType();

    private StringType() {
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public IType deep_copy() {
        return new StringType();
    }
}
