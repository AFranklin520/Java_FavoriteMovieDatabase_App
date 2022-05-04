module com.cis2235.franklin.franklinp5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.naming;
    requires java.desktop;

    opens com.cis2235.franklin.franklinp5 to javafx.fxml;
    exports com.cis2235.franklin.franklinp5;
}