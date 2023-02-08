package com.example.problema_etica.repository;

import java.util.ArrayList;

public interface IRepository<E> {
    public void add(E entity);

    public void remove(int id);

    public void saveData();

    public void loadData();

    public ArrayList<E> getAll();
}
