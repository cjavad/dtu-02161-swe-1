package internal;

import java.util.List;
import java.util.stream.Collectors;

public class ProjektTable extends AbstractTabel<Projekt> {

    private MedarbejderTabel medarbejderTable;

    private AktivitetsTabel aktivitetsTabel;

    public MedarbejderTabel getMedarbejderTable() {
        return medarbejderTable;
    }

    public void setMedarbejderTable(MedarbejderTabel medarbejderTable) {
        this.medarbejderTable = medarbejderTable;
    }

    /**
     * internal
     * @param projekt
     * @param medarbejder
     */
    public void fjernMedarbjeder(String projekt, String medarbejder) {
        // remove medarbjeder from all aktiviteter
        List<String> aktiviteter = this.get(projekt).getAktiviteter()
                .stream()
                .filter(
                        (p) -> this.aktivitetsTabel.get(p).getAnførteMedarbjedere().contains(medarbejder)
        ).collect(Collectors.toList());

        for (String aktivitet : aktiviteter) {
            this.aktivitetsTabel.fjernMedarbejder(aktivitet, medarbejder);
            this.medarbejderTable.get(medarbejder).getAnførteAktiviteter().remove(aktivitet);
        }

        // remove medarbejder from projekt
        this.get(projekt).getMedarbejder().remove(medarbejder);
    }

    public void fjernAktivitet(String projekt, String aktivitet) {
        for (String medarbejder : this.aktivitetsTabel.get(aktivitet).getAnførteMedarbjedere()) {
            this.medarbejderTable.get(medarbejder).getAnførteAktiviteter().remove(aktivitet);
        }
        this.get(projekt).getAktiviteter().remove(aktivitet);
    }

    public void tilføjMedarbejder(String projekt, String medarbejder) {
        this.get(projekt).getMedarbejder().add(medarbejder);
    }

    public void tilføjAktivitet(String projekt, String aktivitet) {
        this.get(projekt).getAktiviteter().add(aktivitet);
    }

    public void fjernMedarbejderFraAktivitet(String medarbejder, String aktivitet) {
        this.aktivitetsTabel.fjernMedarbejder(aktivitet, medarbejder);
    }

    public void tilføjMedarbejderTilAktivitet(String medarbjeder, String aktivitet) {
        this.aktivitetsTabel.tilføjMedarbejder(aktivitet, medarbjeder);
    }



}
