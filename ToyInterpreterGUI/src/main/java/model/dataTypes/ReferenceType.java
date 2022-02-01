package model.dataTypes;

import model.values.IValue;
import model.values.ReferenceValue;

public class ReferenceType implements IType {
    private final IType inner;

    public ReferenceType(IType inner) {
        this.inner = inner;
    }

    @Override
    public IValue getDefaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public IType deep_copy() {
        return new ReferenceType(inner);
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof ReferenceType)
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }
}
