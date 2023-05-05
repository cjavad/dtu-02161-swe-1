package internal;

import java.util.HashSet;
import java.util.Set;

public class Aktivitet {
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
        if (medarbejder == null) {
            throw new NullPointerException();
        }
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
    Precondition: Aktiviteten tilhører et projekt, medarbejderen tilhører samme projekt som aktiviteten
     Postcondition: Der eksisterer nu én refference til medarbejderen i aktivitetens anførteMedarbejdere
     */
    public void tilføjMedarbjeder(Medarbejder medarbejder) {

        assert(this.projekt != null && medarbejder.getProjekter().contains(this.projekt));

        //Da vi har et set, behøver vi ikke at tjekke, om medarbejderen allerede tilhører settet
        this.anførteMedarbjedere.add(medarbejder);
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

    //Checker om startDato er mindre end slutdato eller vise versa
    private boolean isLegalDatoAssignment(UgeDato startDato,UgeDato slutDato){

        //Setting it to null, while they're both null is acceptable
        if(startDato == null && slutDato == null){ //1
            return true;
        }
        // (WLOG) if start != null and slut = null -> then slut := null or start :!= null is acceptable
        if(slutDato == null || startDato == null){ //2
            return true;
        }
        //They're both not null, then we just compare
        else{ //3
            return startDato.compareTo(slutDato) < 0;
        }

    }

    public void setStartDato(UgeDato startDato) {
        assert(startDato != null);

        if(isLegalDatoAssignment(startDato,this.slutDato) )
        {
            this.startDato = startDato;
        }
        else{
            throw new Error("slutDato er mindre end startDato");
        }
    }

    public UgeDato getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(UgeDato slutDato) {

        if( isLegalDatoAssignment(this.startDato,slutDato) )
        {
            this.slutDato = slutDato;
        }
        else{
            throw new Error("slutDato er mindre end startDato");
        }


    }

    public Set<Medarbejder> getAnførteMedarbejdere() {
        return anførteMedarbjedere;
    }
}
