package internal;

import java.io.Serializable;
import java.util.List;

public class ListeView<T> implements Serializable {
    public T valgteObjekt;
    public List<T> højreListe;
    public List<T> venstreListe;

    public ListeView(List<T> højreListe, List<T> venstreListe) {
        this.højreListe = højreListe;
        this.venstreListe = venstreListe;
    }

    public void fraVenstreTilHøjre(T objekt) {
        venstreListe.remove(objekt);
        højreListe.add(objekt);
    }

    public void fraHøjreTilVenstre(T objekt) {
        højreListe.remove(objekt);
        venstreListe.add(objekt);
    }

    public List<T> getHøjreListe() {
        return højreListe;
    }

    public List<T> getVenstreListe() {
        return venstreListe;
    }

    public T getValgteObjekt() {
        return valgteObjekt;
    }

    public void setHøjreListe(List<T> højreListe) {
        this.højreListe = højreListe;
    }

    public void setValgteObjekt(T valgteObjekt) {
        this.valgteObjekt = valgteObjekt;
    }

    public void setVenstreListe(List<T> venstreListe) {
        this.venstreListe = venstreListe;
    }
}
