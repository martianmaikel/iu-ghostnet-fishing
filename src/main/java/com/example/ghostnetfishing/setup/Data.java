package com.example.ghostnetfishing.setup;

import com.example.ghostnetfishing.models.*;
import com.example.ghostnetfishing.services.AuthRecoveryPersonService;
import com.example.ghostnetfishing.services.GhostNetManageService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Singleton
@Startup
public class Data {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AuthRecoveryPersonService authRecoveryPersonService;

    @Inject
    private GhostNetManageService ghostNetManageService;

    @PostConstruct
    @Transactional
    public void init() {
        // Personen
        ReportingPerson anna = new ReportingPerson("0151 23456789", "Anna Fischer");
        ReportingPerson tom = new ReportingPerson("0170 9876543", "Tom Meier");
        ReportingPerson lisa = new ReportingPerson("0160 2223344", "Lisa Brandt");


        RecoveryPerson tauchergruppe = authRecoveryPersonService.registerRecoveryPerson(
                "Tauchergruppe Nord", "taucher@nord.de", "test123", "12122234343"
        );

        RecoveryPerson freetauchteam = authRecoveryPersonService.registerRecoveryPerson(
                "FreeDive Team Baltic", "freedive@baltic.org", "test321", "12434343434"
        );

        // Netz 1
        GhostNet net1 = new GhostNet(
                null,
                "Ostsee nahe Rügen",
                54.4750,
                13.6000,
                120.0,
                GhostNetStatus.REPORTED,
                anna,
                null
        );

        // Netz 2
        GhostNet net2 = new GhostNet(
                null,
                "Nordsee bei Helgoland",
                54.1825,
                7.8886,
                75.0,
                GhostNetStatus.ASSIGNED,
                tom,
                tauchergruppe
        );

        // Netz 3
        GhostNet net3 = new GhostNet(
                null,
                "Bodensee, Nähe Konstanz",
                47.6597,
                9.1756,
                42.0,
                GhostNetStatus.REPORTED,
                lisa,
                null
        );

        // Netz 4
        GhostNet net4 = new GhostNet(
                null,
                "Ostsee vor Kiel",
                54.3275,
                10.1406,
                98.0,
                GhostNetStatus.RECOVERED,
                anna,
                freetauchteam
        );

        // Netz 5
        GhostNet net5 = new GhostNet(
                null,
                "Nordsee bei Borkum",
                53.5950,
                6.7500,
                160.0,
                GhostNetStatus.REPORTED,
                tom,
                tauchergruppe
        );



        // Persistieren
        em.persist(anna);
        em.persist(tom);
        em.persist(lisa);
        em.persist(tauchergruppe);
        em.persist(freetauchteam);


        em.persist(net1);
        em.persist(net2);
        em.persist(net3);
        em.persist(net4);
        em.persist(net5);
        ghostNetManageService.updateStatus(net5, GhostNetStatus.LOST, tom);
    }
}
