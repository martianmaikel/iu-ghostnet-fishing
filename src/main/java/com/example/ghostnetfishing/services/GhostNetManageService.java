package com.example.ghostnetfishing.services;

import com.example.ghostnetfishing.models.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Named
public class GhostNetManageService implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public GhostNet getGhostNetById(Long id) {
        try {
            return em.createQuery("SELECT g FROM GhostNet g LEFT JOIN FETCH g.log WHERE g.id = :id", GhostNet.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<GhostNet> getGhostNets() {
        return em.createQuery(
                        "SELECT g FROM GhostNet g ORDER BY g.reportedAt DESC", GhostNet.class)
                .getResultList();
    }

    public List<GhostNet> getGhostNets(GhostNetStatus status) {
        return em.createQuery(
                        "SELECT g FROM GhostNet g WHERE g.status = :status ORDER BY g.reportedAt DESC", GhostNet.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<GhostNet> getGhostNets(RecoveryPerson recoveryPerson) {
        return em.createQuery(
                        "SELECT g FROM GhostNet g WHERE g.recoveryPerson = :user ORDER BY g.reportedAt DESC", GhostNet.class)
                .setParameter("user", recoveryPerson)
                .getResultList();
    }

    public List<GhostNet> getAssignedGhostNetsExcluding(RecoveryPerson[] ignorePersons) {
        return em.createQuery(
                        "SELECT g FROM GhostNet g " +
                                "WHERE g.status = :status " +
                                "AND g.recoveryPerson IS NOT NULL " +
                                "AND g.recoveryPerson NOT IN :ignoreList " +
                                "ORDER BY g.reportedAt DESC", GhostNet.class)
                .setParameter("status", GhostNetStatus.ASSIGNED)
                .setParameter("ignoreList", List.of(ignorePersons))
                .getResultList();
    }

    public List<GhostNet> getGhostNets(RecoveryPerson recoveryPerson, GhostNetStatus status) {
        return em.createQuery(
                        "SELECT g FROM GhostNet g WHERE g.status = :status AND g.recoveryPerson = :user ORDER BY g.reportedAt DESC", GhostNet.class)
                .setParameter("status", status)
                .setParameter("user", recoveryPerson)
                .getResultList();
    }

    public List<GhostNet> getUnassignedGhostNets() {
        return em.createQuery(
                        "SELECT g FROM GhostNet g WHERE g.recoveryPerson IS NULL ORDER BY g.reportedAt DESC", GhostNet.class)
                .getResultList();
    }

    @Transactional
    public void assignRecoveryPerson(GhostNet net, RecoveryPerson person) {
        net.setRecoveryPerson(person);
        net.setStatus(GhostNetStatus.ASSIGNED, person); // Optional: Loggen
        em.merge(net);
    }

    public void updateStatus(GhostNet net, GhostNetStatus newStatus) {
        net.setStatus(newStatus);
        em.merge(net);
    }

    public void updateStatus(GhostNet net, GhostNetStatus newStatus, Person fromPerson) {
        net.setStatus(newStatus, fromPerson);
        em.merge(net);
    }

    public GhostNet assignGhostNetToUser(GhostNet net, RecoveryPerson recoveryPerson) {
        net.setRecoveryPerson(recoveryPerson);
        return em.merge(net);
    }

    @Transactional
    public void saveNewReportedNet(GhostNet net) {
        if (net.getReportingPerson() != null) {
            em.persist(net.getReportingPerson());
        }
        em.persist(net);
    }


}
