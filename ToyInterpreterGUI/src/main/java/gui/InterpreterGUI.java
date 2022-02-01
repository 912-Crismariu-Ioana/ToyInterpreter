package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterGUI extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader programLoader = new FXMLLoader(gui.InterpreterGUI.class.getResource("program-loader.fxml"));
        VBox root = (VBox) programLoader.load();
        Scene selectionScene = new Scene(root, 1000, 500);
        stage.setTitle("Load Program");
        stage.setScene(selectionScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
