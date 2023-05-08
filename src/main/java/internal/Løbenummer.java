package internal;

import java.io.Serializable;

public class Løbenummer implements Serializable {
    private int nuværendeLøbenummer;

    public Løbenummer() {
        this.nuværendeLøbenummer = 0;
    }

    int getLøbenummer() {
        nuværendeLøbenummer++;
        return nuværendeLøbenummer;
    }
}
