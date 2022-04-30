package ija.proj;

import ija.proj.uml.ClassDiagram;
import ija.proj.uml.SequenceDiagram;
import ija.proj.uml.UMLClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import static ija.proj.uml.SequenceDiagram.seqDiagAllClassList;

public class SequenceDiagramController {
    @FXML
    ChoiceBox<String> classNewChoice;
    @FXML
    ChoiceBox<String> classChoice;
    @FXML
    Button classAddBtn;
    @FXML
    Button classDelBtn;
    @FXML
    VBox all;

    public void initialize(){
        SequenceDiagram seqDiag = ClassDiagramController.classDiagram.findSeqDiagram(ClassDiagramController.title);
        classNewChoice.setItems(seqDiag.getSeqDiagAllClassList());
    }

    @FXML
    public void classAddBtnClicked() {
        seqDiagAllClassList.add("hej");
        //classNewChoice.setItems(seqDiagAllClassList);

        System.out.println(((Stage)all.getScene().getWindow()).getTitle());
    }
    @FXML
    public void classDelBtnClicked() {
        seqDiagAllClassList.add("hej");
        //classNewChoice.setItems(seqDiagAllClassList);
    }

    public void synch(){

    }

}
