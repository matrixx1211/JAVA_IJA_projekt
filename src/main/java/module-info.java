module ija.proj {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens ija.proj to javafx.fxml;
    exports ija.proj;
}
