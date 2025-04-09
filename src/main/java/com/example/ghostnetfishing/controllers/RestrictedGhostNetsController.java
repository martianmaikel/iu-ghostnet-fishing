package com.example.ghostnetfishing.controllers;

import com.example.ghostnetfishing.models.GhostNet;
import com.example.ghostnetfishing.models.GhostNetStatus;
import com.example.ghostnetfishing.models.RecoveryPerson;
import com.example.ghostnetfishing.services.GhostNetManageService;
import com.example.ghostnetfishing.sessions.RecoveryPersonSession;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class RestrictedGhostNetsController implements Serializable {

    public enum ViewMode {
        MY_NETS, OPEN_NETS, IN_RECOVERY_NETS_NOT_ME
    }

    @PersistenceContext
    private EntityManager em;

    @Inject
    private GhostNetManageService ghostNetManageService;

    @Inject
    private RecoveryPersonSession session;

    private ViewMode viewMode = ViewMode.MY_NETS;

    public ViewMode getViewMode() {
        return viewMode;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void refresh() {
    }

    public List<GhostNet> getGhostNets() {
        if (viewMode == ViewMode.MY_NETS && session.isLoggedIn()) {
            return ghostNetManageService.getGhostNets(session.getCurrentUser());
        } else if (viewMode == ViewMode.OPEN_NETS) {
            return ghostNetManageService.getUnassignedGhostNets();
        } else if (viewMode == ViewMode.IN_RECOVERY_NETS_NOT_ME) {
            RecoveryPerson currentUser = session.getCurrentUser();
            return ghostNetManageService.getAssignedGhostNetsExcluding(new RecoveryPerson[]{ currentUser });
        }
        return List.of();
    }
}
