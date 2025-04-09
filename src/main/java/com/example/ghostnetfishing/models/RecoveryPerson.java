package com.example.ghostnetfishing.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recovery_persons")
public class RecoveryPerson extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @OneToMany(mappedBy = "recoveryPerson")
    private List<GhostNet> assignedNets;

    public RecoveryPerson() {}

    public RecoveryPerson(String name, String phoneNumber, List<GhostNet> assignedNets) {
        setName(name);
        setPhoneNumber(phoneNumber);
        this.assignedNets = assignedNets;
    }

    public RecoveryPerson(String name, String phoneNumber) {
        setName(name);
        setPhoneNumber(phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GhostNet> getAssignedNets() {
        return assignedNets;
    }

    public void setAssignedNets(List<GhostNet> assignedNets) {
        this.assignedNets = assignedNets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
