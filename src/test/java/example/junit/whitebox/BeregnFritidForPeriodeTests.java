package example.junit.whitebox;

import internal.*;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class BeregnFritidForPeriodeTests {
    Pair<UgeDato, UgeDato> datoer = new Pair<>(new UgeDato(2023, 1), new UgeDato(2023, 2));
    ProjektPlanningApp app = new ProjektPlanningApp();
    Projekt projekt = new Projekt("Projekt 1", "01-2023");
    List<Aktivitet> aktiviteter = Arrays.asList(new Aktivitet("a"),
            new Aktivitet("b"),
            new Aktivitet("c"));

    Medarbejder m;


    @org.junit.Before()
    public void setup() {
        // Ingen budgetteret tid
        aktiviteter.get(0).setBugetteretTid(0);
        aktiviteter.get(0).setStartDato(new UgeDato(2023, 1));
        aktiviteter.get(0).setSlutDato(new UgeDato(2023, 1));

        // Time (null, null)
        aktiviteter.get(1).setBugetteretTid(1);

        aktiviteter.get(2).setBugetteretTid(1);
        aktiviteter.get(2).setStartDato(new UgeDato(2023, 1));
        aktiviteter.get(2).setSlutDato(new UgeDato(2023, 2));

        app.tilføjAktivitetTilProjekt(aktiviteter.get(0), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(1), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(2), projekt);
    }

    @org.junit.Test()
    public void inputsetA1() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);

        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));

        List<Integer> fritid = m.beregnFritidForPeriode(new Pair<>(null, datoer.getValue()));

        org.junit.Assert.assertEquals(0, fritid.size());
    }

    @org.junit.Test()
    public void inputsetA2() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);

        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));

        List<Integer> fritid = m.beregnFritidForPeriode(new Pair<>(datoer.getKey(), null));

        org.junit.Assert.assertEquals(0, fritid.size());
    }

    @org.junit.Test()
    public void inputsetA3() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);

        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));

        List<Integer> fritid = m.beregnFritidForPeriode(new Pair<>(null, null));

        org.junit.Assert.assertEquals(0, fritid.size());
    }

    @org.junit.Test()
    public void inputsetB1() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);

        app.tilføjMedarbejderTilProjekt(m, projekt);

        List<Integer> fritid = m.beregnFritidForPeriode(new Pair<>(datoer.getKey(), new UgeDato(2023, 1)));
        org.junit.Assert.assertEquals(1, fritid.size());
        org.junit.Assert.assertEquals(m.getUgentligeTimer(), fritid.get(0).intValue());
    }

    @org.junit.Test()
    public void inputsetB2() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);

        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(0), projekt);
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));

        List<Integer> fritid = m.beregnFritidForPeriode(datoer);
        org.junit.Assert.assertEquals(2, fritid.size());
        org.junit.Assert.assertEquals(m.getUgentligeTimer(), fritid.get(0).intValue());
        org.junit.Assert.assertEquals(m.getUgentligeTimer(), fritid.get(1).intValue());
    }

    @org.junit.Test()
    public void inputsetB3() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);

        app.tilføjMedarbejderTilProjekt(m, projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(0), projekt);
        aktiviteter.get(1).setBugetteretTid(0);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(1), projekt);

        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(0));
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(1));

        List<Integer> fritid = m.beregnFritidForPeriode(datoer);
        org.junit.Assert.assertEquals(2, fritid.size());
        org.junit.Assert.assertEquals(m.getUgentligeTimer(), fritid.get(0).intValue());
        org.junit.Assert.assertEquals(m.getUgentligeTimer(), fritid.get(1).intValue());
    }

    @org.junit.Test()
    public void inputsetC() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);
        app.tilføjMedarbejderTilProjekt(m, projekt);

        app.tilføjAktivitetTilProjekt(aktiviteter.get(1), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(2), projekt);

        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(1));
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(2));

        List<Integer> fritid = m.beregnFritidForPeriode(datoer);
        org.junit.Assert.assertEquals(2, fritid.size());
        org.junit.Assert.assertEquals(m.getUgentligeTimer() - 1, fritid.get(0).intValue());
        org.junit.Assert.assertEquals(m.getUgentligeTimer() - 1, fritid.get(1).intValue());
    }

    @org.junit.Test()
    public void inputsetD() {
        m = new Medarbejder("m");
        m.setUgentligeTimer(37);
        app.tilføjMedarbejderTilProjekt(m, projekt);

        aktiviteter.get(1).setStartDato(new UgeDato(2023, 1));
        aktiviteter.get(1).setSlutDato(new UgeDato(2023, 2));

        app.tilføjAktivitetTilProjekt(aktiviteter.get(1), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(2), projekt);

        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(1));
        app.tilføjMedarbjederTilAktivitet(m, aktiviteter.get(2));

        List<Integer> fritid = m.beregnFritidForPeriode(datoer);
        org.junit.Assert.assertEquals(2, fritid.size());
        org.junit.Assert.assertEquals(m.getUgentligeTimer() - 2, fritid.get(0).intValue());
        org.junit.Assert.assertEquals(m.getUgentligeTimer() - 2, fritid.get(1).intValue());
    }
}
