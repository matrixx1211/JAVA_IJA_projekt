package ija.proj;

import ija.proj.uml.ClassDiagram;
import ija.proj.uml.SequenceDiagram;
import ija.proj.uml.UMLClass;
import ija.proj.uml.UMLRelation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;

import static ija.proj.uml.SequenceDiagram.seqDiagAllClassList;
import static ija.proj.uml.SequenceDiagram.seqDiagClassList;

public class SequenceDiagramController {
    double orgSceneX, orgTranslateX;

    @FXML
    ChoiceBox<String> classNewChoice;
    @FXML
    ChoiceBox<String> classChoice;
    @FXML
    Button classAddBtn;
    @FXML
    Button classDelBtn;
    @FXML
    AnchorPane main;


    SequenceDiagram seqDiag;

    public void initialize(){
        seqDiag = ClassDiagramController.classDiagram.findSeqDiagram(ClassDiagramController.title);
        classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
    }

    @FXML
    public void classAddBtnClicked() {
        if (classNewChoice.getValue() != null){
            String name = classNewChoice.getValue();
            seqDiagAllClassList.remove(name);
            seqDiagClassList.add(name);
            classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
            classChoice.setItems(seqDiag.getSeqDiagClassList());

            //vytvoreni grafickeho elementu
            TextField textField = new TextField(name);
            textField.setAlignment(Pos.CENTER);
            textField.setTranslateY(30);
            textField.setId(name.replaceAll("\\s+", "€"));
            System.out.println(textField.getId());
            //horizontalni drag
            textField.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    orgSceneX = event.getSceneX();
                    orgTranslateX = ((TextField) (event.getSource())).getTranslateX();
                }
            });
            textField.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double offsetX = event.getSceneX() - orgSceneX;
                    double newTranslateX = orgTranslateX + offsetX;

                    ((TextField) (event.getSource())).setTranslateX(newTranslateX);
                }
            });

            main.getChildren().add(textField);
        }
    }
    @FXML
    public void classDelBtnClicked() {
        if (classChoice.getValue() != null){
            String name = classChoice.getValue();
            seqDiagClassList.remove(name);
            seqDiagAllClassList.add(name);
            classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
            classChoice.setItems(seqDiag.getSeqDiagClassList());

            //mazani graficke reprezentace
            main.getChildren().remove(main.lookup("#" + name.replaceAll("\\s+", "€")));
        }
    }

    public void synch(){

    }

}
