package ija.proj;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
/* import javafx.scene.control.Label;
import javafx.scene.layout.StackPane; */
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class App extends Application {
	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {
		/* Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("UMLEditor.fxml"));
		scene = new Scene(root, 640, 480);
		stage.setScene(scene);
		stage.show(); */
		System.out.println(getClass().getClassLoader().getResource("").getPath("UMLEditor.fxml"));
	}

	public static void main(String[] args) {
		launch();
	}
}
