package internal;

public class Løbenummer {
    private int nuværendeLøbenummer;

    public Løbenummer() {
        this.nuværendeLøbenummer = 0;
    }

    int getLøbenummer() {
        nuværendeLøbenummer++;
        return nuværendeLøbenummer;
    }
}
