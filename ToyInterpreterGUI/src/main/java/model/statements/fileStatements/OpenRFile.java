package model.statements.fileStatements;

import model.ProgramState;
import model.customExceptions.*;
import model.dataStructures.IDictionary;
import model.dataTypes.IType;
import model.dataTypes.StringType;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.values.StringValue;
import model.values.IValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStatement {
    private final IExpression fileName;

    public OpenRFile(IExpression fileName) {
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
        if (fileTable.isDefined(strVal))
            throw new FileAlreadyOpened(fName);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fName));
            fileTable.add(strVal, br);
        } catch (FileNotFoundException fe) {
            throw new FileNotFound(fName);
        }
        return null;
    }

    @Override
    public IStatement deep_copy() {
        return new OpenRFile(fileName);
    }

    @Override
    public String toString() {
        return "openRFile(" + fileName.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType typeExp = fileName.typeCheck(typeEnv);
        if (!typeExp.equals(StringType.T))
            throw new TypeCheckError("Opening file: Provided filename is not of type string");
        return typeEnv;
    }
}
