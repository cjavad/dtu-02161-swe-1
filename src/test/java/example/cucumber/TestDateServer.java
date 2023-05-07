package example.cucumber;

import internal.DateServer;
import internal.UgeDato;

public class TestDateServer implements DateServer {

    public UgeDato dato;

    public TestDateServer() {
        this.dato = new UgeDato(2023, 1);
    }

    public void setÅr(int år) {
        this.dato.setÅrstal(år);
    }

    public void setUge(int uge) {
        this.dato.setUge(uge);
    }

    public void set(int år, int uge) {
        this.dato.setÅrstal(år);
        this.dato.setUge(uge);
    }

    @Override
    public UgeDato getUgeDato() {
        return this.dato;
    }
}
