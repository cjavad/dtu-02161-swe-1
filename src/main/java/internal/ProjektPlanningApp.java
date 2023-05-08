package internal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class ProjektPlanningApp implements Serializable {


    public Set<Projekt> projekter;
    public Set<Medarbejder> medarbejdere;

    public ProjektPlanningApp(){

        this.projekter = new HashSet<Projekt>();
        this.medarbejdere = new HashSet<Medarbejder>();

    }

    public void ændreProjektleder(Projekt p, Medarbejder m) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.ændreProjektleder(m);
    }

    public void tilføjMedarbejderTilProjekt(Medarbejder m, Projekt p) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.tilføjMedarbejderTilProjekt(m);
    }

    /**
     * Precondition: Medarbejderen er ikke null, aktiviteten er ikke null, aktiviteten tilhører et projekt, medarbejderen tilhører samme projekt som aktiviteten
     * Postcondition: Der eksisterer nu én reference til aktiviteten i medarbejderens anførteAktiviteter og én reference til medarbejderen i aktivitetens anførteMedarbejdere
     */
    public void tilføjMedarbjederTilAktivitet(Medarbejder m, Aktivitet a) {
        assert(a != null && m != null && a.iSammeProjektSomMedarbejder(m));

        a.tilføjMedarbjederTilAktivitet(m);

        assert(a.forekomsterAfMedarbejder(m) == 1 && m.forekomsterAfAktivitet(a) == 1);
    }

    //
    public boolean postconditionFjernMedarbejderFraProjekt(Medarbejder medarbejder,Projekt projekt){
        boolean condition1 = medarbejder.getAnførteAktiviteter().stream().noneMatch(a -> a.getProjekt() == projekt);
        boolean condition2 = projekt.getAktiviteter().stream().noneMatch(a -> a.getAnførteMedarbejdere().contains(medarbejder));
        boolean condition3 = !medarbejder.getProjekter().contains(projekt);
        boolean condition4 = !projekt.getMedarbejder().contains(medarbejder);

        return condition1 && condition2 && condition3 && condition4;

    }

    public void fjernMedarbejderFraProjekt(Medarbejder m, Projekt p) {
        assert(p != null && m != null && m.getProjekter().contains(p) && p.getMedarbejder().contains(m)); //precondition

        p.fjernMedarbejderFraProjekt(m);

        assert postconditionFjernMedarbejderFraProjekt(m,p); //1
    }

    public void fjernMedarbejderFraAktivitet(Medarbejder m, Aktivitet a) {
        if (a == null) {
            throw new NullPointerException();
        }
        a.fjernMedarbejderFraAktivitet(m);
    }

    public void tilføjAktivitetTilProjekt(Aktivitet a, Projekt p) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.tilføjAktivitetTilProjekt(a);
    }

    public void fjernAktivitetFraProjekt(Aktivitet a, Projekt p) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.fjernAktivitetFraProjekt(a);
    }

    public Set<Projekt> fåProjekter() {
        return projekter;
    }

    public Set<Medarbejder> fåMedarbejdere() {
        return medarbejdere;
    }
}
