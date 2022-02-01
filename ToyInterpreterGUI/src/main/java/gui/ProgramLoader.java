package gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.ProgramState;
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

public class ProgramLoader {
    @FXML
    private ListView<IStatement> programs;

    @FXML
    private Button runProgram;


    public void initialize() {
        ObservableList<IStatement> programList = getPrograms();
        programs.setItems(programList);
        programs.getSelectionModel().selectFirst();
        runProgram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage executionStage = new Stage();
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if(type == ProgramRunner.class){
                        return new ProgramRunner(initializeProgram(programs.getSelectionModel().getSelectedItem()));
                    }
                    else{
                        try{
                            return type.getDeclaredConstructor().newInstance();
                        }
                        catch (Exception e){
                            System.err.println("Could not create controller for "+type.getName());
                            throw new RuntimeException(e);
                        }
                    }
                };
                try{
                    FXMLLoader interpreterLoader = new FXMLLoader(gui.InterpreterGUI.class.getResource("program-runner.fxml"));
                    interpreterLoader.setControllerFactory(controllerFactory);
                    HBox root = (HBox) interpreterLoader.load();
                    Scene mainScene = new Scene(root, 1000, 500);
                    executionStage.setTitle("Toy Interpreter");
                    executionStage.setScene(mainScene);
                    executionStage.show();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private ObservableList<IStatement> getPrograms(){

        IStatement ex1 = new CompStatement(new VarDeclaration("v", IntType.T),
                new CompStatement(new Assignment("v",
                        new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));

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

        IStatement ex3 = new CompStatement(new VarDeclaration("a", BoolType.T),
                new CompStatement(new VarDeclaration("v", IntType.T),
                        new CompStatement(new Assignment("a", new ValueExpression(BoolValue.TRUE)),
                                new CompStatement(new IfStatement(new VariableExpression("a"),
                                        new Assignment("v", new ValueExpression(new IntValue(2))),
                                        new Assignment("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));

        IStatement ex4 = new CompStatement(new VarDeclaration("varf", StringType.T),
                new CompStatement(new Assignment("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStatement(new OpenRFile(new VariableExpression("varf")),
                                new CompStatement(new VarDeclaration("varc", IntType.T),
                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))))))))));

        IStatement ex5 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclaration("a", new ReferenceType(new ReferenceType(IntType.T))),
                                new CompStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));

        IStatement ex6 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclaration("a", new ReferenceType(new ReferenceType(IntType.T))),
                                new CompStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5)), '+')))))));

        IStatement ex7 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompStatement(new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new HeapReadExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5)), '+'))))));

        IStatement ex8 = new CompStatement(new VarDeclaration("v", new ReferenceType(IntType.T)),
                new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclaration("a", new ReferenceType(new ReferenceType(IntType.T))),
                                new CompStatement(new AllocateStatement("a", new VariableExpression("v")),
                                        new CompStatement(new AllocateStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))))))));

        IStatement ex9 = new CompStatement(new VarDeclaration("v", IntType.T),
                new CompStatement(new Assignment("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"),
                                new ValueExpression(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VariableExpression("v")),
                                        new Assignment("v", new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), '-')))), new PrintStatement(new VariableExpression("v")))));

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

        return FXCollections.observableArrayList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10);
    }

    private Controller initializeProgram(IStatement program){
        IStack<IStatement> stack = new MyStack<>();
        IDictionary<String, IValue> dic = new MyDictionary<>();
        IList<IValue> out = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        IHeap<IValue> heap = new MyHeap<>();
        IDictionary<String, IType> typeEnv = new MyDictionary<>();
        try {
            program.typeCheck(typeEnv);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ProgramState state = new ProgramState(stack, dic, out, program, fileTable, heap);
        try {
            PrintWriter pw = new PrintWriter("log"+ (programs.getSelectionModel().getSelectedIndex()+1) + ".txt");
            pw.close();
        } catch (IOException ignored) {
        }
        IRepository repo = new Repository("log" + (programs.getSelectionModel().getSelectedIndex()+1) + ".txt");
        repo.addProgram(state);
        return new Controller(repo);
    }
}
