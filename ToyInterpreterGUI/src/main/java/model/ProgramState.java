package model;

import model.customExceptions.EmptyContainerException;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataStructures.IList;
import model.dataStructures.IStack;
import model.statements.IStatement;
import model.values.StringValue;
import model.values.IValue;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private final IStack<IStatement> exeStack;
    private final IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IDictionary<StringValue, BufferedReader> fileTable;
    private final IHeap<IValue> heap;
    private IStatement originalProgram;
    private static AtomicInteger global_id = new AtomicInteger(1);
    private final int id;

    public ProgramState(IStack<IStatement> stk, IDictionary<String, IValue> symtbl, IList<IValue> ot, IStatement prg, IDictionary<StringValue, BufferedReader> fTable, IHeap<IValue> heap) {
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        originalProgram = prg.deep_copy();
        fileTable = fTable;
        this.heap = heap;
        stk.push(prg);
        id = global_id.getAndIncrement();

    }

    public IStack<IStatement> getExecutionStack() {
        return exeStack;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IList<IValue> getOutput() {
        return out;
    }

    public void setOutput(IList<IValue> output) {
        this.out = output;
    }

    public void displayState() {
        synchronized (System.out) {
            System.out.println();
            System.out.println("--------------PROGRAM " + id + " STATE--------------");
            System.out.println(exeStack);
            System.out.println(symTable);
            System.out.println(out);
            System.out.println(heap);
            System.out.println();
        }

    }


    public IHeap<IValue> getHeap() {
        return heap;
    }

    public ProgramState oneStep() throws Exception {
        if (exeStack.isEmpty()) throw new EmptyContainerException("Execution stack is empty");
        IStatement currentStatement = exeStack.pop();
        ProgramState advance = currentStatement.execute(this);
        this.displayState();
        return advance;
    }

    public Boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }

    public int getId() {
        return id;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }
}
