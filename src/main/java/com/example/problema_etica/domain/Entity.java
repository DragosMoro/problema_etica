package com.example.problema_etica.domain;

import java.io.Serial;
import java.io.Serializable;

public class Entity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}