package com.example.problema_etica;

import com.example.problema_etica.domain.Persoana;
import com.example.problema_etica.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InregistrareController {

    @FXML
    public Button butonInregistrare;
    @FXML
    public Button butonLogare;

    @FXML
    public TextField numarStradaInregistrare;

    @FXML
    public TextField numeInregistrare;

    @FXML
    public ComboBox<Persoana.Orase> orasInregistrare;

    @FXML
    public TextField parolaInregistrare;

    @FXML
    public TextField prenumeInregistrare;

    @FXML
    public TextField stradaInregistrare;

    @FXML
    public TextField telefonInregistrare;

    @FXML
    public TextField usernameInregistrare;

    private Service service;

    public void setService(Service service){
        this.service = service;
        initialize();
    }
    private void initialize()
    {
        orasInregistrare.getItems().setAll(Persoana.Orase.values());
    }
    @FXML
    public void onInregistrareButonClick()
    {
        String nume = numeInregistrare.getText();
        String prenume = prenumeInregistrare.getText();
        String username = usernameInregistrare.getText();
        String parola = parolaInregistrare.getText();
        String oras = orasInregistrare.getValue().toString();
        String strada = stradaInregistrare.getText();
        String numarStrada = numarStradaInregistrare.getText();
        String telefon = telefonInregistrare.getText();
        Persoana.Orase oras1 = Persoana.Orase.valueOf(oras);
        Persoana persoana = new Persoana(nume,prenume,username,parola, oras1, strada, numarStrada,telefon);
        service.adaugaPersoana(persoana);


    }
    @FXML
    public void onLogareButonClick()
    {

        Scene scene;
        FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource("logare.fxml"));
        try{
            scene = new Scene(loader.load(), 801, 501);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        LogareController logareController = loader.getController();
        logareController.initialize(service);

        Stage currentStage = (Stage) butonLogare.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.setTitle("Logare");
        currentStage.close();
        newStage.show();
    }

}
