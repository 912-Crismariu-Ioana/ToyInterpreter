package model.statements;

import model.ProgramState;
import model.dataStructures.IDictionary;
import model.dataStructures.IList;
import model.dataTypes.IType;
import model.expressions.IExpression;
import model.values.IValue;

public class PrintStatement implements IStatement {
    private final IExpression printExp;

    public PrintStatement(IExpression printExp) {
        this.printExp = printExp;
    }

    @Override
    public String toString() {
        return "print(" + printExp.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        printExp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IList<IValue> output = state.getOutput();
        output.add(printExp.evaluate(state.getSymTable(), state.getHeap()));
        state.setOutput(output);
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new PrintStatement(this.printExp);
    }
}
