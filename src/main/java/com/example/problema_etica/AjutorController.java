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
    public TableColumn<Nevoie, String> statusColumn1;

    @FXML
    public Button rezolvaButton;


    @FXML
    public TableColumn<Nevoie, String> deadlineColumn1;

    @FXML
    public TableColumn<Nevoie, String> descriereColumn1;

    @FXML
    public TableColumn<Nevoie, String> deadlineColumn;

    @FXML
    public TableColumn<Nevoie, String> descriereColumn;

    @FXML
    public TableView<Nevoie> nevoiOrasTableView;
    @FXML
    public TableView<Nevoie> fapteBuneTableView;

    @FXML
    public TableColumn<Nevoie, String> omInNevoieColumn;

    @FXML
    public TableColumn<Nevoie, String> onSalvatorColumn;
    @FXML
    public TableColumn<Nevoie, String> omInNevoieColumn1;

    @FXML
    public TableColumn<Nevoie, String> onSalvatorColumn1;

    @FXML
    public Tab tab1;

    @FXML
    public Tab tab2;

    @FXML
    public TableColumn<Nevoie, String> titluColumn;
    @FXML
    public TableColumn<Nevoie, String> titluColumn1;

    @FXML
    public DatePicker deadlineDatePicker;

    @FXML
    public TextField titluTextField;

    @FXML
    public TextField descriereTextField;




    @FXML
    public Button adaugareNevoieButton;



    private Service service;
    private Persoana persoanaLogata;
    ObservableList<Nevoie> oameniInNevoieModel = FXCollections.observableArrayList();

    ObservableList<Nevoie> fapteBuneModel = FXCollections.observableArrayList();


    @FXML
    public Label username;
    public void initialize(Service service, Persoana user) {
    this.service=service;
    this.persoanaLogata = user;
    username.setText(persoanaLogata.getUsername());
    populareNevoiOrasTable();
    updateTabelListaDeFapteBune();
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
            updateTabelListaDeFapteBune();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nevoia i-a fost atribuita persoanei", ButtonType.CLOSE);
            alert.show();
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Nevoia nu mai poate fi selectata", ButtonType.OK);
            alert1.show();
        }

    }
    private void updateTabelListaDeFapteBune()
    {
        ArrayList<Nevoie> nevoi = service.getAllNevoi();
        ArrayList<Nevoie> nevoiRezolvateDeUtilizator = new ArrayList<>();
        for (Nevoie nevoie : nevoi)
        {
            if (Objects.equals(nevoie.getOmSalvator(), persoanaLogata.getId()))
                nevoiRezolvateDeUtilizator.add(nevoie);
        }

        fapteBuneModel.setAll(nevoiRezolvateDeUtilizator);
        titluColumn1.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriereColumn1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadlineColumn1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        omInNevoieColumn1.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        onSalvatorColumn1.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
        statusColumn1.setCellValueFactory(new PropertyValueFactory<>("status"));
        fapteBuneTableView.setItems(fapteBuneModel);

    }



    public void onAdaugaNevoieButtonClick()
    {
        String titlu = titluTextField.getText();
        String descriere = descriereTextField.getText();
        LocalDateTime deadline = deadlineDatePicker.getValue().atStartOfDay();
        Long omInNevoie = persoanaLogata.getId();
        Long omSalvator = Long.valueOf(0);
        String status = "Caut erou!";
        Nevoie nevoie = new Nevoie(titlu,descriere,deadline,omInNevoie,omSalvator,status);
        service.adaugaNevoie(nevoie);
        populareNevoiOrasTable();


    }



}
