package internal;

import java.util.HashSet;
import java.util.Set;

public class Aktivitet {
    private int bugetteretTid;
    private Projekt projekt;
    private final String navn;
    private UgeDato startDato;
    private UgeDato slutDato;
    private Set<Medarbejder> anførteMedarbjedere;

    public Aktivitet(String navn, Projekt projekt) {
        if (projekt == null) {
            throw new NullPointerException();
        }

        this.navn = navn;
        this.projekt = projekt;
        this.bugetteretTid = 0;
        this.startDato = null;
        this.slutDato = null;
        this.anførteMedarbjedere = new HashSet<Medarbejder>();

        projekt.tilføjAktivitet(this);
    }

    // Per uge ikke for hele projektet
    public int beregnArbejdePerMedarbejder() {
        if (this.startDato == null || this.slutDato == null) {
            return 0;
        }
        return this.bugetteretTid / this.anførteMedarbjedere.size();
    }

    public void tilføjMedarbjeder(Medarbejder medarbejder) {
        if (this.projekt == null) {
            return;
        }

        if (!medarbejder.getProjekter().contains(this.projekt)) {
            return;
        }

        if (this.anførteMedarbjedere.contains(medarbejder)) {
            return;
        }

        this.anførteMedarbjedere.add(medarbejder);
    }

    public void fjernMedarbejder(Medarbejder medarbejder) {
        if (!this.anførteMedarbjedere.contains(medarbejder)) {
            return;
        }

        this.anførteMedarbjedere.remove(medarbejder);
    }

    public void fjernProjekt(Projekt projekt) {
        if (this.projekt != projekt) {
            return;
        }

        this.projekt = null;
    }

    public String getNavn() {
        return navn;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public int getBugetteretTid() {
        return bugetteretTid;
    }

    public void setBugetteretTid(int bugetteretTid) {
        this.bugetteretTid = bugetteretTid;
    }

    public UgeDato getStartDato() {
        return startDato;
    }

    public void setStartDato(UgeDato startDato) {
        this.startDato = startDato;
    }

    public UgeDato getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(UgeDato slutDato) {
        this.slutDato = slutDato;
    }

    public Set<Medarbejder> getAnførteMedarbjedere() {
        return anførteMedarbjedere;
    }
}
