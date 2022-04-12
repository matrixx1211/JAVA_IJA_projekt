module ija.proj {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens ija.proj to javafx.fxml, com.google.gson;
    opens ija.proj.uml to javafx.fxml, com.google.gson;
    exports ija.proj;
    exports ija.proj.uml;
}
