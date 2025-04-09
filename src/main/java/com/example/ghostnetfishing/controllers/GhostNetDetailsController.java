package com.example.ghostnetfishing.controllers;

import com.example.ghostnetfishing.models.GhostNet;
import com.example.ghostnetfishing.models.GhostNetStatus;
import com.example.ghostnetfishing.models.ReportingPerson;
import com.example.ghostnetfishing.services.GhostNetManageService;
import com.example.ghostnetfishing.sessions.RecoveryPersonSession;
import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class GhostNetDetailsController implements Serializable {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private RecoveryPersonSession session;

    @Inject
    private GhostNetManageService ghostNetManageService;

    private Long selectedNetId;
    private GhostNet selectedNet;
    private GhostNetStatus selectedNetStatus;

    private String anonName;
    private String anonPhone;


    public Long getSelectedNetId() {
        return selectedNetId;
    }

    public void setSelectedNetId(Long selectedNetId) {
        this.selectedNetId = selectedNetId;
    }

    public GhostNet getSelectedNet() {
        return selectedNet;
    }

    public void loadNet(Long id) {
        if (id != null) {
            selectedNet = ghostNetManageService.getGhostNetById(id);
            if (selectedNet == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Netz nicht gefunden", "Das Geisternetz mit ID " + id + " existiert nicht."));
            }
        }
    }

    public GhostNetStatus getSelectedNetStatus() {
        return selectedNetStatus;
    }

    public void setSelectedNetStatus(GhostNetStatus selectedNetStatus) {
        this.selectedNetStatus = selectedNetStatus;
    }

    @Transactional
    public void updateStatus() {
        if (selectedNet != null && selectedNetStatus != null && selectedNetStatus != selectedNet.getStatus()) {
            if(session.isLoggedIn()) {
                ghostNetManageService.updateStatus(selectedNet, selectedNetStatus, session.getCurrentUser());
            } else {
                ghostNetManageService.updateStatus(selectedNet, selectedNetStatus);
            }
            selectedNet.setStatus(selectedNetStatus);
            try {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(FacesContext.getCurrentInstance()
                                .getExternalContext()
                                .getRequestContextPath() + "/ghostnet-details.xhtml?net_id=" + selectedNet.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAnonName() {
        return anonName;
    }

    public void setAnonName(String anonName) {
        this.anonName = anonName;
    }

    public String getAnonPhone() {
        return anonPhone;
    }

    public void setAnonPhone(String anonPhone) {
        this.anonPhone = anonPhone;
    }

    public boolean isCanShowLostButton() {
        return selectedNet != null && !GhostNetStatus.LOST.equals(selectedNet.getStatus());
    }

    public void markAsLostAsLoggedInOrPrompt() {
        if (session.isLoggedIn() && selectedNet != null) {
            ghostNetManageService.updateStatus(selectedNet, GhostNetStatus.LOST, session.getCurrentUser());

            try {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(FacesContext.getCurrentInstance()
                                .getExternalContext()
                                .getRequestContextPath() + "/ghostnet-details.xhtml?net_id=" + selectedNet.getId());
            } catch (IOException e) {
                e.printStackTrace(); // Optional: Logging
            }
        }
    }


    @Transactional
    public void markAsLostAnonymously() {
        if (selectedNet != null && anonName != null && !anonName.isBlank()) {
            ReportingPerson anon = new ReportingPerson();
            anon.setName(anonName);
            anon.setPhoneNumber(anonPhone);

            em.persist(anon); // 👈 transient object fix

            ghostNetManageService.updateStatus(selectedNet, GhostNetStatus.LOST, anon);
            selectedNet.setStatus(GhostNetStatus.LOST, anon);

            anonName = null;
            anonPhone = null;

            try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/ghostnet-details.xhtml?net_id=" + selectedNet.getId());
            } catch (IOException e) {
                e.printStackTrace(); // Optional: Logging
            }
        }
    }

    @Transactional
    public void assignToCurrentUser(GhostNet net) {
        if (net != null && session.isLoggedIn()) {
            ghostNetManageService.assignRecoveryPerson(net, session.getCurrentUser());

            // Optional: View neuladen
            try {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(FacesContext.getCurrentInstance()
                                .getExternalContext()
                                .getRequestContextPath() + "/ghostnet-details.xhtml?net_id=" + net.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void prepareLostMarking() {
        if (session.isLoggedIn()) {
            ghostNetManageService.updateStatus(selectedNet, GhostNetStatus.LOST, session.getCurrentUser());
            selectedNet.setStatus(GhostNetStatus.LOST, session.getCurrentUser());
            try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + "/ghostnet-details.xhtml?net_id=" + selectedNet.getId());
            } catch (IOException e) {
                e.printStackTrace(); // Optional: Logging
            }
        }
    }

    @Transactional
    public void releaseNet() {
        if (selectedNet != null && session.isLoggedIn()) {
            if (selectedNet.getRecoveryPerson() != null &&
                    selectedNet.getRecoveryPerson().getId().equals(session.getCurrentUser().getId())) {

                ghostNetManageService.releaseNet(selectedNet);
                selectedNet.setRecoveryPerson(null);

                try {
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .redirect(FacesContext.getCurrentInstance()
                                    .getExternalContext()
                                    .getRequestContextPath() + "/ghostnet-details.xhtml?net_id=" + selectedNet.getId());
                } catch (IOException e) {
                    e.printStackTrace(); // Logging wäre auch nice
                }
            }
        }
    }



}
