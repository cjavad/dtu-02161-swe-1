package internal;

public class AktivitetsTabel extends AbstractTabel<Aktivitet> {

    public void fjernMedarbejder(String aktivitet, String medarbejder) {
        this.get(aktivitet).getAnførteMedarbjedere().remove(medarbejder);
    }

    public void tilføjMedarbejder(String aktivitet, String medarbejder) {

    }

}
