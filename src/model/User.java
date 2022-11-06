package model;

import java.time.LocalDate;

public abstract class User {

    private String documentId;

    private LocalDate joiningDate;

    public User(String documentId) {
        this.documentId = documentId;
        this.joiningDate = LocalDate.now();
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        return "User{" +
                "joiningDate=" + joiningDate +
                '}';
    }
}
