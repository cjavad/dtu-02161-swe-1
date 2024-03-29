package internal;

import java.io.Serializable;

public class IDGenerator implements Serializable {
    public Løbenummer løbenummer;

    public IDGenerator() {
        this.løbenummer = new Løbenummer();
    }

    public String getProjektID(UgeDato dato) {
        // zero pad løbenummer
        return formatProjektID(dato.getÅrstal(), this.løbenummer.getLøbenummer());
    }

    public String formatProjektID(int år, int løbenummer) {
        return String.format("%04d", år) + "-" + String.format("%02d", løbenummer);
    }

    public Løbenummer getLøbenummer() {
        return løbenummer;
    }

    public void setLøbenummer(Løbenummer løbenummer) {
        this.løbenummer = løbenummer;
    }
}