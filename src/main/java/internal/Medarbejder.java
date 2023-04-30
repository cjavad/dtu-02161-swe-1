package internal;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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

    // TODO :: start / slut uge input

    /**
     * @param datoer: Indeholder en start og slut dato for perioden
     * @return En int stream,
     */
    public List<Integer> beregnFritidForPeriode(Pair<UgeDato, UgeDato> datoer) {

        UgeDato start = datoer.getKey();
        UgeDato slut = datoer.getValue();

        if(start == null || slut == null){
            // Empty list
            return new ArrayList<>();
        }

        // Init empty arraylist.
        List<Integer> fritid = new ArrayList<>(Collections.nCopies(slut.ugeDiff(start), 0));


        this.anførteAktiviteter.forEach((aktivitet) -> {
            if (aktivitet.getStartDato() == null || aktivitet.getSlutDato() == null) return;

            int startIndex, endIndex;

            startIndex = Math.max(aktivitet.getStartDato().ugeDiff(start), 0) - 1;
            endIndex = Math.min(aktivitet.getStartDato().ugeDiff(slut), start.ugeDiff(slut));

            for (int i = startIndex; i < endIndex; i++) {
                fritid.set(i, fritid.get(i) + aktivitet.beregnArbejdePerMedarbejder());
            }
        });

        for (int i = 0; i < fritid.size(); i++) {
            fritid.set(i, this.ugentligeTimer - fritid.get(i));
        }

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

    public String toString() {
        return initial;
    }
}
