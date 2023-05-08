package internal;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class LedigAktivitetListeView extends ListeView<Medarbejder> {
    public Pair<UgeDato, UgeDato> datoer;

    public static void main(String[] args) {
        /*
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

        for (Medarbejder m : lalv.højreListe) {
            System.out.println(m.beregnFritidForPeriode(lalv.datoer) + ": " + lalv.klassificerSubliste(m) + ": " + m);
        }

        lalv.fraHøjreTilVenstre(m1);

        for (Medarbejder m : lalv.venstreListe) {
            System.out.println(m);
        }
        */
    }

    public LedigAktivitetListeView(Pair<UgeDato, UgeDato> datoer, ArrayList<Medarbejder> højreListe, ArrayList<Medarbejder> venstreListe) {
        super(højreListe, venstreListe);
        this.datoer = datoer;
    }

    public boolean isA(List<Integer> fritid) {
        for (int i : fritid) {
            if (i <= 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isB(List<Integer> fritid) {
        for (int i : fritid) {
            if (i > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isC(List<Integer> fritid) {
        for (int i : fritid) {
            if (i > 0) {
                return false;
            }
        }

        return true;
    }


    public String opdelPåBaggrundAfFritid(Medarbejder o) {
        /**
         * Precondition:
         *
         * Medarbarbejder kan ikke være null.
         * Start dato kan ikke være null.
         * Slut dato kan ikke være null.
         * Slut dato kan ikke komme før start dato
         */
        assert o != null && datoer.getKey() != null && datoer.getValue() != null && datoer.getValue().compareTo(datoer.getKey()) >= 0; //0

        // Find sublisten listen af fritid for medarbejderen tilhører ved at tælle længden af listen
        // For at sammenligne med antal af elementer i listen der er større end 0, aka. hvor der er fritid.
        List<Integer> fritidPerUge = o.beregnFritidForPeriode(datoer);

        // Hvis antallet af elementer i listen er det samme som antal uger med fritid, er det liste A.
        // Hvis der er nogen uger med fritid er det liste B.
        // Hvis der ingen uger med fritid er det liste C.

        String kategori = "";


        if (isA(fritidPerUge)) { // 1 - Ingen har en uge med fritid på 0 eller under
            kategori = "A";
        } else if (isB(fritidPerUge)) { // 2
            kategori = "B";
        } else if (isC(fritidPerUge)) { // 3
            kategori = "C";
        } else { // 4
            kategori = null;
        }

        /**
         * Postcondition:
         *
         * Kategorien må ikke være null (det burde heller ikke være muligt)
         * Kategorien skal være enten A, B eller C.
         *
         */
        assert kategori != null && (kategori.equals("A") || kategori.equals("B") || kategori.equals("C"));
        return kategori;
    }

    public void sorterHøjreListe() {
        // Sorter listen efter klassificeringen af sublisterne og derefter efter antal timer med fritid.
        højreListe.sort((o1, o2) -> -Math.max(
                opdelPåBaggrundAfFritid(o1).compareTo(opdelPåBaggrundAfFritid(o2)),
                Integer.compare(
                        o1.beregnFritidForPeriode(datoer).stream().mapToInt(i -> i).sum(),
                        o2.beregnFritidForPeriode(datoer).stream().mapToInt(i -> i).sum()
                )
        ));
    }
}