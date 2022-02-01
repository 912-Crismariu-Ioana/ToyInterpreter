package model.statements.multithreadingStatements;

import model.ProgramState;
import model.dataStructures.IDictionary;
import model.dataStructures.IStack;
import model.dataStructures.MyStack;
import model.dataTypes.IType;
import model.statements.IStatement;

public class ForkStatement implements IStatement {
    private final IStatement split_point;

    public ForkStatement(IStatement split_point) {
        this.split_point = split_point;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IStack<IStatement> new_stack = new MyStack<>();
        return new ProgramState(new_stack,
                state.getSymTable().deep_copy(),
                state.getOutput(),
                split_point,
                state.getFileTable(),
                state.getHeap());
    }

    @Override
    public IStatement deep_copy() {
        return new ForkStatement(split_point);
    }


    @Override
    public String toString() {
        return "fork(" + split_point.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        split_point.typeCheck(typeEnv.deep_copy());
        return typeEnv;
    }
}
