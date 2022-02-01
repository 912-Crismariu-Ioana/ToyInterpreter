package model.values;

import model.dataTypes.IType;
import model.dataTypes.StringType;

import java.util.Objects;

public class StringValue implements IValue {
    private final String wrappedValue;

    public StringValue(String value) {
        this.wrappedValue = value;
    }

    public String getWrappedValue() {
        return this.wrappedValue;
    }

    @Override
    public String toString() {
        return this.wrappedValue;
    }

    @Override
    public IType getType() {
        return StringType.T;
    }

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }

    public boolean equals(Object another) {
        if (another instanceof StringValue)
            return Objects.equals(((StringValue) another).wrappedValue, this.wrappedValue);
        return false;
    }
}
