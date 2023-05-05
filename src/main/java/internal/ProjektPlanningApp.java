package internal;

public class ProjektPlanningApp {

    // gjort ind i Aktivitet contructoren, har bruge for projekt object til lave aktivitet
//    public void tilføjAktivitetTilProjekt(Aktivitet aktivitet, Projekt projekt) {
//        projekt.tilføjAktivitet(aktivitet);
//    }


    public void tilføjMedarbejderTilProjekt(Medarbejder m, Projekt p) {
        if (p == null) {
            throw new NullPointerException();
        }
        p.tilføjMedarbejderTilProjekt(m);
    }

    /**
     * Precondition: Medarbejderen er ikke null, aktiviteten er ikke null, aktiviteten tilhører et projekt, medarbejderen tilhører samme projekt som aktiviteten
     * Postcondition: Der eksisterer nu én refference til aktiviteten i medarbejderens anførteAktiviteter
     */
    public void tilføjMedarbjederTilAktivitet(Medarbejder m, Aktivitet a) {
        assert(a != null && m != null && a.iSammeProjektSomMedarbejder(m));
        a.tilføjMedarbjederTilAktivitet(m);
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
}
