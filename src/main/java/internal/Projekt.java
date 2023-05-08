package internal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Projekt implements Serializable {
    private String navn;
    private final String projektID;
    private Medarbejder projektLeder;
    private Set<Medarbejder> medarbejder;
    private Set<Aktivitet> aktiviteter;

    public Projekt(String navn, String projektID) {
        this.navn = navn;
        this.projektID = projektID;
        this.projektLeder = null;
        this.medarbejder = new HashSet<Medarbejder>();
        this.aktiviteter = new HashSet<Aktivitet>();
    }

    public void ændreProjektleder(Medarbejder medarbejder) {
        this.setProjektLeder(medarbejder);

        if (this.getProjektLeder() != null) {
            this.getProjektLeder().fjernProjektleder(this);
        }

        if (medarbejder != null) {
            medarbejder.tilføjProjektleder(this);
        }
    }

    public void tilføjAktivitetTilProjekt(Aktivitet aktivitet) {
        assert medarbejder != null;
        this.tilføjAktivitet(aktivitet);
        aktivitet.tilføjProjekt(this);
    }

    public void fjernAktivitetFraProjekt(Aktivitet aktivitet) {
        if (aktivitet == null) {
            throw new NullPointerException();
        }
        this.fjernAktivitet(aktivitet);
        aktivitet.fjernProjekt(this);
    }

    public void tilføjMedarbejderTilProjekt(Medarbejder medarbejder) {
        if (medarbejder == null) {
            throw new NullPointerException();
        }

        medarbejder.tilføjProjekt(this);
        this.tilføjMedarbejder(medarbejder);
    }


    public void fjernMedarbejderFraProjekt(Medarbejder medarbejder) {
        assert medarbejder != null;
        medarbejder.fjernProjekt(this); // 2
        this.fjernMedarbejder(medarbejder); // 3
    }

    public void tilføjMedarbejder(Medarbejder medarbejder) {
		// NOTE: hvorfor laver vi detter tjek? 
		// altså, det er et hashset så dubletter kan ikke eksistere
		//
		// sammen kommentar gælder for alle lignende metoder under
        if (this.medarbejder.contains(medarbejder)) {
            return;
        }
        this.medarbejder.add(medarbejder);
    }

    public void tilføjAktivitet(Aktivitet aktivitet) {
        for (Aktivitet a : this.aktiviteter) {
            if (a.getNavn().contains(aktivitet.getNavn())) return;
        }
        this.aktiviteter.add(aktivitet);
    }

    public void fjernMedarbejder(Medarbejder medarbejder) {
        if (!this.medarbejder.contains(medarbejder)) {
            return;
        }

        if (this.projektLeder == medarbejder) {
            this.projektLeder = null;
        }

        this.medarbejder.remove(medarbejder);

        this.aktiviteter.stream()
                .forEach(a -> a.fjernMedarbejder(medarbejder));
    }

    public void fjernAktivitet(Aktivitet aktivitet) {
        if (!this.aktiviteter.contains(aktivitet)) {
            return;
        }
        this.aktiviteter.remove(aktivitet);

        aktivitet.getAnførteMedarbejdere().stream()
                .forEach(m -> m.fjernAktivitet(aktivitet));
    }

    public String getProjektID() {
        return projektID;
    }

    public Medarbejder getProjektLeder() {
        return projektLeder;
    }

    public void setProjektLeder(Medarbejder projektLeder) {
        if (this.projektLeder != null) this.projektLeder.getProjektLederFor().remove(this);
        if (projektLeder != null) projektLeder.getProjektLederFor().add(this);
        this.projektLeder = projektLeder;
    }

	// FIXME: burde det ikke være getMedarbejdere altså flertal?
    public Set<Medarbejder> getMedarbejder() {
        return medarbejder;
    }

    public Set<Aktivitet> getAktiviteter() {
        return aktiviteter;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Aktivitet findAktivitet(String navn) {
        for (Aktivitet aktivitet : this.aktiviteter) {
            if (aktivitet.getNavn().equals(navn)) return aktivitet;
        }

        return null;
    }

    public Medarbejder findMedarbejder(String initial) {
        for (Medarbejder medarbejder : this.medarbejder) {
            if (medarbejder.getInitial().equals(initial)) return medarbejder;
        }

        return null;
    }
}
