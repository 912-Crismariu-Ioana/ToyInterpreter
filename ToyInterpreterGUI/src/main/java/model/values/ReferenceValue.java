package model.values;

import model.dataTypes.IType;
import model.dataTypes.ReferenceType;

public class ReferenceValue implements IValue {
    private final int address;
    private final IType locationType;

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public IValue getDefaultValue() {
        return new ReferenceValue(0, locationType);
    }

    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "(" + this.address + "," + this.locationType.toString() + ")";
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IValue)
            return (this.address == ((ReferenceValue) another).getAddress())
                    && (this.getType().equals(((ReferenceValue) another).getType()));
        return false;
    }
}
