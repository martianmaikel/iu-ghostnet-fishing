package com.example.ghostnetfishing.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Log-Eintrag für den Statusverlauf eines Geisternetzes.
 * Hält fest, welcher Status gesetzt wurde, wann – und von wem (Person & Typ).
 */
@Entity
@Table(name = "ghost nets_status_logs")
public class GhostNetStatusLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private GhostNet ghostNet;

    @Enumerated(EnumType.STRING)
    private GhostNetStatus newStatus;

    private LocalDateTime changedAt;

    @ManyToOne
    @JoinColumn(name = "reporting_person_id")
    private ReportingPerson changedByReportingPerson;

    @ManyToOne
    @JoinColumn(name = "recovery_person_id")
    private RecoveryPerson changedByRecoveryPerson;

    public GhostNetStatusLog() {
        this.changedAt = LocalDateTime.now();
    }

    public GhostNetStatusLog(GhostNet ghostNet, GhostNetStatus newStatus) {
        this.ghostNet = ghostNet;
        this.newStatus = newStatus;
        this.changedAt = LocalDateTime.now();
    }

    public GhostNetStatusLog(GhostNet ghostNet, GhostNetStatus newStatus, Person changedByPerson) {
        this.ghostNet = ghostNet;
        this.newStatus = newStatus;
        this.changedAt = LocalDateTime.now();
        setChangedByPerson(changedByPerson);
    }

    public Long getId() {
        return id;
    }

    public GhostNet getGhostNet() {
        return ghostNet;
    }

    public void setGhostNet(GhostNet ghostNet) {
        this.ghostNet = ghostNet;
    }

    public GhostNetStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(GhostNetStatus newStatus) {
        this.newStatus = newStatus;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    public Person getChangedByPerson() {
        if (changedByRecoveryPerson != null) {
            return changedByRecoveryPerson;
        } else if (changedByReportingPerson != null) {
            return changedByReportingPerson;
        } else {
            return null;
        }
    }

    public void setChangedByPerson(Person person) {
        if (person instanceof RecoveryPerson) {
            this.changedByRecoveryPerson = (RecoveryPerson) person;
        } else if (person instanceof ReportingPerson) {
            this.changedByReportingPerson = (ReportingPerson) person;
        } else {
            throw new IllegalArgumentException("Unbekannter Personentyp: " + person);
        }
    }


    public String getChangedAtFormatted() {
        return changedAt != null ? changedAt.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) : "";
    }



}
