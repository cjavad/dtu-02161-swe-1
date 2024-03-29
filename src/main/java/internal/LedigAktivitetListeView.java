package internal;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LedigAktivitetListeView implements Serializable {
    public Medarbejder valgteObjekt;
    public List<Medarbejder> højreListe;
    public List<Medarbejder> venstreListe;
    public Pair<UgeDato, UgeDato> datoer;

    public LedigAktivitetListeView(Pair<UgeDato, UgeDato> datoer, List<Medarbejder> højreListe, List<Medarbejder> venstreListe) {
        this.højreListe = højreListe;
        this.venstreListe = venstreListe;
        this.datoer = datoer;
    }

    public void fraVenstreTilHøjre(Medarbejder objekt) {
        venstreListe.remove(objekt);
        højreListe.add(objekt);
    }

    public void fraHøjreTilVenstre(Medarbejder objekt) {
        højreListe.remove(objekt);
        venstreListe.add(objekt);
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

    /**
     * Precondition:
     *
     * Medarbarbejder kan ikke være null &&
     * Start dato kan ikke være null &&
     * Slut dato kan ikke være null &&
     * Slut dato kan ikke komme før start dato
     * Postcondition:
     *
     * Kategorien må ikke være null &&
     * (kategori == "A" => \neg \exists i(i \in fritidPerUge && i<=0)) &&
     * (kategori == "B" => \exists i, \existsj (i \in fritidPerUge && i \in fritidPerUge && j \in fritidPerUge && i<=0 && j>0)) &&
     * (kategori == "C" => \neg \exists i(i \in fritidPerUge && i>0)) &&
     * (kategori == "A" || kategori == "B" || kategori == "C")
     */

    public String opdelPåBaggrundAfFritid(Medarbejder medarbejder) {
        UgeDato startDato = datoer.getKey();
        UgeDato slutDato = datoer.getValue();

        assert medarbejder != null && startDato != null && slutDato != null && slutDato.compareTo(startDato) >= 0; //precondition

        // Find sublisten listen af fritid for medarbejderen tilhører ved at tælle længden af listen
        // For at sammenligne med antal af elementer i listen der er større end 0, aka. hvor der er fritid.
        List<Integer> fritidPerUge = medarbejder.beregnFritidForPeriode(datoer);

        // Hvis antallet af elementer i listen er det samme som antal uger med fritid, er det liste A.
        // Hvis der er nogen uger med fritid er det liste B.
        // Hvis der ingen uger med fritid er det liste C.

        String kategori = "";

        if (isA(fritidPerUge)) { // 1 - Ingen har en uge med fritid på 0 eller under
            kategori = "A";
        } else if (isB(fritidPerUge)) { // 2 - Der eksisterer uger med fritid og uger uden
            kategori = "B";
        } else if (isC(fritidPerUge)) { // 3 - Der eksisterer ingen uger med fritid
            kategori = "C";
        } else { // 4 is not logically possible
            kategori = null;
        }

        assert postconditionOpdelPåBaggrundAfFritid(fritidPerUge, kategori); //postcondition
        return kategori;
    }

    public boolean postconditionOpdelPåBaggrundAfFritid(List<Integer> fritidPerUge,String kategori){

        switch(kategori){
            case "A":
                return fritidPerUge.stream().noneMatch(i -> i<=0);
            case "B":
                return fritidPerUge.stream().anyMatch(i -> i<=0) && fritidPerUge.stream().anyMatch(j -> j>0);
            case "C":
                return fritidPerUge.stream().noneMatch(i -> i>0);
        }


        return false;
    }


    public void sorterHøjreListe() {
        // Sorter listen efter klassificeringen af sublisterne og derefter efter antal timer med fritid.
        højreListe.sort((o1, o2) -> opdelPåBaggrundAfFritid(o1).compareTo(opdelPåBaggrundAfFritid(o2)));
    }

    public List<Medarbejder> getHøjreListe() {
        return højreListe;
    }

    public List<Medarbejder> getVenstreListe() {
        return venstreListe;
    }

    public Medarbejder getValgteObjekt() {
        return valgteObjekt;
    }

    public void setHøjreListe(List<Medarbejder> højreListe) {
        this.højreListe = højreListe;
    }

    public void setValgteObjekt(Medarbejder valgteObjekt) {
        this.valgteObjekt = valgteObjekt;
    }

    public void setVenstreListe(List<Medarbejder> venstreListe) {
        this.venstreListe = venstreListe;
    }
}
