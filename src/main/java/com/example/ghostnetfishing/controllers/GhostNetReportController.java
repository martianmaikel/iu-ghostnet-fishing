package com.example.ghostnetfishing.controllers;

import com.example.ghostnetfishing.models.*;
import com.example.ghostnetfishing.services.GhostNetManageService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;

@Named
@ViewScoped
public class GhostNetReportController implements Serializable {

    @Inject
    private GhostNetManageService ghostNetManageService;

    private String location;
    private BigDecimal lat;
    private BigDecimal lng;

    private double size;

    private String name;
    private String phoneNumber;

    // === Getter & Setter ===

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getLat() { return lat; }
    public void setLat(BigDecimal lat) { this.lat = lat; }

    public BigDecimal getLng() { return lng; }
    public void setLng(BigDecimal lng) { this.lng = lng; }

    public double getSize() {
        return size;
    }
    public void setSize(double size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Transactional
    public void submit() {
        ReportingPerson reporter = new ReportingPerson();
        reporter.setName(name);
        reporter.setPhoneNumber(phoneNumber);

        GhostNet net = new GhostNet();
        net.setLocation(location);
        net.setLat(lat != null ? lat.doubleValue() : null);
        net.setLng(lng != null ? lng.doubleValue() : null);
        net.setSizeInSquareMeters(size);
        net.setStatus(GhostNetStatus.REPORTED, reporter);
        net.setReportingPerson(reporter);

        ghostNetManageService.saveNewReportedNet(net);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Erfolg", "Geisternetz wurde gemeldet. Vielen Dank!"));

        reset();
    }

    private void reset() {
        location = null;
        lat = null;
        lng = null;
        size = 0;
        name = null;
        phoneNumber = null;
    }
}
