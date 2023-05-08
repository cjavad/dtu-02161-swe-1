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


    public void fjernMedarbejderFraProjekt(Medarbejder m, Projekt p) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.fjernMedarbejderFraProjekt(m);
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
