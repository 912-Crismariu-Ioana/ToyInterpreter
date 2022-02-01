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
import model.values.IntValue;

public class RelationalExpression implements IExpression {
    private final IExpression e1;
    private final IExpression e2;
    private final String operator;

    public RelationalExpression(IExpression exp1, IExpression exp2, String operator) {
        this.e1 = exp1;
        this.e2 = exp2;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        IValue v1, v2;
        v1 = e1.evaluate(symtable, heap);
        if (!v1.getType().equals(IntType.T))
            throw new WrongType("Operand 1", IntType.T, v1.getType());
        v2 = e2.evaluate(symtable, heap);
        if (!v2.getType().equals(IntType.T))
            throw new WrongType("Operand 2", IntType.T, v1.getType());
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int lhs = i1.getWrappedValue();
        int rhs = i2.getWrappedValue();
        switch (operator) {
            case ("<"):
                if (lhs < rhs)
                    return BoolValue.TRUE;
                else
                    return BoolValue.FALSE;
            case ("<="):
                if (lhs <= rhs)
                    return BoolValue.TRUE;
                else
                    return BoolValue.FALSE;
            case ("=="):
                if (lhs == rhs)
                    return BoolValue.TRUE;
                else
                    return BoolValue.FALSE;
            case (">"):
                if (lhs > rhs)
                    return BoolValue.TRUE;
                else
                    return BoolValue.FALSE;
            case (">="):
                if (lhs >= rhs)
                    return BoolValue.TRUE;
                else
                    return BoolValue.FALSE;
        }
        return v1;
    }

    @Override
    public IExpression deep_copy() {
        return new RelationalExpression(e1, e2, operator);
    }

    @Override
    public String toString() {
        return "" + e1.toString() + " " + operator + " " + e2.toString();
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType type1;
        IType type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (!type1.equals(IntType.T))
            throw new TypeCheckError("The first operand is not an integer");
        if (!type2.equals(IntType.T))
            throw new TypeCheckError("The second operand is not an integer");
        return BoolType.T;
    }

}
