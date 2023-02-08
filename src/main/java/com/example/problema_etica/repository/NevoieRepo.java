package com.example.problema_etica.repository;

import com.example.problema_etica.domain.Nevoie;
import com.example.problema_etica.domain.Persoana;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class NevoieRepo implements IRepository<Nevoie>{
    private ArrayList<Nevoie> nevoiList;
    private final String userName;
    private final String password;
    private final String URL;

    public NevoieRepo(String userName, String password, String URL) {
        this.userName = userName;
        this.password = password;
        this.URL = URL;
        nevoiList = new ArrayList<>();
        loadData();
    }
    @Override
    public void add(Nevoie entity) {

    }

    @Override
    public void remove(int id) {

    }
    public void loadData(){
        try {

            Connection connection = DriverManager.getConnection(URL, userName, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM nevoi");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String titlu = resultSet.getString("titlu");
                String descriere = resultSet.getString("descriere");
                LocalDateTime deadline = LocalDateTime.parse(resultSet.getString("deadline"));
                Long omInNevoie = resultSet.getLong("om_in_nevoie");
                Long omSalvator = resultSet.getLong("om_salvator");
                String status = resultSet.getString("status");
                Nevoie nevoie = new Nevoie(titlu,descriere,deadline,omInNevoie,omSalvator,status);
                nevoie.setId(id);
                nevoiList.add(nevoie);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }
    @Override
    public ArrayList<Nevoie> getAll()
    {
        return nevoiList;
    }

    public void saveData()
    {
        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE nevoi");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO nevoi (id, titlu,descriere,deadline,om_in_nevoie, om_salvator, status) VALUES (?,?,?,?,?,?,?)");
            for (Nevoie nevoie : nevoiList) {
                ps.setLong(1, nevoie.getId());
                ps.setString(2,nevoie.getTitlu());
                ps.setString(3,nevoie.getDescriere());
                ps.setString(4,nevoie.getDeadline().toString());
                ps.setLong(5,nevoie.getOmInNevoie());
                ps.setLong(6,nevoie.getOmSalvator());
                ps.setString(7,nevoie.getStatus());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNevoie(Long idNevoie, Long id) {
        for(Nevoie nevoie:nevoiList)
            if(Objects.equals(idNevoie, nevoie.getId())) {
                nevoie.setOmSalvator(id);
                nevoie.setStatus("Erou gasit!");
                break;
            }
        saveData();
    }


    public void adaugaNevoie(Nevoie nevoie) {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        nevoie.setId(id);
        nevoiList.add(nevoie);
        saveData();
    }
}
