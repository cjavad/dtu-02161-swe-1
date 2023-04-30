package internal;

public class ProjektWrapper {

    public static ProjektWrapper g = new ProjektWrapper();

    public void tilføjMedarbejderTilProjekt(Medarbejder medarbejder, Projekt projekt) {
        if (medarbejder == null || projekt == null) {
            throw new NullPointerException();
        }
        projekt.tilføjMedarbejder(medarbejder);
        medarbejder.tilføjProjekt(projekt);
    }

    public void tilføjMedarbjederTilAktivitet(Medarbejder medarbejder, Aktivitet aktivitet) {
        if (medarbejder == null || aktivitet == null) {
            throw new NullPointerException();
        }
        medarbejder.tilføjAktivitet(aktivitet);
        aktivitet.tilføjMedarbjeder(medarbejder);
    }

    // gjort ind i Aktivitet contructoren, har bruge for projekt object til lave aktivitet
    //    public void tilføjAktivitetTilProjekt(Aktivitet aktivitet, Projekt projekt) {
    //        projekt.tilføjAktivitet(aktivitet);
    //    }

    public void fjernMedarbejderFraProjekt(Medarbejder medarbejder, Projekt projekt) {
        if (medarbejder == null || projekt == null) {
            throw new NullPointerException();
        }
        projekt.fjernMedarbejder(medarbejder);
        medarbejder.fjernProjekt(projekt);
    }

    public void fjernMedarbejderFraAktivitet(Medarbejder medarbejder, Aktivitet aktivitet) {
        if (medarbejder == null || aktivitet == null) {
            throw new NullPointerException();
        }

        medarbejder.fjernAktivitet(aktivitet);
        aktivitet.fjernMedarbejder(medarbejder);
    }

    public void fjernAktivitetFraProjekt(Aktivitet aktivitet, Projekt projekt) {
        if (aktivitet == null || projekt == null) {
            throw new NullPointerException();
        }
        aktivitet.fjernProjekt(projekt);
        projekt.fjernAktivitet(aktivitet);
    }

}
