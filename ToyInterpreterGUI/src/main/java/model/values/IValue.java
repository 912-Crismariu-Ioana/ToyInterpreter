package model.values;

import model.dataTypes.IType;

public interface IValue {
    IType getType();

    IValue getDefaultValue();

    String toString();

    boolean equals(Object another);
}
