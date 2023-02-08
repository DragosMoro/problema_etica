package com.example.problema_etica.service;

import com.example.problema_etica.domain.Persoana;
import com.example.problema_etica.repository.PersoanaRepo;

import java.util.ArrayList;

public class Service {

    private final PersoanaRepo persoanaRepo;

    public Service(PersoanaRepo persoanaRepo) {
        this.persoanaRepo = persoanaRepo;
    }

    public ArrayList<Persoana> getAllPersoane()
    {
        return persoanaRepo.getAll();
    }

    public void adaugaPersoana(Persoana persoana)
    {
        persoanaRepo.adaugaPersoana(persoana);

    }
    public static Service getInstance()
    {
        return new Service( new PersoanaRepo(
                "postgres", "postgres", "jdbc:postgresql://localhost:5432/civic"));
    }
}
