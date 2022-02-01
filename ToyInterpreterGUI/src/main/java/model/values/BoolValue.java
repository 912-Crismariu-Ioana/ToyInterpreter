package model.values;

import model.dataTypes.BoolType;
import model.dataTypes.IType;

import java.util.Objects;

public class BoolValue implements IValue {
    private final Boolean wrappedValue;
    public static final BoolValue TRUE = new BoolValue(true);
    public static final BoolValue FALSE = new BoolValue(false);

    private BoolValue(Boolean v) {

        wrappedValue = v;
    }

    public Boolean getWrappedValue() {

        return wrappedValue;
    }

    @Override
    public String toString() {

        return "" + wrappedValue;
    }

    public IType getType() {

        return BoolType.T;
    }

    public IValue getDefaultValue() {

        return BoolValue.FALSE;
    }

    public boolean equals(Object another) {
        if (another instanceof BoolValue)
            return Objects.equals(((BoolValue) another).wrappedValue, this.wrappedValue);
        return false;
    }
}
