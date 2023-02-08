package com.example.problema_etica.repository;

import com.example.problema_etica.domain.Persoana;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;


public class PersoanaRepo implements IRepository<Persoana>{
    private ArrayList<Persoana> persoaneList;
    private final String userName;
    private final String password;
    private final String URL;


    public PersoanaRepo(String userName, String password, String URL) {
        this.userName = userName;
        this.password = password;
        this.URL = URL;
        this.persoaneList = new ArrayList<>();
        loadData();
    }
    @Override
    public void add(Persoana entity) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void saveData() {
        try {
            Connection connection = DriverManager.getConnection(URL, userName, password);
            PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE persoane");
            ps.execute();
            ps = connection.prepareStatement("INSERT INTO persoane (id, nume,prenume,username,parola, oras, strada, numar_strada,telefon) VALUES (?,?,?,?,?,?,?,?,?)");
            for (Persoana persoana : persoaneList) {
                ps.setLong(1, persoana.getId());
                ps.setString(2,persoana.getNume());
                ps.setString(3,persoana.getPrenume());
                ps.setString(4,persoana.getUsername());
                ps.setString(5,persoana.getParola());
                ps.setString(6,persoana.getOras());
                ps.setString(7,persoana.getStrada());
                ps.setString(8,persoana.getNumarStrada());
                ps.setString(9,persoana.getTelefon());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadData(){
        try {

            Connection connection = DriverManager.getConnection(URL, userName, password);
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM persoane");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                Persoana.Orase oras = Persoana.Orase.valueOf(resultSet.getString("oras"));
                String strada = resultSet.getString("strada");
                String numarStrada = resultSet.getString("numar_strada");
                String telefon = resultSet.getString("telefon");
                Persoana persoana = new Persoana( nume,prenume,username,parola, oras, strada, numarStrada,telefon);
                persoana.setId(id);
                persoaneList.add(persoana);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }



    }

    @Override
    public ArrayList<Persoana> getAll()
    {
        return persoaneList;
    }
    public void adaugaPersoana(Persoana persoana)
    {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        persoana.setId(id);
        persoaneList.add(persoana);
        saveData();


    }

}
