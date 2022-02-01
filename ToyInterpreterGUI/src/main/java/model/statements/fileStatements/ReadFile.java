package model.statements.fileStatements;

import model.ProgramState;
import model.customExceptions.*;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.dataTypes.StringType;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class ReadFile implements IStatement {
    private final IExpression fileName;
    private final String varName;

    public ReadFile(IExpression fileName, String varName) {
        this.fileName = fileName;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new VariableNameNotFound(varName);
        IValue correspVal = symTable.lookup(varName);
        if (!correspVal.getType().equals(IntType.T))
            throw new WrongType("Variable value", IntType.T, correspVal.getType());
        IValue expValue = fileName.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(StringType.T))
            throw new WrongType("Filename", StringType.T, expValue.getType());
        //We know it is of type StringType, thus we can safely perform the casting
        StringValue strVal = (StringValue) expValue;
        String fName = strVal.getWrappedValue();
        if (!state.getFileTable().isDefined(strVal))
            throw new FileNotOpened(fName);
        BufferedReader br = state.getFileTable().lookup(strVal);
        if (br == null)
            throw new ReaderError(fName);
        try {
            String line = br.readLine();
            IntValue assoc_val = new IntValue(0);
            if (line != null)
                assoc_val = new IntValue(Integer.parseInt(line));
            symTable.update(varName, assoc_val);
        } catch (IOException ioe) {
            throw new FileIOException(fName + " : An error occurred during reading");
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new ReadFile(fileName, varName);
    }

    @Override
    public String toString() {
        return "readFile(" + fileName.toString() + "," + varName + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeVar = typeEnv.lookup(varName);
        IType typeExp = fileName.typeCheck(typeEnv);
        if (!typeVar.equals(IntType.T))
            throw new TypeCheckError("Reading file: Provided variable name does not correspond to an integer");
        if (!typeExp.equals(StringType.T))
            throw new TypeCheckError("Reading file: Provided filename is not of type string");
        return typeEnv;
    }
}
