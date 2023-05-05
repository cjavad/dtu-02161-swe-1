package internal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Medarbejder {

    private final String initial;
    private Set<Projekt> projekter;
    private Set<Projekt> projektLederFor;
    private Set<Aktivitet> anførteAktiviteter;
    private int ugentligeTimer;

    public Medarbejder(String initial) {
        this.initial = initial;
        this.projekter = new HashSet<Projekt>();
        this.projektLederFor = new HashSet<Projekt>();
        this.anførteAktiviteter = new HashSet<Aktivitet>();
        this.ugentligeTimer = 0;
    }

    public void tilføjMedarbejderTilProjekt(Projekt projekt) {
        if (projekt == null) {
            throw new NullPointerException();
        }
        projekt.tilføjMedarbejder(this);
        this.tilføjProjekt(projekt);
    }

    public void fjernMedarbejderFraProjekt(Projekt projekt) {
        if (projekt == null) {
            throw new NullPointerException();
        }
        projekt.fjernMedarbejder(this);
        this.fjernProjekt(projekt);
    }

    public void tilføjMedarbjederTilAktivitet(Aktivitet aktivitet) {
        if (aktivitet == null) {
            throw new NullPointerException();
        }
        this.tilføjAktivitet(aktivitet);
        aktivitet.tilføjMedarbjeder(this);
    }

    public void fjernMedarbejderFraAktivitet(Aktivitet aktivitet) {
        if (aktivitet == null) {
            throw new NullPointerException();
        }
        this.fjernAktivitet(aktivitet);
        aktivitet.fjernMedarbejder(this);
    }


    // TODO :: start / slut uge input

    /**
     *
     * @param start
     * @param slut
     * @return
     */
    public int[] beregnFritid(UgeDato start, UgeDato slut) {
        assert start != null;
        assert slut != null;

        int[] fritid = new int[slut.ugeDiff(start)];

        // fjern aktiviteter der ikke har start eller slut dato
        this.anførteAktiviteter.stream()
                .filter(a -> a.getStartDato() != null && a.getSlutDato() != null)
                .forEach((aktivitet) -> {
            int startIndex, endIndex;

            startIndex = Math.max(aktivitet.getStartDato().ugeDiff(start), 0);
            endIndex = Math.min(aktivitet.getStartDato().ugeDiff(slut), start.ugeDiff(slut));

            for (int i = startIndex; i < endIndex; i++) {
                fritid[i] += aktivitet.beregnArbejdePerMedarbejder();
            }

        });

        return fritid;
    }

    public void tilføjAktivitet(Aktivitet aktivitet) {
        if (!aktivitet.getProjekt().getMedarbejder().contains(this)) {
            return; // TODO :: fejl
        }
        if (this.anførteAktiviteter.contains(aktivitet)) {
            return;
        }
        this.anførteAktiviteter.add(aktivitet);
    }

    public void tilføjProjekt(Projekt projekt) {
        if (this.projekter.contains(projekt)) {
            return;
        }
        this.projekter.add(projekt);
    }

    public void fjernAktivitet(Aktivitet aktivitet) {
        if (!this.anførteAktiviteter.contains(aktivitet)) {
            return;
        }
        this.anførteAktiviteter.remove(aktivitet);
    }

    public void fjernProjekt(Projekt projekt) {
        if (!this.projekter.contains(projekt)) {
            return;
        }

        if (this.projektLederFor.contains(projekt)) {
            this.projektLederFor.remove(projekt);
        }
        this.projekter.remove(projekt);

        this.anførteAktiviteter.stream()
                .filter(a -> a.getProjekt() == projekt)
                .forEach(a -> this.anførteAktiviteter.remove(a));
    }

    public String getInitial() {
        return initial;
    }

    public Set<Projekt> getProjekter() {
        return projekter;
    }

    public Set<Projekt> getProjektLederFor() {
        return projektLederFor;
    }

    public Set<Aktivitet> getAnførteAktiviteter() {
        return anførteAktiviteter;
    }

    public int getUgentligeTimer() {
        return ugentligeTimer;
    }

    public void setUgentligeTimer(int ugentligeTimer) {
        this.ugentligeTimer = ugentligeTimer;
    }
}
