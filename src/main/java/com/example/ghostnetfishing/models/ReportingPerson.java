package com.example.ghostnetfishing.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reporting_persons")
public class ReportingPerson extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public ReportingPerson() {}

    public ReportingPerson(String phoneNumber) {
        setPhoneNumber(phoneNumber);
    }

    public ReportingPerson(String phoneNumber, String name) {
        setPhoneNumber(phoneNumber);
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAnon() {
        return this.getPhoneNumber() == null;
    }
}
