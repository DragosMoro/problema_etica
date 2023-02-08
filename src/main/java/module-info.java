module com.example.problema_etica {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.problema_etica to javafx.fxml;
    opens com.example.problema_etica.domain to javafx.fxml;
     opens com.example.problema_etica.service to javafx.fxml;
    opens com.example.problema_etica.repository to javafx.fxml;
    exports com.example.problema_etica;
    exports com.example.problema_etica.service;
    exports com.example.problema_etica.domain;
    exports com.example.problema_etica.repository;
}