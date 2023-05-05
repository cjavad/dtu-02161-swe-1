package internal;

public class ProjektPlanningApp {

    // gjort ind i Aktivitet contructoren, har bruge for projekt object til lave aktivitet
//    public void tilføjAktivitetTilProjekt(Aktivitet aktivitet, Projekt projekt) {
//        projekt.tilføjAktivitet(aktivitet);
//    }


    public void tilføjMedarbejderTilProjekt(Medarbejder m, Projekt p) {
        if (m == null) {
            throw new NullPointerException();
        }
        m.tilføjMedarbejderTilProjekt(p);
    }

    public void tilføjMedarbjederTilAktivitet(Medarbejder m, Aktivitet a) {
        if (m == null) {
            throw new NullPointerException();
        }
        m.tilføjMedarbjederTilAktivitet(a);
    }

    public void fjernMedarbejderFraProjekt(Medarbejder m, Projekt p) {
        if (m == null) {
            throw new NullPointerException();
        }
        m.fjernMedarbejderFraProjekt(p);
    }

    public void fjernMedarbejderFraAktivitet(Medarbejder m, Aktivitet a) {
        if (m == null) {
            throw new NullPointerException();
        }
        m.fjernMedarbejderFraAktivitet(a);
    }

    public void fjernAktivitetFraProjekt(Aktivitet a, Projekt p) {
        if (a == null) {
            throw new NullPointerException();
        }
        a.fjernAktivitetFraProjekt(p);
    }
}
