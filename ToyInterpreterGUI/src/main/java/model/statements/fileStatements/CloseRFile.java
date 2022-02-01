package model.statements.fileStatements;

import model.ProgramState;
import model.customExceptions.*;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.dataTypes.StringType;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement {
    private final IExpression fileName;

    public CloseRFile(IExpression fileName) {
        this.fileName = fileName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        IValue expValue = fileName.evaluate(state.getSymTable(), state.getHeap());
        if (!expValue.getType().equals(StringType.T))
            throw new WrongType("Filename", StringType.T, expValue.getType());
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        //This is a safe cast, we know that expValue is an instance of StringValue
        StringValue strVal = (StringValue) expValue;
        String fName = strVal.getWrappedValue();
        if (!fileTable.isDefined(strVal))
            throw new FileNotOpened(fName);
        BufferedReader br = fileTable.lookup(strVal);
        if (br == null)
            throw new ReaderError(fName);
        try {
            br.close();
        } catch (IOException ioe) {
            throw new FileIOException(fName + " could not be closed");
        }
        fileTable.remove(strVal);
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new CloseRFile(fileName);
    }

    @Override
    public String toString() {
        return "closeRFile(" + fileName + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = fileName.typeCheck(typeEnv);
        if (!typeExp.equals(StringType.T))
            throw new TypeCheckError("Closing file: Provided filename is not of type string");
        return typeEnv;
    }
}
