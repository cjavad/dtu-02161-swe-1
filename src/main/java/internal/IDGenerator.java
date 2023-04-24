package internal;

public class IDGenerator {
    public Løbenummer løbenummer;

    public IDGenerator() {
        this.løbenummer = new Løbenummer();
    }

    public String getProjektID(UgeDato dato) {
        // zero pad løbenummer
        return dato.getÅrstal() + "-" + String.format("%02d", løbenummer.getLøbenummer());
    }

    public Løbenummer getLøbenummer() {
        return løbenummer;
    }

    public void setLøbenummer(Løbenummer løbenummer) {
        this.løbenummer = løbenummer;
    }
}