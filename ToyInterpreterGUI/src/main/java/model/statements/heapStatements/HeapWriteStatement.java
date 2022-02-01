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

public class HeapWriteStatement implements IStatement {
    private final String var_name;
    private final IExpression exp;

    public HeapWriteStatement(String var_name, IExpression exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IHeap<IValue> heap = state.getHeap();
        IDictionary<String, IValue> symtable = state.getSymTable();
        IValue mappedVal = symtable.lookup(var_name);
        if (mappedVal == null)
            throw new VariableNameNotFound(var_name);
        if (!(mappedVal.getType() instanceof ReferenceType))
            throw new InvalidReference(var_name);
        int address = ((ReferenceValue) mappedVal).getAddress();
        if (!heap.isDefined(address))
            throw new InvalidReference(var_name);
        IType inner = ((ReferenceType) mappedVal.getType()).getInner();
        IValue exp_value = exp.evaluate(symtable, heap);
        if (!exp_value.getType().equals(inner))
            throw new WrongType(exp_value.toString(), inner, exp_value.getType());
        heap.update(address, exp_value);
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new HeapWriteStatement(var_name, exp);
    }

    @Override
    public String toString() {
        return "wH(" + var_name + "," + exp.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(var_name);
        IType typeExp = exp.typeCheck(typeEnv);
        if (!(typeVar instanceof ReferenceType))
            throw new TypeCheckError(var_name + " is not of type reference");
        if (!((ReferenceType) typeVar).getInner().equals(typeExp))
            throw new TypeCheckError("Heap Write: type mismatch");
        return typeEnv;
    }
}
