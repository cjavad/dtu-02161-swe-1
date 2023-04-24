package internal;

public class IDGenerator {
    public Løbenummer løbenummer;

    public IDGenerator() {
        this.løbenummer = new Løbenummer();
    }

    public String getProjektID(UgeDato dato) {
        return dato.getÅrstal() + "-" + løbenummer.getLøbenummer();
    }

    public Løbenummer getLøbenummer() {
        return løbenummer;
    }

    public void setLøbenummer(Løbenummer løbenummer) {
        this.løbenummer = løbenummer;
    }
}