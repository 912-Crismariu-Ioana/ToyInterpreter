package model.statements.heapStatements;

import model.ProgramState;
import model.customExceptions.InvalidReference;
import model.customExceptions.TypeCheckError;
import model.customExceptions.VariableNameNotFound;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.dataTypes.ReferenceType;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.values.IValue;
import model.values.ReferenceValue;

public class AllocateStatement implements IStatement {
    private final String var_name;
    private final IExpression exp;

    public AllocateStatement(String var_name, IExpression exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symtable = state.getSymTable();
        IHeap<IValue> heap = state.getHeap();
        if (!symtable.isDefined(var_name))
            throw new VariableNameNotFound(var_name);
        IValue val = symtable.lookup(var_name);
        if (!(val.getType() instanceof ReferenceType))
            throw new InvalidReference(var_name);
        IType inner = ((ReferenceType) val.getType()).getInner();
        IValue exp_value = exp.evaluate(symtable, heap);
        if (!exp_value.getType().equals(inner))
            throw new WrongType(exp_value.toString(), inner, exp_value.getType());
        int address = heap.allocate(exp_value);
        symtable.update(var_name, new ReferenceValue(address, inner));
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new AllocateStatement(var_name, exp);
    }

    @Override
    public String toString() {
        return "new(" + var_name + "," + exp.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(var_name);
        IType typeExp = exp.typeCheck(typeEnv);
        if (!(typeVar instanceof ReferenceType))
            throw new TypeCheckError(var_name + " is not of type reference");
        if (!((ReferenceType) typeVar).getInner().equals(typeExp))
            throw new TypeCheckError("Heap Allocation: type mismatch");
        return typeEnv;
    }
}
