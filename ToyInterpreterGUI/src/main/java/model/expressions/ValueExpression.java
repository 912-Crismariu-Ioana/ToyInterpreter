package model.expressions;

import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    public final IValue v;

    public ValueExpression(IValue v) {
        this.v = v;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        return v;
    }

    @Override
    public IExpression deep_copy() {
        return new ValueExpression(this.v);
    }

    @Override
    public String toString() {
        return "" + v.toString();
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        return v.getType();
    }
}
