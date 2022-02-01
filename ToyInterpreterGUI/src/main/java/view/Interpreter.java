package view;

import controller.Controller;
import model.ProgramState;
import model.commands.ExitCommand;
import model.commands.RunExample;
import model.dataStructures.*;
import model.dataTypes.*;
import model.expressions.ArithmeticExpression;
import model.expressions.RelationalExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.expressions.heapExpressions.HeapReadExpression;
import model.statements.*;
import model.statements.fileStatements.CloseRFile;
import model.statements.fileStatements.OpenRFile;
import model.statements.fileStatements.ReadFile;
import model.statements.heapStatements.AllocateStatement;
import model.statements.heapStatements.HeapWriteStatement;
import model.statements.multithreadingStatements.ForkStatement;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Interpreter {
    public static void main(String[] args) {

        IStack<IStatement> stack1 = new MyStack<>();
        IDictionary<String, IValue> dic1 = new MyDictionary<>();
        IList<IValue> out1 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        IHeap<IValue> heap1 = new MyHeap<>();
        IStatement ex1 = new CompStatement(new VarDeclaration("v", IntType.T),
                new CompStatement(new Assignment("v",
                        new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        IDictionary<String, IType> typeEnv1 = new MyDictionary<>();
        try {
            ex1.typeCheck(typeEnv1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state1 = new ProgramState(stack1, dic1, out1, ex1, fileTable1, heap1);
        try {
            PrintWriter pw1 = new PrintWriter("log1.txt");
            pw1.close();
        } catch (IOException ignored) {
        }
        IRepository repo1 = new Repository("log1.txt");
        repo1.addProgram(state1);
        Controller cont1 = new Controller(repo1);


        IStack<IStatement> stack2 = new MyStack<>();
        IDictionary<String, IValue> dic2 = new MyDictionary<>();
        IList<IValue> out2 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        IHeap<IValue> heap2 = new MyHeap<>();
        IStatement ex2 = new CompStatement(new VarDeclaration("a", IntType.T),
                new CompStatement(new VarDeclaration("b", IntType.T),
                        new CompStatement(new Assignment("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                                new ValueExpression(new IntValue(5)), '*'), '+')),
                                new CompStatement(new Assignment("b",
                                        new ArithmeticExpression(new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)), '+')),
                                        new PrintStatement(new VariableExpression("b"))))));
        IDictionary<String, IType> typeEnv2 = new MyDictionary<>();
        try {
            ex2.typeCheck(typeEnv2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state2 = new ProgramState(stack2, dic2, out2, ex2, fileTable2, heap2);
        try {
            PrintWriter pw2 = new PrintWriter("log2.txt");
            pw2.close();
        } catch (IOException ignored) {
        }
        IRepository repo2 = new Repository("log2.txt");
        repo2.addProgram(state2);
        Controller cont2 = new Controller(repo2);


        IStack<IStatement> stack3 = new MyStack<>();
        IDictionary<String, IValue> dic3 = new MyDictionary<>();
        IList<IValue> out3 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        IHeap<IValue> heap3 = new MyHeap<>();
        IStatement ex3 = new CompStatement(new VarDeclaration("a", BoolType.T),
                new CompStatement(new VarDeclaration("v", IntType.T),
                        new CompStatement(new Assignment("a", new ValueExpression(BoolValue.TRUE)),
                                new CompStatement(new IfStatement(new VariableExpression("a"),
                                        new Assignment("v", new ValueExpression(new IntValue(2))),
                                        new Assignment("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
        IDictionary<String, IType> typeEnv3 = new MyDictionary<>();
        try {
            ex3.typeCheck(typeEnv3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state3 = new ProgramState(stack3, dic3, out3, ex3, fileTable3, heap3);
        try {
            PrintWriter pw3 = new PrintWriter("log3.txt");
            pw3.close();
        } catch (IOException ignored) {
        }
        IRepository repo3 = new Repository("log3.txt");
        repo3.addProgram(state3);
        Controller cont3 = new Controller(repo3);


        IStack<IStatement> stack4 = new MyStack<>();
        IDictionary<String, IValue> dic4 = new MyDictionary<>();
        IList<IValue> out4 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        IHeap<IValue> heap4 = new MyHeap<>();
        IStatement ex4 = new CompStatement(new VarDeclaration("varf", StringType.T),
                new CompStatement(new Assignment("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompStatement(new VarDeclaration("varc", IntType.T),
                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));
        IDictionary<String, IType> typeEnv4 = new MyDictionary<>();
        try {
            ex4.typeCheck(typeEnv4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state4 = new ProgramState(stack4, dic4, out4, ex4, fileTable4, heap4);
        try {
            PrintWriter pw4 = new PrintWriter("log4.txt");
            pw4.close();
        } catch (IOException ignored) {
        }
        IRepository repo4 = new Repository("log4.txt");
        repo4.addProgram(state4);
        Controller cont4 = new Controller(repo4);


        IStack<IStatement> stack5 = new MyStack<>();
        IDictionary<String, IValue> dic5 = new MyDictionary<>();
        IList<IValue> out5 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable5 = new MyDictionary<>();
        IHeap<IValue> heap5 = new MyHeap<>();
        IStatement ex5 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclaration("a", new ReferenceType(new ReferenceType(IntType.T))),
                                new CompStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
        IDictionary<String, IType> typeEnv5 = new MyDictionary<>();
        try {
            ex5.typeCheck(typeEnv5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state5 = new ProgramState(stack5, dic5, out5, ex5, fileTable5, heap5);
        try {
            PrintWriter pw5 = new PrintWriter("log5.txt");
            pw5.close();
        } catch (IOException ignored) {
        }
        IRepository repo5 = new Repository("log5.txt");
        repo5.addProgram(state5);
        Controller cont5 = new Controller(repo5);


        IStack<IStatement> stack6 = new MyStack<>();
        IDictionary<String, IValue> dic6 = new MyDictionary<>();
        IList<IValue> out6 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>();
        IHeap<IValue> heap6 = new MyHeap<>();
        IStatement ex6 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclaration("a", new ReferenceType(new ReferenceType(IntType.T))),
                                new CompStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)), '+')))))));
        IDictionary<String, IType> typeEnv6 = new MyDictionary<>();
        try {
            ex6.typeCheck(typeEnv6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state6 = new ProgramState(stack6, dic6, out6, ex6, fileTable6, heap6);
        try {
            PrintWriter pw6 = new PrintWriter("log6.txt");
            pw6.close();
        } catch (IOException ignored) {
        }
        IRepository repo6 = new Repository("log6.txt");
        repo6.addProgram(state6);
        Controller cont6 = new Controller(repo6);


        IStack<IStatement> stack7 = new MyStack<>();
        IDictionary<String, IValue> dic7 = new MyDictionary<>();
        IList<IValue> out7 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable7 = new MyDictionary<>();
        IHeap<IValue> heap7 = new MyHeap<>();
        IStatement ex7 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompStatement(new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5)), '+'))))));
        ProgramState state7 = new ProgramState(stack7, dic7, out7, ex7, fileTable7, heap7);
        IDictionary<String, IType> typeEnv7 = new MyDictionary<>();
        try {
            ex7.typeCheck(typeEnv7);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            PrintWriter pw7 = new PrintWriter("log7.txt");
            pw7.close();
        } catch (IOException ignored) {
        }
        IRepository repo7 = new Repository("log7.txt");
        repo7.addProgram(state7);
        Controller cont7 = new Controller(repo7);


        IStack<IStatement> stack8 = new MyStack<>();
        IDictionary<String, IValue> dic8 = new MyDictionary<>();
        IList<IValue> out8 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable8 = new MyDictionary<>();
        IHeap<IValue> heap8 = new MyHeap<>();
        IStatement ex8 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclaration("a", new ReferenceType(new ReferenceType(IntType.T))),
                                new CompStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))))));
        IDictionary<String, IType> typeEnv8 = new MyDictionary<>();
        try {
            ex8.typeCheck(typeEnv8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state8 = new ProgramState(stack8, dic8, out8, ex8, fileTable8, heap8);
        try {
            PrintWriter pw8 = new PrintWriter("log8.txt");
            pw8.close();
        } catch (IOException ignored) {
        }
        IRepository repo8 = new Repository("log8.txt");
        repo8.addProgram(state8);
        Controller cont8 = new Controller(repo8);


        IStack<IStatement> stack9 = new MyStack<>();
        IDictionary<String, IValue> dic9 = new MyDictionary<>();
        IList<IValue> out9 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable9 = new MyDictionary<>();
        IHeap<IValue> heap9 = new MyHeap<>();
        IStatement ex9 = new CompStatement(new VarDeclaration("v", IntType.T),
                new CompStatement(new Assignment("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                                new ValueExpression(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new Assignment("v", new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), '-')))), new PrintStatement(new VariableExpression("v")))));
        IDictionary<String, IType> typeEnv9 = new MyDictionary<>();
        try {
            ex9.typeCheck(typeEnv9);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state9 = new ProgramState(stack9, dic9, out9, ex9, fileTable9, heap9);
        try {
            PrintWriter pw9 = new PrintWriter("log9.txt");
            pw9.close();
        } catch (IOException ignored) {
        }
        IRepository repo9 = new Repository("log9.txt");
        repo9.addProgram(state9);
        Controller cont9 = new Controller(repo9);


        IStack<IStatement> stack10 = new MyStack<>();
        IDictionary<String, IValue> dic10 = new MyDictionary<>();
        IList<IValue> out10 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable10 = new MyDictionary<>();
        IHeap<IValue> heap10 = new MyHeap<>();
        IStatement ex10 = new CompStatement(new VarDeclaration("v", IntType.T), new CompStatement(
                new VarDeclaration("a", new ReferenceType(IntType.T)), new CompStatement(new Assignment("v",
                new ValueExpression(new IntValue(10))), new CompStatement(new AllocateStatement(
                "a", new ValueExpression(new IntValue(22))), new CompStatement(new ForkStatement(
                new CompStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                        new CompStatement(new Assignment("v", new ValueExpression(new IntValue(32))),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))),
                new CompStatement(new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))))))));
        IDictionary<String, IType> typeEnv10 = new MyDictionary<>();
        try {
            ex10.typeCheck(typeEnv10);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state10 = new ProgramState(stack10, dic10, out10, ex10, fileTable10, heap10);
        try {
            PrintWriter pw10 = new PrintWriter("log10.txt");
            pw10.close();
        } catch (IOException ignored) {
        }
        IRepository repo10 = new Repository("log10.txt");
        repo10.addProgram(state10);
        Controller cont10 = new Controller(repo10);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), cont1));
        menu.addCommand(new RunExample("2", ex2.toString(), cont2));
        menu.addCommand(new RunExample("3", ex3.toString(), cont3));
        menu.addCommand(new RunExample("4", ex4.toString(), cont4));
        menu.addCommand(new RunExample("5", ex5.toString(), cont5));
        menu.addCommand(new RunExample("6", ex6.toString(), cont6));
        menu.addCommand(new RunExample("7", ex7.toString(), cont7));
        menu.addCommand(new RunExample("8", ex8.toString(), cont8));
        menu.addCommand(new RunExample("9", ex9.toString(), cont9));
        menu.addCommand(new RunExample("10", ex10.toString(), cont10));
        menu.show();
    }
}
