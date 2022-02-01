package model.expressions;

import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.BoolType;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicalExpression implements IExpression {
    private final IExpression e1;
    private final IExpression e2;
    private final char op;

    public LogicalExpression(IExpression e1, IExpression e2, char op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        IValue v1, v2;
        v1 = e1.evaluate(symtable, heap);
        if (!v1.getType().equals(BoolType.T))
            throw new WrongType("Operand 1", BoolType.T, v1.getType());
        v2 = e2.evaluate(symtable, heap);
        if (!v2.getType().equals(BoolType.T))
            throw new WrongType("Operand 2", BoolType.T, v1.getType());
        BoolValue i1 = (BoolValue) v1;
        BoolValue i2 = (BoolValue) v2;
        boolean n1, n2;
        n1 = i1.getWrappedValue();
        n2 = i2.getWrappedValue();
        switch (op) {
            case ('&'):
                boolean and = n1 && n2;
                if (and)
                    return BoolValue.TRUE;
                return BoolValue.FALSE;
            case ('|'):
                boolean or = n1 || n2;
                if (or)
                    return BoolValue.TRUE;
                return BoolValue.FALSE;
        }
        return v1;
    }

    @Override
    public IExpression deep_copy() {
        return new LogicalExpression(this.e1, this.e2, this.op);
    }

    @Override
    public String toString() {
        return "" + e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType type1;
        IType type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (!type1.equals(BoolType.T))
            throw new TypeCheckError("The first operand is not a boolean");
        if (!type2.equals(BoolType.T))
            throw new TypeCheckError("The second operand is not a boolean");
        return BoolType.T;
    }
}
