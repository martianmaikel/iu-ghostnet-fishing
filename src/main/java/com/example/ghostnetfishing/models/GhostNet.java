package com.example.ghostnetfishing.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ghost_nets")
public class GhostNet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double lat;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double lng;

    private double sizeInSquareMeters;

    @Enumerated(EnumType.STRING)
    private GhostNetStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private ReportingPerson reportingPerson;

    @ManyToOne
    private RecoveryPerson recoveryPerson;

    private LocalDateTime reportedAt;

    @OneToMany(mappedBy = "ghostNet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GhostNetStatusLog> log = new ArrayList<>();

    public GhostNet() {
        this.reportedAt = LocalDateTime.now();
    }

    public GhostNet(Long id, String location, Double lat, Double lng, double sizeInSquareMeters,
                    GhostNetStatus status, ReportingPerson reportingPerson, RecoveryPerson recoveryPerson) {
        this.id = id;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.sizeInSquareMeters = sizeInSquareMeters;
        setStatus(status, reportingPerson);
        this.reportingPerson = reportingPerson;
        this.recoveryPerson = recoveryPerson;
        this.reportedAt = LocalDateTime.now();

    }

    // === Status-Log Methoden ===

    public void addStatusLog(GhostNetStatusLog statusLog) {
        if (log == null) {
            log = new ArrayList<>();
        }
        log.add(statusLog);
        statusLog.setGhostNet(this);
    }


    public List<GhostNetStatusLog> getLog() {
        return log;
    }

    // === Getter & Setter ===

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public double getSizeInSquareMeters() {
        return sizeInSquareMeters;
    }

    public void setSizeInSquareMeters(double sizeInSquareMeters) {
        this.sizeInSquareMeters = sizeInSquareMeters;
    }

    public GhostNetStatus getStatus() {
        return status;
    }

    public void setStatus(GhostNetStatus newStatus) {
        if (newStatus != null && !newStatus.equals(this.status)) {
            this.status = newStatus;

            GhostNetStatusLog logEntry = new GhostNetStatusLog(this, newStatus);
            addStatusLog(logEntry);
        } else {
            this.status = newStatus;
        }
    }

    public void setStatus(GhostNetStatus newStatus, Person person) {
        if (newStatus != null && !newStatus.equals(this.status)) {
            this.status = newStatus;

            GhostNetStatusLog logEntry = new GhostNetStatusLog(this, newStatus, person);
            addStatusLog(logEntry);
        } else {
            this.status = newStatus;
        }
    }


    public ReportingPerson getReportingPerson() {
        return reportingPerson;
    }

    public void setReportingPerson(ReportingPerson reportingPerson) {
        this.reportingPerson = reportingPerson;
    }

    public RecoveryPerson getRecoveryPerson() {
        return recoveryPerson;
    }

    public void setRecoveryPerson(RecoveryPerson recoveryPerson) {
        this.recoveryPerson = recoveryPerson;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }

    public String getLatFormatted() {
        if (lat == null) return "";
        String direction = lat >= 0 ? "N" : "S";
        return String.format("%.4f° %s", Math.abs(lat), direction);
    }

    public String getLngFormatted() {
        if (lng == null) return "";
        String direction = lng >= 0 ? "E" : "W";
        return String.format("%.4f° %s", Math.abs(lng), direction);
    }

    public String getLatLngFormatted() {
        return getLatFormatted() + ", " + getLngFormatted();
    }

    public String getStatusStr() {
        return status.toString();
    }

}
