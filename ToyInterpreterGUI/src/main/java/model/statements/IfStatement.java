package model.statements;

import model.ProgramState;
import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IStack;
import model.dataTypes.BoolType;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement {
    private final IExpression cond;
    private final IStatement thenS;
    private final IStatement elseS;

    public IfStatement(IExpression cond, IStatement thenS, IStatement elseS) {
        this.cond = cond;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IValue expValue = cond.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(BoolType.T))
            throw new WrongType("Condition", BoolType.T, expValue.getType());
        if (expValue.equals(BoolValue.TRUE))
            exeStack.push(thenS);
        else
            exeStack.push(elseS);
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new IfStatement(this.cond, this.thenS, this.elseS);
    }

    @Override
    public String toString() {
        return "(IF(" + cond.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + "))";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = cond.typeCheck(typeEnv);
        if (!typeExp.equals(BoolType.T))
            throw new TypeCheckError("Condition of if statement is not of boolean type");
        thenS.typeCheck(typeEnv.deep_copy());
        elseS.typeCheck(typeEnv.deep_copy());
        return typeEnv;
    }
}
