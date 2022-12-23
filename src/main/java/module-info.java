module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens org.example to javafx.fxml;
    exports org.example;
}