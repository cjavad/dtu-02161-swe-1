package internal;

import io.cucumber.java.sl.In;

public class UgeDato implements Comparable<UgeDato> {
    int årstal;
    int uge;

    public UgeDato(int årstal, int uge) {
        this.årstal = årstal;
        this.uge = uge;
    }

    public int getÅrstal() {
        return årstal;
    }

    public int getUge() {
        return uge;
    }

    public void setÅrstal(int årstal) {
        this.årstal = årstal;
    }

    public void setUge(int uge) {
        this.uge = uge;
    }

    public int ugeDiff(UgeDato dato) {

        assert(dato != null);
        int difference = Math.abs(this.årstal - dato.årstal) * 52 + Math.abs(this.uge - dato.uge) + 1;
//        assert(difference >= 1); // BAD >:(
        return difference;
    }

    @Override
    public int compareTo(UgeDato o) {
        // For at sammenligne UgeDato kan først sammenligne årstal, og hvis de er ens, ugen.
        return this.årstal == o.årstal ?
                Integer.compare(this.uge, o.uge) :
                Integer.compare(this.årstal, o.årstal);
    }
}
