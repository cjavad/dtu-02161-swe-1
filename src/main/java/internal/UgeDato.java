package internal;

public class UgeDato {
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
        return (this.årstal - dato.årstal) * 52 + this.uge - dato.uge;
    }
}
