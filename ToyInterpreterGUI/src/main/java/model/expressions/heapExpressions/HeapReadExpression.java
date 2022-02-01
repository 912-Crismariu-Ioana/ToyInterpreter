package model.expressions.heapExpressions;

import model.customExceptions.InvalidReference;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.dataTypes.ReferenceType;
import model.expressions.IExpression;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapReadExpression implements IExpression {
    private final IExpression exp;

    public HeapReadExpression(IExpression exp) {
        this.exp = exp;
    }


    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        IValue exp_val = exp.evaluate(symtable, heap);
        if (!(exp_val instanceof ReferenceValue))
            throw new InvalidReference(exp_val.toString());
        IValue mappedValue = heap.lookup(((ReferenceValue) exp_val).getAddress());
        if (mappedValue == null)
            //TODO: maybe implement a new kind of exception for this situation
            throw new InvalidReference(exp_val.toString());
        return mappedValue;
    }

    @Override
    public IExpression deep_copy() {
        return new HeapReadExpression(exp);
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typ = exp.typeCheck(typeEnv);
        if (!(typ instanceof ReferenceType))
            throw new InvalidReference(exp.toString());
        ReferenceType reft = (ReferenceType) typ;
        return reft.getInner();
    }
}
