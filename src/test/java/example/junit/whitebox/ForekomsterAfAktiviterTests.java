package example.junit.whitebox;

import internal.*;
import io.cucumber.java.ht.Ak;

import java.util.Arrays;
import java.util.List;

public class ForekomsterAfAktiviterTests {
    ProjektPlanningApp app = new ProjektPlanningApp();
    Projekt projekt = new Projekt("Projekt", "01-2023");
    List<Aktivitet> aktiviteter = Arrays.asList(new Aktivitet("a"),
            new Aktivitet("b"),  new Aktivitet("c"));

    Medarbejder m;

    @org.junit.Before()
    public void setup() {
        app.tilføjAktivitetTilProjekt(aktiviteter.get(0), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(1), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(2), projekt);
    }

    @org.junit.Test()
    public void inputsetA() {   
        m = new Medarbejder("m");
        org.junit.Assert.assertThrows(AssertionError.class, () -> m.forekomsterAfAktivitet(null));
    }

    @org.junit.Test()
    public void inputsetB() {
        m = new Medarbejder("m");
        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));
        org.junit.Assert.assertEquals(1, m.forekomsterAfAktivitet(aktiviteter.get(0)));
    }

    @org.junit.Test()
    public void inputsetC() {
        m = new Medarbejder("m");
        app.tilføjMedarbejderTilProjekt(m, projekt);
        org.junit.Assert.assertEquals(0, m.forekomsterAfAktivitet(aktiviteter.get(0)));
    }

    @org.junit.Test()
    public void inputsetD() {
        m = new Medarbejder("m");
        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(1));
        org.junit.Assert.assertEquals(0, m.forekomsterAfAktivitet(aktiviteter.get(0)));
    }

    @org.junit.Test()
    public void inputsetE() {
        m = new Medarbejder("m");
        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(1));
        org.junit.Assert.assertEquals(1, m.forekomsterAfAktivitet(aktiviteter.get(0)));
    }

    @org.junit.Test()
    public void inputsetF() {
        m = new Medarbejder("m");
        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(2));
        org.junit.Assert.assertEquals(0, m.forekomsterAfAktivitet(aktiviteter.get(1)));
    }
}
