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

public class WhileStatement implements IStatement {
    private final IExpression control_cond;
    private final IStatement action;

    public WhileStatement(IExpression control_cond, IStatement action) {
        this.control_cond = control_cond;
        this.action = action;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> exeStack = state.getExecutionStack();
        IValue expValue = control_cond.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(BoolType.T))
            throw new WrongType("Condition", BoolType.T, expValue.getType());
        if (expValue.equals(BoolValue.TRUE)) {
            IStatement copy = new WhileStatement(control_cond, action);
            exeStack.push(copy);
            exeStack.push(this.action);
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new WhileStatement(control_cond, action);
    }

    public String toString() {
        return "(WHILE(" + control_cond.toString() + ") " + action.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = control_cond.typeCheck(typeEnv);
        if (!typeExp.equals(BoolType.T))
            throw new TypeCheckError("Condition of while statement is not of type boolean");
        action.typeCheck(typeEnv.deep_copy());
        return typeEnv;
    }
}
