package com.example.problema_etica.service;

import com.example.problema_etica.domain.Nevoie;
import com.example.problema_etica.domain.Persoana;
import com.example.problema_etica.repository.NevoieRepo;
import com.example.problema_etica.repository.PersoanaRepo;

import java.util.ArrayList;

public class Service {

    private final PersoanaRepo persoanaRepo;

    private final NevoieRepo nevoieRepo;

    public Service(PersoanaRepo persoanaRepo, NevoieRepo nevoieRepo) {
        this.persoanaRepo = persoanaRepo;
        this.nevoieRepo = nevoieRepo;
    }

    public ArrayList<Persoana> getAllPersoane()
    {
        return persoanaRepo.getAll();
    }

    public ArrayList<Nevoie> getAllNevoi()
    {
        return nevoieRepo.getAll();
    }

    public void adaugaPersoana(Persoana persoana)
    {
        persoanaRepo.adaugaPersoana(persoana);

    }
    public static Service getInstance()
    {
        return new Service( new PersoanaRepo(
                "postgres", "postgres", "jdbc:postgresql://localhost:5432/civic"),
                new NevoieRepo(
                        "postgres", "postgres", "jdbc:postgresql://localhost:5432/civic"));
    }

    public void updateNevoie(Long idNevoie, Long id) {
        nevoieRepo.updateNevoie(idNevoie,id);
    }

    public void adaugaNevoie(Nevoie nevoie) {
        nevoieRepo.adaugaNevoie(nevoie);
    }
}
