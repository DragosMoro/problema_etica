package com.example.problema_etica;

import com.example.problema_etica.domain.Nevoie;
import com.example.problema_etica.domain.Persoana;
import com.example.problema_etica.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class AjutorController {
    @FXML
    public TableColumn<Nevoie, String> statusColumn;

    @FXML
    public Button rezolvaButton;


    @FXML
    public TableColumn<Nevoie, String> deadlineColumn;

    @FXML
    public TableColumn<Nevoie, String> descriereColumn;

    @FXML
    public TableView<Nevoie> nevoiOrasTableView;

    @FXML
    public TableColumn<Nevoie, String> omInNevoieColumn;

    @FXML
    public TableColumn<Nevoie, String> onSalvatorColumn;

    @FXML
    public Tab tab1;

    @FXML
    public Tab tab2;

    @FXML
    public TableColumn<Nevoie, String> titluColumn;

    private Service service;
    private Persoana persoanaLogata;
    ObservableList<Nevoie> oameniInNevoieModel = FXCollections.observableArrayList();

    @FXML
    public Label username;
    public void initialize(Service service, Persoana user) {
    this.service=service;
    this.persoanaLogata = user;
    username.setText(persoanaLogata.getUsername());
    populareNevoiOrasTable();
    }

    public void populareNevoiOrasTable()
    {
        ArrayList<Persoana> persoaneDinAcelasiOras = filtrareDupaOras();
        ArrayList<Nevoie> oameniInNevoie = filterDupaOmInNevoie(persoaneDinAcelasiOras);
        oameniInNevoieModel.setAll(oameniInNevoie);
        titluColumn.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        omInNevoieColumn.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        onSalvatorColumn.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        nevoiOrasTableView.setItems(oameniInNevoieModel);
    }

    private ArrayList<Persoana> filtrareDupaOras()
    {
        ArrayList<Persoana> persoane = service.getAllPersoane();
        persoane.remove(persoanaLogata);
        ArrayList<Persoana> concetateni = (ArrayList<Persoana>) persoane
                .stream()
                .filter(q -> Objects.equals(q.getOras(), persoanaLogata.getOras()))
                .collect(Collectors.toList());
        return concetateni;
    }
    private ArrayList<Nevoie> filterDupaOmInNevoie(ArrayList<Persoana> persoane)
    {
        ArrayList<Nevoie> nevoi = service.getAllNevoi();
        ArrayList<Nevoie> listaFinala = new ArrayList<>();
        for (Persoana persoana:persoane)
            for(Nevoie nevoie:nevoi)
                if (Objects.equals(persoana.getId(),nevoie.getOmInNevoie()))
                    listaFinala.add(nevoie);

        return listaFinala;

    }

    @FXML
    public void onRezolvaButttonAction() {
        Nevoie nevoieDeRezolvat = nevoiOrasTableView.getSelectionModel().getSelectedItem();
        if (nevoieDeRezolvat.getOmSalvator() == 0 || Objects.equals(nevoieDeRezolvat.getStatus(), "Caut erou!")) {
            service.updateNevoie(nevoieDeRezolvat.getId(), persoanaLogata.getId());
            populareNevoiOrasTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nevoia i-a fost atribuita persoanei", ButtonType.CLOSE);
            alert.show();
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Nevoia nu mai poate fi selectata", ButtonType.OK);
            alert1.show();
        }
    }





}
