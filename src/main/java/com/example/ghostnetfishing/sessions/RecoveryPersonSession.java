package com.example.ghostnetfishing.sessions;

import com.example.ghostnetfishing.models.RecoveryPerson;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class RecoveryPersonSession implements Serializable {

    private RecoveryPerson currentUser;

    public RecoveryPerson getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(RecoveryPerson currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }
}
