package com.example.ghostnetfishing.services;

import com.example.ghostnetfishing.models.RecoveryPerson;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

@Stateless
public class AuthRecoveryPersonService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public RecoveryPerson registerRecoveryPerson(String name, String email, String rawPassword) {
        com.example.ghostnetfishing.models.RecoveryPerson person = new RecoveryPerson();
        person.setName(name);
        person.setEmail(email);
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        person.setPassword(hashed);
        em.persist(person);
        return person;
    }
    public RecoveryPerson registerRecoveryPerson(String name, String email, String rawPassword, String phoneNumber) {
        com.example.ghostnetfishing.models.RecoveryPerson person = new RecoveryPerson();
        person.setName(name);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber);
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        person.setPassword(hashed);
        em.persist(person);
        return person;
    }
}
