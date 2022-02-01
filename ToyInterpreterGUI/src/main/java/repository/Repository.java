package repository;

import model.ProgramState;
import model.customExceptions.FileIOException;
import model.dataStructures.*;
import model.statements.CompStatement;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> myProgramStates;
    private final String logFilePath;

    public Repository(String logFile) {
        this.logFilePath = logFile;
        myProgramStates = new ArrayList<>();
    }

    @Override
    public void addProgram(ProgramState newProgram) {
        myProgramStates.add(newProgram);
    }

    @Override
    public List<ProgramState> getProgramList() {
        return myProgramStates;
    }

    @Override
    public void setProgramList(List<ProgramState> new_progs) {
        myProgramStates = new_progs;
    }

    @Override
    public void logProgramState(ProgramState prog) throws Exception {
        PrintWriter logFile = null;
        IStack<IStatement> exeStack = prog.getExecutionStack().deep_copy();
        IDictionary<String, IValue> symTable = prog.getSymTable();
        IList<IValue> out = prog.getOutput();
        IDictionary<StringValue, BufferedReader> fileTable = prog.getFileTable();
        IHeap<IValue> heap = prog.getHeap();
        synchronized (logFilePath) {
            try {
                logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
                logFile.println();
                logFile.println("----------------START STATE PROGRAM " + prog.getId() + " ----------------");
                logFile.println("Execution Stack:");
                if (!exeStack.isEmpty()) {
                    while (!exeStack.isEmpty()) {
                        IStatement statement = exeStack.pop();
                        if (statement instanceof CompStatement) {
                            exeStack.push(((CompStatement) statement).getSecond());
                            exeStack.push(((CompStatement) statement).getFirst());
                            continue;
                        }
                        logFile.println(statement.toString());
                    }
                }
                logFile.println("Symbol Table:");
                for (String key : symTable.keys()) {
                    logFile.println(key + " -> " + symTable.lookup(key).toString());
                }
                logFile.println("Output:");
                for (IValue val : out) {
                    logFile.println(val.toString());
                }
                logFile.println("File Table:");
                for (StringValue key : fileTable.keys()) {
                    logFile.println(key.toString());
                }
                logFile.println("Heap:");
                for (int key : heap.keys()) {
                    logFile.println(key + " -> " + heap.lookup(key).toString());
                }
                logFile.println("----------------END STATE PROGRAM " + prog.getId() + "----------------");
                logFile.println();
            } catch (IOException ioe) {
                throw new FileIOException(logFilePath + " : Error occurred while operating on file");
            } finally {
                if (logFile != null) {
                    logFile.close();
                }
            }
        }
    }
}
