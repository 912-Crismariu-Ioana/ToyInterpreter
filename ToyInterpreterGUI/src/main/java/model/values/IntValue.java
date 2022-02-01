package model.values;

import model.dataTypes.IType;
import model.dataTypes.IntType;

import java.util.Objects;

public class IntValue implements IValue {
    private final Integer wrappedValue;

    public IntValue(Integer v) {

        wrappedValue = v;
    }

    public Integer getWrappedValue() {

        return this.wrappedValue;
    }

    @Override
    public String toString() {

        return "" + wrappedValue;
    }

    public IType getType() {

        return IntType.T;
    }

    @Override
    public IValue getDefaultValue() {

        return new IntValue(0);
    }

    public boolean equals(Object another) {
        if (another instanceof IntValue)
            return Objects.equals(((IntValue) another).wrappedValue, this.wrappedValue);
        return false;
    }
}
