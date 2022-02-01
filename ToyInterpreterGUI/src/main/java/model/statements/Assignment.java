package model.statements;

import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.ProgramState;
import model.values.IValue;

public class Assignment implements IStatement {
    private final String identifier;
    private final IExpression variableValue;

    public Assignment(String identifier, IExpression variableValue) {
        this.identifier = identifier;
        this.variableValue = variableValue;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        if (!symbolTable.isDefined(identifier))
            throw new VariableNameNotFound(identifier);
        IValue lhs = variableValue.evaluate(symbolTable, state.getHeap());
        if (!lhs.getType().equals(symbolTable.lookup(identifier).getType()))
            throw new WrongType("Identifier", lhs.getType(), symbolTable.lookup(identifier).getType());
        symbolTable.update(identifier, lhs);
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new Assignment(this.identifier, this.variableValue);
    }

    @Override
    public String toString() {
        return identifier + " = " + variableValue.toString();
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(identifier);
        IType typeExp = variableValue.typeCheck(typeEnv);
        if (!typeVar.equals(typeExp))
            throw new TypeCheckError("Assignment: right hand side and left hand side have different types");
        return typeEnv;
    }
}
