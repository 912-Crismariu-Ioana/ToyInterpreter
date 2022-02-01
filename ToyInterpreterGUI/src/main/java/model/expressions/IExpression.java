package model.expressions;

import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception;

    IExpression deep_copy();

    String toString();

    IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception;

}
