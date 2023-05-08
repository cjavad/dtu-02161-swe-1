package internal;

import java.io.Serializable;

public class UgeDato implements Comparable<UgeDato>, Serializable {
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
        return this.uge + "-" + this.årstal;
    }

    @Override
    public int compareTo(UgeDato o) {
        // For at sammenligne UgeDato kan først sammenligne årstal, og hvis de er ens, ugen.
        return this.årstal == o.årstal ?
                Integer.compare(this.uge, o.uge) :
                Integer.compare(this.årstal, o.årstal);
    }

    /**
    Precondition: objekt != null && type(objekt) == UgeDato
     Postcondition: (result == true => objekt.uge == this.uge && objekt.årstal == this.årstal) && (result == false => objekt.uge != this.uge || objekt.årstal != this.årstal)
     */
    @Override
    public boolean equals(Object o) {
        assert(o != null && (o instanceof UgeDato)); //precondition

        UgeDato obj = (UgeDato) o;

        boolean result = obj.uge == this.uge && obj.årstal == this.årstal; //1


        return result;
    }

    public boolean postConditionEquals(UgeDato andenUgedato, boolean resultat){
        if (resultat){
            return andenUgedato.uge == this.uge && andenUgedato.årstal == this.årstal;
        }
        else{

            return andenUgedato.uge != this.uge || andenUgedato.årstal != this.årstal;
        }

    }
}
