package example.cucumber;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;

import java.util.List;

public class Aktiv {

    public Projekt projekt;

    public List<Aktivitet> aktivitetList;
    public List<Medarbejder> medarbejderList;

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public Projekt getProjekt() {
        return this.projekt;
    }

}
