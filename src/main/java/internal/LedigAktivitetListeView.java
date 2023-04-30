package internal;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class LedigAktivitetListeView extends ListeView<Medarbejder> {
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

        // Lav projekt og en aktivitet
        // Tilføj aktivitet til projekt

        Projekt p = new Projekt("P1");

        Aktivitet a = new Aktivitet("A1", p);
        Aktivitet a2 = new Aktivitet("A2", p);
        a.setBugetteretTid(37 * 2);
        a2.setBugetteretTid(37 * 2);
        a.setStartDato(new UgeDato(1, 1));
        a.setSlutDato(new UgeDato(1, 2));
        a2.setStartDato(new UgeDato(1, 2));
        a2.setSlutDato(new UgeDato(1, 3));
        p.tilføjAktivitet(a);
        p.tilføjAktivitet(a2);

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

        LedigAktivitetListeView lalv = new LedigAktivitetListeView(new Pair<>(new UgeDato(1, 1), new UgeDato(1, 3)), højreListe, venstreListe);
        lalv.sorterHøjreListe();

        System.out.println("Tid per uge for aktivitet: " + a2.beregnArbejdePerMedarbejder());
        System.out.println("Liste af aktiviter for m2: " + List.of(m2.getAnførteAktiviteter().toArray(new Aktivitet[0])));
        System.out.println("Tid for m2: " + m2.beregnFritidForPeriode(lalv.datoer).sum());

        for (Medarbejder m : lalv.højreListe) {
            System.out.println(Arrays.toString(m.beregnFritidForPeriode(lalv.datoer).toArray()));
        }

        lalv.fraHøjreTilVenstre(m1);

        for (Medarbejder m : lalv.venstreListe) {
            System.out.println(m);
        }
    }

    public LedigAktivitetListeView(Pair<UgeDato, UgeDato> datoer, ArrayList<Medarbejder> højreListe, ArrayList<Medarbejder> venstreListe) {
        super(højreListe, venstreListe);
        this.datoer = datoer;
    }

    public boolean isA(int[] fritid) {
        for (int i : fritid) {
            if (i == 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isB(int[] fritid) {
        for (int i : fritid) {
            if (i > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isC(int[] fritid) {
        for (int i : fritid) {
            if (i > 0) {
                return false;
            }
        }

        return true;
    }

    public void sorterHøjreListe() {
        // Sorter listen efter klassificeringen af sublisterne og derefter efter antal timer med fritid.
        højreListe.sort((o1, o2) -> {
            // Find sublisten listen af fritid for medarbejderen tilhører ved at tælle længden af listen
            // For at sammenligne med antal af elementer i listen der er større end 0, aka. hvor der er fritid.
            int[] s1 = o1.beregnFritidForPeriode(datoer).toArray();
            int[] s2 = o2.beregnFritidForPeriode(datoer).toArray();
            int sum1 = Arrays.stream(s1).sum();
            int sum2 = Arrays.stream(s2).sum();

            // Hvis antallet af elementer i listen er det samme som antal uger med fritid, er det liste A.
            // Hvis der er nogen uger med fritid er det liste B.
            // Hvis der ingen uger med fritid er det liste C.
            String klassificeretSubliste1 = isA(s1) ? "A" : isB(s1) ? "B" : isC(s1) ? "C" : null;
            String klassificeretSubliste2 = isA(s2) ? "A" : isB(s2) ? "B" : isC(s2) ? "C" : null;
            return -Math.max(klassificeretSubliste1.compareTo(klassificeretSubliste2), Integer.compare(sum1, sum2));
        });
    }
}