package model.expressions;

import model.customExceptions.VariableNameNotFound;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    public final String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        IValue mappedValue = symtable.lookup(id);
        if (mappedValue == null)
            throw new VariableNameNotFound(id);
        return mappedValue;
    }

    @Override
    public IExpression deep_copy() {
        return new VariableExpression(this.id);
    }

    @Override
    public String toString() {
        return "" + id;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        return typeEnv.lookup(id);
    }
}
