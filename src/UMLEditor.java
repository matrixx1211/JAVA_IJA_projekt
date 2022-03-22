
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UMLEditor {

    @FXML
    private Button gotoUMLEditor;
    @FXML
    private void gotoUMLClass() throws IOException {
        App.setRoot("../UMLClass");
    }
}
