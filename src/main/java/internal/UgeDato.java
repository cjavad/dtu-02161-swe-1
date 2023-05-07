package internal;

public class UgeDato implements Comparable<UgeDato> {
    int årstal;
    int uge;

    public UgeDato(int årstal, int uge) {
        this.årstal = årstal;
        this.uge = uge;
    }

    public static UgeDato fromString(String dato) {
        String[] pair = dato.split("-");
        return new UgeDato(Integer.parseInt(pair[1]), Integer.parseInt(pair[0]));
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
        assert(difference >= 1);
        return difference;
    }

    @Override
    public String toString() {
        return this.årstal + "-" + this.uge;
    }

    @Override
    public int compareTo(UgeDato o) {
        // For at sammenligne UgeDato kan først sammenligne årstal, og hvis de er ens, ugen.
        return this.årstal == o.årstal ?
                Integer.compare(this.uge, o.uge) :
                Integer.compare(this.årstal, o.årstal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof UgeDato)) return false;

        UgeDato obj = (UgeDato) o;

        return obj.uge == this.uge && obj.årstal == this.årstal;
    }
}
