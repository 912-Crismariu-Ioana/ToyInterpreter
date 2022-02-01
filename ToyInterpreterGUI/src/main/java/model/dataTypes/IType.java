package model.dataTypes;

import model.values.IValue;

public interface IType {
    boolean equals(Object another);

    IValue getDefaultValue();

    IType deep_copy();

    String toString();
}
