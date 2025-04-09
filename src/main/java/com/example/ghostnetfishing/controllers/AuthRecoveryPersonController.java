package com.example.ghostnetfishing.controllers;

import com.example.ghostnetfishing.models.RecoveryPerson;
import com.example.ghostnetfishing.services.AuthRecoveryPersonService;
import com.example.ghostnetfishing.sessions.RecoveryPersonSession;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AuthRecoveryPersonController implements Serializable {

    private String email;
    private String password;
    private String phoneNumber;
    private RecoveryPerson currentUser;
    @Inject
    private RecoveryPersonSession session;

    @PersistenceContext
    private EntityManager em;

    private RecoveryPerson newUser = new RecoveryPerson();

    @Inject
    private AuthRecoveryPersonService authRecoveryPersonService;

    public String login() {
        List<RecoveryPerson> result = em.createQuery(
                        "SELECT r FROM RecoveryPerson r WHERE r.email = :email", RecoveryPerson.class)
                .setParameter("email", email)
                .getResultList();

        if (!result.isEmpty()) {
            RecoveryPerson user = result.get(0);

            if (BCrypt.checkpw(password, user.getPassword())) {
                session.setCurrentUser(user);
                return "/restricted/dashboard.xhtml?faces-redirect=true";
            }
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login fehlgeschlagen", "E-Mail oder Passwort ist ungültig."));

        return null;
    }


    @Transactional
    public String register() {
            authRecoveryPersonService.registerRecoveryPerson(
                    newUser.getName(), newUser.getEmail(), newUser.getPassword(), newUser.getPhoneNumber()
            );
            return "/login.xhtml?faces-redirect=true";
    }


    public String logout() {
        session.logout();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }


    public Boolean getLoggedIn() {
        return currentUser != null;
    }

    // Getter & Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public RecoveryPerson getCurrentUser() { return currentUser; }

    public RecoveryPerson getNewUser() { return newUser; }
    public void setNewUser(RecoveryPerson newUser) { this.newUser = newUser; }
}
