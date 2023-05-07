package example.junit.whitebox;

import internal.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpdelPåBaggrundAfFritidTests {
    LedigAktivitetListeView lw;
    Pair<UgeDato, UgeDato> datoer = new Pair<>(new UgeDato(2023, 1), new UgeDato(2023, 2));
    ProjektPlanningApp app = new ProjektPlanningApp();
    Projekt projekt = new Projekt("Projekt 1", "01-2023");
    List<Medarbejder> medarbejdere = Arrays.asList(new Medarbejder("a"),
            new Medarbejder("b"),
            new Medarbejder("c"));

    List<Aktivitet> aktiviteter = Arrays.asList(new Aktivitet("a"),
            new Aktivitet("b"),
            new Aktivitet("c"));
    @org.junit.Before()
    public void setup() {
        // Instantiate the medarbejder and aktivitet objects
        medarbejdere.get(0).setUgentligeTimer(2);
        medarbejdere.get(1).setUgentligeTimer(2);
        medarbejdere.get(2).setUgentligeTimer(2);

        app.tilføjMedarbejderTilProjekt(medarbejdere.get(0), projekt);
        app.tilføjMedarbejderTilProjekt(medarbejdere.get(1), projekt);
        app.tilføjMedarbejderTilProjekt(medarbejdere.get(2), projekt);

        aktiviteter.get(0).setBugetteretTid(1);
        aktiviteter.get(0).setStartDato(new UgeDato(2023, 1));
        aktiviteter.get(0).setSlutDato(new UgeDato(2023, 1));

        aktiviteter.get(1).setBugetteretTid(2);
        aktiviteter.get(1).setStartDato(new UgeDato(2023, 1));
        aktiviteter.get(1).setSlutDato(new UgeDato(2023, 1));

        aktiviteter.get(2).setBugetteretTid(2);
        aktiviteter.get(2).setStartDato(new UgeDato(2023, 1));
        aktiviteter.get(2).setSlutDato(new UgeDato(2023, 2));

        app.tilføjAktivitetTilProjekt(aktiviteter.get(0), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(1), projekt);
        app.tilføjAktivitetTilProjekt(aktiviteter.get(2), projekt);

        app.tilføjMedarbjederTilAktivitet(medarbejdere.get(0), aktiviteter.get(0));
        app.tilføjMedarbjederTilAktivitet(medarbejdere.get(1), aktiviteter.get(1));
        app.tilføjMedarbjederTilAktivitet(medarbejdere.get(2), aktiviteter.get(2));

        lw = new LedigAktivitetListeView(datoer, new ArrayList<>(medarbejdere), new ArrayList<>());
    }

    @org.junit.Test()
    public void inputsetA() {
        org.junit.Assert.assertThrows(AssertionError.class, () -> lw.opdelPåBaggrundAfFritid(null));
    }

    @org.junit.Test()
    public void inputsetA2() {
        lw = new LedigAktivitetListeView(new Pair<>(null, datoer.getValue()), new ArrayList<>(medarbejdere), new ArrayList<>());
        org.junit.Assert.assertThrows(AssertionError.class, () -> lw.opdelPåBaggrundAfFritid(new Medarbejder("")));
    }

    @org.junit.Test()
    public void inputsetA3() {
        lw = new LedigAktivitetListeView(new Pair<>(datoer.getKey(), null), new ArrayList<>(medarbejdere), new ArrayList<>());
        org.junit.Assert.assertThrows(AssertionError.class, () -> lw.opdelPåBaggrundAfFritid(new Medarbejder("")));
    }

    @org.junit.Test()
    public void inputsetA4() {
        lw = new LedigAktivitetListeView(new Pair<>(datoer.getValue(), datoer.getKey()), new ArrayList<>(medarbejdere), new ArrayList<>());
        org.junit.Assert.assertThrows(AssertionError.class, () -> lw.opdelPåBaggrundAfFritid(new Medarbejder("")));
    }

    @org.junit.Test()
    public void inputsetB() {
        org.junit.Assert.assertEquals("A", lw.opdelPåBaggrundAfFritid(medarbejdere.get(0)));
    }

    @org.junit.Test()
    public void inputsetC() {
        org.junit.Assert.assertEquals("B", lw.opdelPåBaggrundAfFritid(medarbejdere.get(1)));
    }

    @org.junit.Test()
    public void inputsetD() {
        org.junit.Assert.assertEquals("C", lw.opdelPåBaggrundAfFritid(medarbejdere.get(2)));
    }

}
