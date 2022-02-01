package model.statements;

import model.ProgramState;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "No operation";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        return typeEnv;
    }
}
