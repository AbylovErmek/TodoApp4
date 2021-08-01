package com.geektech.todoapp4.ui.models;

import java.io.Serializable;

public class Task implements Serializable {

    private String tite;

    public Task(String tite) {
        this.tite = tite;
    }

    public String getTite() {
        return tite;
    }

    public void setTite(String tite) {
        this.tite = tite;
    }
}
