package internal;

import java.util.HashSet;
import java.util.Set;

public class Aktivitet {

    private final String ID;
    private final String navn;
    private int bugetteretTid;
    private UgeDato startDato;
    private UgeDato slutDato;
    private Set<String> anførteMedarbjedere;

    public Aktivitet(String navn, String projektID) {
        this.navn = navn;
        this.ID = projektID + navn;
        this.bugetteretTid = 0;
        this.startDato = null;
        this.slutDato = null;
        this.anførteMedarbjedere = new HashSet<String>();
    }

    public int beregnArbejdePerMedarbejder() {
        if (this.startDato == null || this.slutDato == null) {
            return 0;
        }
        return this.bugetteretTid / (this.anførteMedarbjedere.size() * (this.slutDato.ugeDiff(startDato)));
    }

    public String getID() {
        return ID;
    }

    public String getNavn() {
        return navn;
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

    public Set<String> getAnførteMedarbjedere() {
        return anførteMedarbjedere;
    }

    public void setAnførteMedarbjedere(Set<String> anførteMedarbjedere) {
        this.anførteMedarbjedere = anførteMedarbjedere;
    }
}
