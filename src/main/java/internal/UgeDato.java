package internal;

public class UgeDato {

    private int år;
    private int uge;

    public UgeDato() {

    }

    public UgeDato(int år, int uge) {
        this.år = år;
        this.uge = uge;
    }

    public int ugeDiff(UgeDato dato) {
        return (this.år - dato.år) * 52 + this.uge - dato.uge;
    }

    public int getÅr() {
        return år;
    }

    public void setÅr(int år) {
        this.år = år;
    }

    public int getUge() {
        return uge;
    }

    public void setUge(int uge) {
        this.uge = uge;
    }
}
