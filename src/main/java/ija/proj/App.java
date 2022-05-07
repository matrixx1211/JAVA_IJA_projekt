/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci hlavní části programu, která spouští GUI.
 */

package ija.proj;

import ija.proj.uml.ClassDiagram;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hlavní třída pro GUI aplikaci
 */
public class App extends Application {
    public static Scene scene;
    /**
     * Uchovává instanci diagramu tříd.
     */
    public static ClassDiagram classDiagram;
    /**
     * Počítadlo IDček pro ukládání do souboru
     */
    public Integer idCounter = 1;
    /**
     * List pro přístupové modifikátory
     */
    public static ObservableList<String> accessibilityList = FXCollections.observableArrayList();
    /**
     * List entit (tříd a rozhraní) pro relace
     */
    public static ObservableList<String> classList = FXCollections.observableArrayList();

    public static ClassDiagramController controller;

    public static Stage stage;
    public static Stage seqHelp;
    public static Stage commHelp;
    public static Stage classHelp;
    public static List<String> undoData;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ClassDiagram"));
        App.stage = stage;
        stage.setScene(scene);
        stage.setTitle("UML Editor");
        stage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // main help
        Scene helpScene = new Scene(loadFXML("Help") , 600, 400);
        Stage helpStage = new Stage();
        helpStage.setTitle("Main help");
        helpStage.setScene(helpScene);
        helpStage.show();

        // seq help
        Scene seqHelpScene = new Scene(loadFXML("SequenceDiagramHelp") , 600, 400);
        seqHelp = new Stage();
        seqHelp.setTitle("Sequence diagram help");
        seqHelp.setScene(seqHelpScene);

        // comm help
        Scene commHelpScene = new Scene(loadFXML("CommunicationDiagramHelp") , 600, 400);
        commHelp = new Stage();
        commHelp.setTitle("Communication diagram help");
        commHelp.setScene(commHelpScene);

        // class help
        Scene classHelpScene = new Scene(loadFXML("ClassDiagramHelp") , 600, 400);
        classHelp = new Stage();
        classHelp.setTitle("Class diagram help");
        classHelp.setScene(classHelpScene);
    }

    /**
     * Funkce nastavuje FXML soubor, který načíst.
     *
     * @param fxml název FXML souboru
     * @throws IOException
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Načítá FXML soubor
     * @param fxml jméno fxml souboru bez .fxml
     * @return načtený fxml
     * @throws IOException IO chyba (soubor neexistuje atp...)
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent load = fxmlLoader.load();
        if (fxml == "ClassDiagram") {
            controller = fxmlLoader.getController();
        }
        return load;
    }

    /**
     * Hlavní funkce, která vytváří classDiagram a spouští hlavní FXML.
     *
     * @param args Argumenty
     */
    public static void main(String[] args) {
        // vytvoření diagramu tříd
        classDiagram = new ClassDiagram("Diagram");

        // přidání přístupových modifikátorů
        accessibilityList.add("+");
        accessibilityList.add("-");
        accessibilityList.add("#");
        accessibilityList.add("~");

        // přídání možnosti, aby šlo vybrat nic
        classList.add("");

        // undo
        undoData = new ArrayList<String>();

        launch();
    }

}