package model.statements;

import model.ProgramState;
import model.customExceptions.VariableNameAlreadyDefined;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.values.IValue;

public class VarDeclaration implements IStatement {
    private final String name;
    private final IType type;

    public VarDeclaration(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new VariableNameAlreadyDefined(name);
        symTable.add(name, type.getDefaultValue());
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new VarDeclaration(this.name, this.type);
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        typeEnv.add(name, type);
        return typeEnv;
    }
}
