package internal;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LedigAktivitetListeView extends ListeView<Medarbejder> implements SorteretListeView {
    public Pair<UgeDato, UgeDato> datoer;

    public static void main(String[] args) {
        // Simple Test
        ArrayList<Medarbejder> højreListe = new ArrayList<>();
        ArrayList<Medarbejder> venstreListe = new ArrayList<>();

        Medarbejder m1 = new Medarbejder("M1");
        Medarbejder m2 = new Medarbejder("M2");
        Medarbejder m3 = new Medarbejder("M3");

        m1.setUgentligeTimer(37);
        m2.setUgentligeTimer(37);
        m3.setUgentligeTimer(37);

        højreListe.add(m1);
        højreListe.add(m2);
        højreListe.add(m3);

        LedigAktivitetListeView lalv = new LedigAktivitetListeView(new Pair<>(new UgeDato(1, 1), new UgeDato(1, 2)), højreListe, venstreListe);
        // Lav projekt og en aktivitet
        // Tilføj aktivitet til projekt

        Projekt p = new Projekt("P1");
        Aktivitet a = new Aktivitet("A1", p);
        Aktivitet a2 = new Aktivitet("A2", p);
        a.setBugetteretTid(37);
        a2.setBugetteretTid(37);
        a.setStartDato(new UgeDato(1, 1));
        a.setSlutDato(new UgeDato(1, 2));
        a2.setStartDato(new UgeDato(1, 2));
        a2.setSlutDato(new UgeDato(1, 3));
        p.tilføjAktivitet(a);
        p.tilføjMedarbejder(m1);
        p.tilføjMedarbejder(m2);
        p.tilføjMedarbejder(m3);
        m1.tilføjProjekt(p);
        m2.tilføjProjekt(p);
        m3.tilføjProjekt(p);

        m1.tilføjAktivitet(a);
        m2.tilføjAktivitet(a);
        a.tilføjMedarbjeder(m1);
        a.tilføjMedarbjeder(m2);
        m2.tilføjAktivitet(a2);
        a2.tilføjMedarbjeder(m2);

        lalv.sorterHøjreListe();

        System.out.println("Tid per uge for aktivitet: " + a.beregnArbejdePerMedarbejder());
        System.out.println("Beregn fritid for m1: " + Arrays.toString(m1.beregnFritid(lalv.datoer.getKey(), lalv.datoer.getValue(), List.of(m1.getAnførteAktiviteter().toArray(new Aktivitet[0])))));
        System.out.println("Tid for m1: " +  m1.fritidFraDato(lalv.datoer).sum());

        for (Medarbejder m : lalv.højreListe) {
            System.out.println(m.kategoriser(lalv.datoer) + ": " + m);
        }

        lalv.fraHøjreTilVenstre(m1);

        for (Medarbejder m : lalv.venstreListe) {
            System.out.println(m);
        }
    }

    public LedigAktivitetListeView(Pair<UgeDato, UgeDato>  datoer, ArrayList<Medarbejder> højreListe, ArrayList<Medarbejder> venstreListe) {
        super(højreListe, venstreListe);
        this.datoer = datoer;
    }

    @Override
    public void sorterHøjreListe() {
        højreListe.sort((o1, o2) -> -o1.compareToAktivitet(datoer, o2));
    }
}