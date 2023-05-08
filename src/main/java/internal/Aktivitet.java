package internal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Aktivitet implements Serializable {
    private int bugetteretTid;
    private Projekt projekt;
    private final String navn;
    private UgeDato startDato;
    private UgeDato slutDato;
    private Set<Medarbejder> anførteMedarbjedere;

    public Aktivitet(String navn) {
        this.navn = navn;
        this.bugetteretTid = 0;
        this.startDato = null;
        this.slutDato = null;
        this.anførteMedarbjedere = new HashSet<Medarbejder>();
    }



    public void tilføjMedarbjederTilAktivitet(Medarbejder medarbejder) {

        this.tilføjMedarbjeder(medarbejder);
        medarbejder.tilføjAktivitet(this);
    }

    public void fjernMedarbejderFraAktivitet(Medarbejder medarbejder) {
        if (medarbejder == null) {
            throw new NullPointerException();
        }

        this.fjernMedarbejder(medarbejder);
        medarbejder.fjernAktivitet(this);
    }

    // Per uge ikke for hele projektet
    public int beregnArbejdePerMedarbejder() {
        if (this.startDato == null || this.slutDato == null) {
            return 0;
        }

        return this.bugetteretTid / this.anførteMedarbjedere.size();
    }


    /**
    Precondition: Medarbejderen er ikke null, aktiviteten tilhører et projekt, medarbejderen tilhører samme projekt som aktiviteten
     Postcondition: Der eksisterer nu én refference til medarbejderen i aktivitetens anførteMedarbejdere
     */
    public void tilføjMedarbjeder(Medarbejder medarbejder) {

        assert(medarbejder != null && iSammeProjektSomMedarbejder(medarbejder));

        //Da vi har et set, behøver vi ikke at tjekke, om medarbejderen allerede tilhører settet
        this.anførteMedarbjedere.add(medarbejder);

        assert(this.anførteMedarbjedere.contains(medarbejder));
    }

    public void fjernMedarbejder(Medarbejder medarbejder) {
        if (!this.anførteMedarbjedere.contains(medarbejder)) {
            return;
        }

        this.anførteMedarbjedere.remove(medarbejder);
    }

    public void tilføjProjekt(Projekt projekt) {
        if (projekt == null || this.projekt != null) {
            return;
        }

        this.projekt = projekt;
    }

    public void fjernProjekt(Projekt projekt) {
        if (projekt == null || this.projekt != projekt) {
            return;
        }

        this.projekt = null;
    }

    public String getNavn() {
        return navn;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public int getBugetteretTid() {
        return bugetteretTid;
    }

    public void setBugetteretTid(int bugetteretTid) {
        this.bugetteretTid = bugetteretTid;
    }

    public UgeDato getStartDato() {
        return startDato;
    }

    /**
    Precondition: True (Can be ommitted)
     Postcondition: ((startDato != null) && (slutDato != null)) => startDato<=slutDato
     */
    //Checker om startDato er mindre end slutdato eller vise versa
    public boolean isLegalDatoAssignment(UgeDato startDato,UgeDato slutDato){

        assert true;
        //Setting it to null, while they're both null is acceptable
        boolean result;

        if(startDato == null && slutDato == null){ //1
            result = true;
        }
        // (WLOG) if start != null and slut = null -> then slut := null or start :!= null is acceptable
        if(slutDato == null || startDato == null){ //2
            result = true;
        }
        //They're both not null, then we just compare
        // They can be equal, or the end date can come after the start date, but the end date cannot come before the start date.
        else { //3
            result = startDato.compareTo(slutDato) <= 0; // 3a, 3b
        }

        assert(postconditionIsLegalDatoAssignment(result,startDato,slutDato));
        return result;
    }

    public boolean postconditionIsLegalDatoAssignment(boolean result,UgeDato startDato,UgeDato slutDato){
        boolean resultBurdeVære;
        if(slutDato == null || startDato == null){ //2
            resultBurdeVære = true;
        }
        else{
            resultBurdeVære = startDato.compareTo(slutDato) <= 0;
        }

        return resultBurdeVære == result;
    }

    public void setStartDato(UgeDato startDato) {
        assert(startDato != null);

        if (isLegalDatoAssignment(startDato, this.slutDato)) {
            this.startDato = startDato;
        } else {
            throw new Error("slutDato er mindre end startDato");
        }

        assert isLegalDatoAssignment(startDato, this.slutDato);
    }

    public UgeDato getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(UgeDato slutDato) {

        if (isLegalDatoAssignment(this.startDato,slutDato)) {
            this.slutDato = slutDato;
        } else {
            throw new Error("slutDato er mindre end startDato");
        }
    }

    public Set<Medarbejder> getAnførteMedarbejdere() {
        return anførteMedarbjedere;
    }

    public boolean iSammeProjektSomMedarbejder(Medarbejder medarbejder){
        return (this.projekt != null && this.projekt.getMedarbejder().contains(medarbejder));
    }

    public int forekomsterAfMedarbejder(Medarbejder m) {
        Iterator<Medarbejder> medarbejderIterator = this.anførteMedarbjedere.iterator();
        int forekomster = 0;

        while (medarbejderIterator.hasNext()) {
            if (medarbejderIterator.next() == m) {
                forekomster++;
            }
        }

        return forekomster;
	}

    @Override
    public String toString() {
        return this.navn;
    }
}
