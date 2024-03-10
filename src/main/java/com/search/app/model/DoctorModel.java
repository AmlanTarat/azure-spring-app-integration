package com.search.app.model;

public class DoctorModel {

    private final Long id;

    private final String content;

    public DoctorModel(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
