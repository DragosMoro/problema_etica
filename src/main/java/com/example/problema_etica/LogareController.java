package com.example.problema_etica;

import com.example.problema_etica.domain.Persoana;
import com.example.problema_etica.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class LogareController {

    private Service service;
    @FXML
    ListView<String> listaPersoane;
    ObservableList<String> persoaneList = FXCollections.observableArrayList();

    public void initialize(Service service)
    {
        this.service = service;
        persoaneList.setAll();
        listaPersoane.setItems(persoaneList);
        populareLista();

    }

    private void populareLista()
    {
        ArrayList<Persoana> persoane = service.getAllPersoane();
        ArrayList<String> usernameuri = new ArrayList<>();
        for(Persoana persoana:persoane)
        {
            usernameuri.add(persoana.getUsername());
        }
        persoaneList.setAll(usernameuri);


    }
    @FXML
    public void onMouseClickedRedirect()
    {
        String username;
        username= listaPersoane.getSelectionModel().getSelectedItem();

           Persoana user = getPersoanaByUsername(username);
        Scene scene;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("ajutor.fxml"));
        try{
            scene = new Scene(loader.load(), 801, 501);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        AjutorController ajutorController = loader.getController();
        ajutorController.initialize(service, user);

        Stage currentStage = (Stage) listaPersoane.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Logare");
        currentStage.close();
        newStage.show();
    }
    private Persoana getPersoanaByUsername(String username)
    {
        ArrayList<Persoana> persoane = service.getAllPersoane();
        for (Persoana persoana:persoane)
            if(Objects.equals(persoana.getUsername(), username))
                return persoana;
        return null;
    }
    }




