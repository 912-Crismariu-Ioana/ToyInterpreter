package gui;

import controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ProgramState;
import model.dataStructures.IStack;
import model.observer.Observer;
import model.statements.CompStatement;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProgramRunner implements Observer {

    private Controller cont;

    @FXML
    private Button runOneStep;

    @FXML
    private TextField nrOfPrograms;

    @FXML
    private TableView<Map.Entry<String, IValue>> symTable;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symbol;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> varValue;

    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> address;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> refValue;

    @FXML
    private TableView<Map.Entry<Integer, StringValue>> fileTable;

    @FXML
    private TableColumn<Map.Entry<Integer, StringValue>, String> fileName;

    @FXML
    private TableColumn<Map.Entry<Integer, StringValue>, String> fileIdentifier;

    @FXML
    private ListView<IValue> output;

    @FXML
    private ListView<IStatement> exeStack;

    @FXML
    private ListView<Integer> programList;

    ProgramRunner(Controller cont){
        this.cont = cont;
        cont.addObserver(this);
    }

    public void initialize(){
        firstRun();
        programList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSymbolTableAndExecutionStack();
            }
        });
        runOneStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cont.getRepository().getProgramList().size() > 0){
                    try {
                        cont.allStepGUI();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Current program finished");
                    alert.setHeaderText(null);
                    alert.setContentText("Program finished successfully");
                    Button confirm = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) programList.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }

    private void firstRun(){
        setNumberTextField();
        setHeapTable();
        setOutput();
        setFileTable();
        setProgramList();
        programList.getSelectionModel().selectFirst();
        setSymbolTableAndExecutionStack();
    }

    public void update(){
        setNumberTextField();
        setHeapTable();
        setOutput();
        setFileTable();
        setProgramList();
        if(programList.getSelectionModel().getSelectedItem() == null) {
            programList.getSelectionModel().selectFirst();
        }
        setSymbolTableAndExecutionStack();
    }

    private void setSymbolTableAndExecutionStack() {
        ObservableList<Map.Entry<String, IValue>> symbolTableList = FXCollections.observableArrayList();
        ObservableList<IStatement> exeStackList = FXCollections.observableArrayList();
        List<ProgramState> allPrograms = cont.getRepository().getProgramList();
        ProgramState result = null;
        for(ProgramState st: allPrograms){
            if(st.getId() == programList.getSelectionModel().getSelectedItem()){
                result = st;
                break;
            }
        }
        if (result != null){
            symbol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey() + ""));
            varValue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString() + ""));
            symbolTableList.addAll(result.getSymTable().getContent().entrySet());
            IStack<IStatement> exeStack = result.getExecutionStack().deep_copy();
            while (!exeStack.isEmpty()) {
                try {
                    IStatement statement = exeStack.pop();
                    if (statement instanceof CompStatement) {
                        exeStack.push(((CompStatement) statement).getSecond());
                        exeStack.push(((CompStatement) statement).getFirst());
                        continue;
                    }
                    exeStackList.add(statement);
                }catch (Exception ece){
                    ece.printStackTrace();
                }
            }
            symTable.setItems(symbolTableList);
            symTable.refresh();
            this.exeStack.setItems(exeStackList);
            this.exeStack.refresh();
        }
    }

    private void setProgramList() {
        ObservableList<Integer> programIDList = FXCollections.observableArrayList();
        for(ProgramState st : cont.getRepository().getProgramList()) {
            programIDList.add(st.getId());
        }
        programList.setItems(programIDList);
        this.programList.refresh();
    }

    private void setFileTable() {
        ObservableList<Map.Entry<Integer, StringValue>> fileTableList = FXCollections.observableArrayList();
        List<StringValue> fileNames = new ArrayList<>(cont.getRepository().getProgramList().get(0).getFileTable().getContent().keySet());
        Map<Integer, StringValue> map = IntStream.range(0, fileNames.size()).boxed()
                        .collect(Collectors.toMap(i -> i + 1, fileNames::get));
        fileName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));
        fileIdentifier.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getKey())));
        fileTableList.addAll(map.entrySet());
        fileTable.setItems(fileTableList);
        this.fileTable.refresh();
    }

    private void setOutput() {
        ObservableList<IValue> outObservables = FXCollections.observableArrayList();
        outObservables.addAll(cont.getRepository().getProgramList().get(0).getOutput().getContent());
        output.setItems(outObservables);
        this.output.refresh();
    }

    private void setHeapTable() {
        ObservableList<Map.Entry<Integer, IValue>> heapTableList = FXCollections.observableArrayList();
        refValue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));
        address.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getKey())));
        heapTableList.addAll(cont.getRepository().getProgramList().get(0).getHeap().getContent().entrySet());
        heapTable.setItems(heapTableList);
        this.heapTable.refresh();
    }

    private void setNumberTextField() {
        nrOfPrograms.setText("Number of program states: " + cont.getRepository().getProgramList().size());
    }
}

