package com.example.problema_etica;

import com.example.problema_etica.repository.PersoanaRepo;
import com.example.problema_etica.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application {
    String url = "jdbc:postgresql://localhost:5432/civic";
    String userName = "postgres";
    String password = "postgres";


    @Override
    public void start(Stage stage) throws IOException {
        Service service = Service.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("inregistrare.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 265, 461);
        InregistrareController inregistrareController = fxmlLoader.getController();
        inregistrareController.setService(service);
        stage.setTitle("Logare");
        stage.setScene(scene);
        //stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
