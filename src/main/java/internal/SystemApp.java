package internal;

import java.io.Serializable;
import java.util.Set;

public class SystemApp implements Serializable {
    public boolean isAdmin;
    public Medarbejder user;

    public IDGenerator recorder;
    public ProjektPlanningApp planner;

    public DateServer dateServer;


    public SystemApp() {
        this.dateServer = new SystemDateServer(new UgeDato(1, 2024));

        this.planner = new ProjektPlanningApp();
        this.recorder = new IDGenerator();

        this.isAdmin = false;
        this.user = null;
    }

    public boolean lavNytProjekt(String navn) throws SystemAppException {
        if (!isLoggedIn()) return false;
        if (!isAdmin) throw new SystemAppException("Du kan ikke oprette et projekt, da du ikke er admin");
        Set<Projekt> projekter = this.fåProjekter();

        projekter.add(
			new Projekt(
				navn,
				this.recorder.getProjektID(this.dateServer.getUgeDato())
            )
        );



        return true;
    }



    public void opretNyMedarbejder(String initials) {
        if (!isLoggedIn()) return;
        if (!isAdmin) return;
        if (initials.length() > 4) return;
        if (findMedarbejder(initials) != null) return;

        Set<Medarbejder> medarbejdere = this.fåMedarbejdere();

        medarbejdere.add(
                new Medarbejder(initials)
        );
    }

    public void sletMedarbejder(Medarbejder medarbejder) {
        if (!isLoggedIn()) return;
        if (!isAdmin) return;

        medarbejder.getProjekter().forEach(
                projekt -> this.planner.fjernMedarbejderFraProjekt(medarbejder, projekt)
        );
        this.fåMedarbejdere().remove(medarbejder);
    }

    public void sletProjekt(Projekt projekt) {
        if (!isLoggedIn()) return;
        if (!isAdmin) return;

        projekt.getMedarbejder().forEach(
                medarbejder -> this.planner.fjernMedarbejderFraProjekt(medarbejder, projekt)
        );
        projekt.getAktiviteter().forEach(
                aktivitet -> this.planner.fjernAktivitetFraProjekt(aktivitet, projekt)
        );

        this.fåProjekter().remove(projekt);
    }

    public void lavAktivitet(String navn, Projekt projekt) throws SystemAppException {
        if (!isLoggedIn()) return;
        if (!isProjektleder(projekt) && projekt.getProjektLeder() != null) {
            throw new SystemAppException("Du kan ikke ændre aktiviteter på dette projekt");
        };

        this.planner.tilføjAktivitetTilProjekt(
                new Aktivitet(navn),
                projekt
        );
    }

    public void sletAktivitet(Aktivitet aktivitet) throws SystemAppException {
        if (!isLoggedIn()) return;
        if (aktivitet.getProjekt().getProjektLeder() != null && !isProjektleder(aktivitet.getProjekt()))
            throw new SystemAppException("du kan ikke aktiviteter fra projektet");
        this.planner.fjernAktivitetFraProjekt(
                aktivitet,
                aktivitet.getProjekt()
        );
    }

    public void vælgProjektleder(Projekt projekt, Medarbejder medarbejder) {
        if (!isLoggedIn()) return;
        // TODO :: permissions ????
        if (!this.isAdmin) return;

        this.planner.ændreProjektleder(projekt, medarbejder);
    }

    public void tilføjMedarbejderTilProjekt(Medarbejder medarbejder, Projekt projekt) throws SystemAppException {
        if (!isLoggedIn()) return;
        if (!this.isAdmin && !isProjektleder(projekt) && projekt.getProjektLeder() != null) throw new SystemAppException("Du har ikke rettigheder til at tilknytte en medarbejder til dette projekt");

        this.planner.tilføjMedarbejderTilProjekt(medarbejder, projekt);
    }

    public void fjernMedarbejderFraProjekt(Medarbejder medarbejder, Projekt projekt) throws SystemAppException {
        if (!isLoggedIn()) return;
        if (!this.isAdmin && !isProjektleder(projekt) && projekt.getProjektLeder() != null) throw new SystemAppException("du kan ikke fjerne medarbejder fra projektet");

        this.planner.fjernMedarbejderFraProjekt(medarbejder, projekt);
    }

    public void tilføjMedarbejderTilAktivitet(Medarbejder medarbejder, Aktivitet aktivitet) throws SystemAppException {
        if (!isLoggedIn()) return;
        if (!this.isAdmin && aktivitet.getProjekt().getProjektLeder() != null && !isProjektleder(aktivitet.getProjekt())) {
            throw new SystemAppException("du kan ikke anføre en medarbejder til en aktivitet");
        }

        if (!aktivitet.iSammeProjektSomMedarbejder(medarbejder)) throw new SystemAppException("du kan ikke anføre en medarbejder til en aktivitet som ikke er på projektet");

        this.planner.tilføjMedarbjederTilAktivitet(medarbejder, aktivitet);
    }

    public void fjernMedarbejderFraAktivitet(Medarbejder medarbejder, Aktivitet aktivitet) throws SystemAppException {
        if (!isLoggedIn()) return;
        if (!this.isAdmin && !isProjektleder(aktivitet.getProjekt())) throw new SystemAppException("du kan ikke fjerne anførte medarbejdere fra aktiviter");

        this.planner.fjernMedarbejderFraAktivitet(medarbejder, aktivitet);
    }


    public boolean login(String initials) {
        if (initials.equals("admin")) {
            this.isAdmin = true;
            this.user = null;
            return true;
        }
        this.user = findMedarbejder(initials);
        if (this.user != null) {
            this.isAdmin = false;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isAdmin = false;
        this.user = null;
    }

    public boolean isLoggedIn() {
        return this.isAdmin || this.user != null;
    }

    public Medarbejder findMedarbejder(String initials) {
        for (Medarbejder m : this.fåMedarbejdere()) {
            if (m.getInitial().equals(initials)) return m;
        }
        return null;
    }

    public Projekt findProjektMedID(String id) {
        for (Projekt p : this.fåProjekter()) {
            if (p.getProjektID().equals(id)) return p;
        }
        return null;
    }

    public Projekt findProjektMedNavn(String navn) {
        for (Projekt p : this.fåProjekter()) {
            if (p.getNavn().equals(navn)) return p;
        }
        return null;
    }

    public boolean isProjektleder(Projekt projekt) {
		if (this.isAdmin) return true;
        if (this.user == null) return false;
        return this.user.getProjektLederFor().contains(projekt);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Medarbejder getUser() {
        return user;
    }

    public Set<Projekt> fåProjekter() {
        return this.planner.fåProjekter();
    }

    public Set<Medarbejder> fåMedarbejdere() {
        return this.planner.fåMedarbejdere();
    }

    public boolean harProjekter(){
        return this.fåProjekter().isEmpty();
    }

    public int hvorMangeProjekter(){
        return this.fåProjekter().size();
    }


}
