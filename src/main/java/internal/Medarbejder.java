package internal;

import java.util.*;
import javafx.util.Pair;

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


        if(start == null || slut == null){ //0
            // Empty list
            return new ArrayList<>();
        }

        // Length of the period [start,slut]>=1
        List<Integer> fritid = new ArrayList<>(Collections.nCopies(slut.ugeDiff(start), this.ugentligeTimer));


        this.anførteAktiviteter.forEach((aktivitet) -> { //1
            beregnFritidPerAktivitet(start, slut, fritid, aktivitet);
        });


        return fritid;
    }

    private void beregnFritidPerAktivitet(UgeDato start, UgeDato slut, List<Integer> fritid, Aktivitet aktivitet) {

        if (aktivitet.getStartDato() == null || aktivitet.getSlutDato() == null) return; //0

        int startIndex, endIndex;

        //Giver altid min. 0, da ugeDiff(ugeDato)>=1
        startIndex = aktivitet.getStartDato().ugeDiff(start) - 1;
        endIndex = Math.min(aktivitet.getStartDato().ugeDiff(slut), start.ugeDiff(slut));

        for (int i = startIndex; i < endIndex; i++) { //2
            fritid.set(i, fritid.get(i) - aktivitet.beregnArbejdePerMedarbejder()); //2a
        }
    }

    /**
     *
     * Precondition: Medarbejderen tilhører samme projekt som aktiviteten
     * Postcondition: Der eksisterer nu én refference til aktiviteten i medarbejderens anførteAktiviteter
     */
    public void tilføjAktivitet(Aktivitet aktivitet) {
        assert (iSammeProjektSomAktivitet(aktivitet));

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


    private Boolean iSammeProjektSomAktivitet(Aktivitet aktivitet){

        return aktivitet.iSammeProjektSomMedarbejder(this);
    }

    public int forekomsterAfAktivitet(Aktivitet a){
        Iterator<Aktivitet> aktivitetIterator = this.anførteAktiviteter.iterator();
        int forekomster = 0;
        boolean elementerTilbage = aktivitetIterator.hasNext();

        while(elementerTilbage)
            if(aktivitetIterator.next() == a){
                forekomster++;
            }

        elementerTilbage = aktivitetIterator.hasNext();

        return forekomster;

    }
}
