package model.statements;

import model.ProgramState;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;


public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;

    // executes a statement of any kind
    IStatement deep_copy();

    String toString();

    IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception;
}
