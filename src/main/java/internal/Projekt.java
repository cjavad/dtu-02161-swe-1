package internal;

import java.util.HashSet;
import java.util.Set;

public class Projekt {

    private final String projektID;
    private String projektLeder;
    private Set<String> medarbejder;
    private Set<String> aktiviteter;

    public Projekt(String projektID) {
        this.projektID = projektID;
        this.projektLeder = null;
        this.medarbejder = new HashSet<String>();
        this.aktiviteter = new HashSet<String>();
    }

    public String getProjektID() {
        return projektID;
    }

    public String getProjektLeder() {
        return projektLeder;
    }

    public void setProjektLeder(String projektLeder) {
        this.projektLeder = projektLeder;
    }

    public Set<String> getMedarbejder() {
        return medarbejder;
    }

    public void setMedarbejder(Set<String> medarbejder) {
        this.medarbejder = medarbejder;
    }

    public Set<String> getAktiviteter() {
        return aktiviteter;
    }

    public void setAktiviteter(Set<String> aktiviteter) {
        this.aktiviteter = aktiviteter;
    }
}
