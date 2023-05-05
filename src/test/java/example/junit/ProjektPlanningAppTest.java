package example.junit;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;
import internal.ProjektPlanningApp;
import internal.ProjektWrapper;

public class ProjektPlanningAppTest {

    public ProjektPlanningApp app = new ProjektPlanningApp();

    @org.junit.Test
    public void tilføjMedarbejderTilProjektTest() {
        // null medarbjeder
        {
            Projekt p = new Projekt("2023-01");
            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.tilføjMedarbejderTilProjekt(null, p);
            });
        }
        // null projekt
        {
            Medarbejder m = new Medarbejder("abcd");
            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.tilføjMedarbejderTilProjekt(m, null);
            });
        }
        // non null
        {
            Medarbejder m = new Medarbejder("abcd");
            Projekt p = new Projekt("2023-01");
            app.tilføjMedarbejderTilProjekt(m, p);

            org.junit.Assert.assertTrue(m.getProjekter().contains(p));
            org.junit.Assert.assertTrue(p.getMedarbejder().contains(m));
        }
    }

    @org.junit.Test
    public void tilføjMedarbjederTilAktivitetTest() {
        // null medarbejder
        {
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
               app.tilføjMedarbjederTilAktivitet(null, a);
            });
        }
        // null aktivitet
        {
            Medarbejder m = new Medarbejder("abcd");

            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.tilføjMedarbjederTilAktivitet(m, null);
            });
        }
        // medarbejder not in projekt
        {
            Medarbejder m = new Medarbejder("abcd");
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            app.tilføjMedarbjederTilAktivitet(m, a);

            org.junit.Assert.assertFalse(m.getAnførteAktiviteter().contains(a));
            org.junit.Assert.assertFalse(a.getAnførteMedarbejdere().contains(m));
        }
        // medarbejder in projekt
        {
            Medarbejder m = new Medarbejder("abcd");
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);
            app.tilføjMedarbejderTilProjekt(m, p);

            app.tilføjMedarbjederTilAktivitet(m, a);

            org.junit.Assert.assertTrue(m.getAnførteAktiviteter().contains(a));
            org.junit.Assert.assertTrue(a.getAnførteMedarbejdere().contains(m));
        }
    }

    @org.junit.Test
    public void tilføjAktivitetTilProjektTest() {
        // null projekt
        {
            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                new Aktivitet("ak", null);
            });
        }
        // non null projekt
        {
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            org.junit.Assert.assertTrue(a.getProjekt() == p);
            org.junit.Assert.assertTrue(p.getAktiviteter().contains(a));
        }
    }

    @org.junit.Test
    public void fjernMedarbejderFraProjektTest() {
        // null medarbjeder
        {
            Projekt p = new Projekt("2023-01");
            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.fjernMedarbejderFraProjekt(null, p);
            });
        }
        // null projekt
        {
            Medarbejder m = new Medarbejder("abcd");
            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.fjernMedarbejderFraProjekt(m, null);
            });
        }
        // non null
        {
            Medarbejder m = new Medarbejder("abcd");
            Projekt p = new Projekt("2023-01");

            app.tilføjMedarbejderTilProjekt(m, p);

            app.fjernMedarbejderFraProjekt(m, p);

            org.junit.Assert.assertFalse(m.getProjekter().contains(p));
            org.junit.Assert.assertFalse(p.getMedarbejder().contains(m));
        }
        // not in projekt
        {
            Medarbejder m = new Medarbejder("abcd");
            Projekt p = new Projekt("2023-01");

            app.fjernMedarbejderFraProjekt(m, p);

            org.junit.Assert.assertFalse(m.getProjekter().contains(p));
            org.junit.Assert.assertFalse(p.getMedarbejder().contains(m));
        }

        // TODO :: with aktivities
    }

    @org.junit.Test
    public void fjernMedarbejderFraAktivitetTest() {
        // null medarbejder
        {
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.fjernMedarbejderFraAktivitet(null, a);
            });
        }
        // null aktivitet
        {
            Medarbejder m = new Medarbejder("abcd");

            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.fjernMedarbejderFraAktivitet(m, null);
            });
        }
        // normal
        {
            Medarbejder m = new Medarbejder("abcd");
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            app.tilføjMedarbejderTilProjekt(m, p);
            app.tilføjMedarbjederTilAktivitet(m, a);

            app.fjernMedarbejderFraAktivitet(m, a);

            org.junit.Assert.assertFalse(m.getAnførteAktiviteter().contains(a));
            org.junit.Assert.assertFalse(a.getAnførteMedarbejdere().contains(m));
        }
    }

    @org.junit.Test
    public void fjernAktivitetFraProjekt() {
        // null aktivitet
        {
            Projekt p = new Projekt("2023-01");

            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.fjernAktivitetFraProjekt(null, p);
            });
        }
        // null projekt
        {
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            org.junit.Assert.assertThrows(NullPointerException.class, () -> {
                app.fjernAktivitetFraProjekt(a, null);
            });
        }
        // aktivitet in different projekt
        {
            Projekt p1 = new Projekt("2023-01");
            Projekt p2 = new Projekt("2023-02");
            Aktivitet a = new Aktivitet("ak", p2);

            app.fjernAktivitetFraProjekt(a, p1);

            org.junit.Assert.assertTrue(a.getProjekt() == p2);
            org.junit.Assert.assertTrue(p2.getAktiviteter().contains(a));
            org.junit.Assert.assertFalse(p1.getAktiviteter().contains(a));
        }
        // normal
        {
            Projekt p = new Projekt("2023-01");
            Aktivitet a = new Aktivitet("ak", p);

            app.fjernAktivitetFraProjekt(a, p);

            org.junit.Assert.assertFalse(a.getProjekt() == p);
            org.junit.Assert.assertFalse(p.getAktiviteter().contains(a));
        }
    }

}
