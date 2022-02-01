package model.statements;

import model.dataStructures.IDictionary;
import model.dataStructures.IStack;
import model.ProgramState;
import model.dataTypes.IType;

public class CompStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> stk = state.getExecutionStack();
        stk.push(this.second);
        stk.push(this.first);
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new CompStatement(this.first, this.second);
    }

    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }
}
