package internal;

public class MedarbejderTabel extends AbstractTabel<Medarbejder> {

    private ProjektTable projektTable;

    public ProjektTable getProjektTable() {
        return projektTable;
    }

    public void setProjektTable(ProjektTable projektTable) {
        this.projektTable = projektTable;
    }

    /**
     * preconditions: medarbejder exists, projekt exists
     * @param medarbejder
     * @param projekt
     */
    public void fjernProjekt(String medarbejder, String projekt) {
        this.projektTable.fjernMedarbjeder(projekt, medarbejder);
        this.get(medarbejder).getProjekter().remove(projekt);
    }

    public void fjernProjektLeder(String medarbejder, String projekt) {
        this.projektTable.get(projekt).setProjektLeder(null);
        this.get(medarbejder).getProjektLederFor().remove(projekt);
    }

    public void fjernAktivitet(String medarbejder, String aktivitet) {
        this.projektTable.fjernMedarbejderFraAktivitet(medarbejder, aktivitet);
        this.get(medarbejder).getAnførteAktiviteter().remove(aktivitet);
    }

    public void tilføjProjekt(String medarbejder, String projekt) {
        this.projektTable.tilføjMedarbejder(projekt, medarbejder);
        this.get(medarbejder).getProjekter().add(projekt);
    }

    public void tilføjProjektLeder(String medarbejder, String projekt) {
        this.projektTable.get(projekt).setProjektLeder(medarbejder);
        this.get(medarbejder).getProjektLederFor().remove(projekt);
    }

    public void tilføjAktivitet(String medarbejder, String aktivitet) {
        this.projektTable.tilføjMedarbejderTilAktivitet(medarbejder, aktivitet);
        this.get(medarbejder).getAnførteAktiviteter().add(aktivitet);
    }


}
