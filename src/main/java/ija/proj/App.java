/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci hlavní části programu, která spouští GUI
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


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("UMLEditor"));
        stage.setScene(scene);
        stage.setTitle("UML Editor");
        stage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
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

        launch();
    }

}