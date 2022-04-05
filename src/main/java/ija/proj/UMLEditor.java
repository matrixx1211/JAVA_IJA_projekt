package ija.proj;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UMLEditor {
    @FXML
    private Button gotoUMLEditor;
    @FXML
    private void gotoUMLClass() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UMLshit.fxml"));
        Scene newscene = new Scene(fxmlLoader.load(), 640, 480);
        Stage stage = new Stage();
        stage.setScene(newscene);
        stage.show();
    }
}
