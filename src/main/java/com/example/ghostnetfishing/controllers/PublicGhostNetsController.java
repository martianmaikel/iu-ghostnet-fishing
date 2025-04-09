package com.example.ghostnetfishing.controllers;

import com.example.ghostnetfishing.models.GhostNet;
import com.example.ghostnetfishing.models.GhostNetStatus;
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
public class PublicGhostNetsController implements Serializable {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private GhostNetManageService ghostNetManageService;


    private GhostNetStatus filterGhostNetStatus;

    public List<GhostNet> getGhostNets() {
        if(filterGhostNetStatus != null) {
            return ghostNetManageService.getGhostNets(filterGhostNetStatus);
        }
        return ghostNetManageService.getGhostNets();
    }


}
