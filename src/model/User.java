package model;
import java.time.LocalDate;

public abstract class User {
    private LocalDate joiningDate;

    public User() {
        this.joiningDate = LocalDate.now();
    }

    // GETTERS AND SETTERS

    public LocalDate getJoiningDate() {
        return joiningDate;
    }
    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "joiningDate=" + joiningDate +
                '}';
    }
}
