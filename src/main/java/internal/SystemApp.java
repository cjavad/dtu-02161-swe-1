package internal;

import internal.*;
import internal.ListeView;

import java.util.HashSet;
import java.util.Set;


public class SystemApp {

    public boolean isAdmin;
    public Medarbejder user;

    public IDGenerator recorder;
    public ListeView<Projekt> projektListeView;
    public ListeView<Medarbejder> medarbejderListeView;
    public ProjektPlanningApp planner;

    public DateServer dateServer;

    public Set<Projekt> projekter;
    public Set<Medarbejder> medarbejder;

    public SystemApp() {
        this.dateServer = null;

        this.projekter = new HashSet<Projekt>();
        this.medarbejder = new HashSet<Medarbejder>();

        this.planner = new ProjektPlanningApp();
        this.recorder = new IDGenerator();

        this.isAdmin = false;
        this.user = null;
    }

    public boolean lavNytProjekt(String navn, DateServer dateServer) {
        if (!isAdmin) return false;

        this.projekter.add(
                new Projekt(
                        navn,
                        this.recorder.getProjektID(this.dateServer.getUgeDato())
                )
        );
        return true;
    }

    public void opretNyMedarbejder(String initials) {
        if (!isAdmin) return;
        if (initials.length() > 4) return;
        if (findMedarbejder(initials) != null) return;

        this.medarbejder.add(
                new Medarbejder(initials)
        );
    }

    public void sletMedarbejder(Medarbejder medarbejder) {
        if (!isAdmin) return;

        medarbejder.getProjekter().forEach(
                projekt -> this.planner.fjernMedarbejderFraProjekt(medarbejder, projekt)
        );
        this.medarbejder.remove(medarbejder);
    }

    public void sletProjekt(Projekt projekt) {
        if (!isAdmin) return;

        projekt.getMedarbejder().forEach(
                medarbejder -> this.planner.fjernMedarbejderFraProjekt(medarbejder, projekt)
        );
        projekt.getAktiviteter().forEach(
                aktivitet -> this.planner.fjernAktivitetFraProjekt(aktivitet, projekt)
        );
        this.projekter.remove(projekt);
    }

    public void lavAktivitet(String navn, Projekt projekt) {
        if (!isProjektleder(projekt)) return;

        this.planner.tilføjAktivitetTilProjekt(
                new Aktivitet(navn),
                projekt
        );
    }

    public void sletAktivitet(Aktivitet aktivitet) {
        if (!isProjektleder(aktivitet.getProjekt())) return;

        this.planner.fjernAktivitetFraProjekt(
                aktivitet,
                aktivitet.getProjekt()
        );
    }

    public void vælgProjektleder(Projekt projekt, Medarbejder medarbejder) {
        // TODO :: permissions ????
        if (!this.isAdmin) return;

        this.planner.ændreProjektleder(projekt, medarbejder);
    }

    public void tilføjMedarbejderTilProjekt(Medarbejder medarbejder, Projekt projekt) {
        if (!isProjektleder(projekt)) return;

        this.planner.tilføjMedarbejderTilProjekt(medarbejder, projekt);
    }

    public void fjernMedarbejderFraProjekt(Medarbejder medarbejder, Projekt projekt) {
        if (!isProjektleder(projekt)) return;

        this.planner.fjernMedarbejderFraProjekt(medarbejder, projekt);
    }

    public void tilføjMedarbejderTilAktivitet(Medarbejder medarbejder, Aktivitet aktivitet) {
        if (!isProjektleder(aktivitet.getProjekt())) return;

        this.planner.tilføjMedarbjederTilAktivitet(medarbejder, aktivitet);
    }

    public void fjernMedarbejderFraAktivitet(Medarbejder medarbejder, Aktivitet aktivitet) {
        if (!isProjektleder(aktivitet.getProjekt())) return;

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

    public Medarbejder findMedarbejder(String initials) {
        for (Medarbejder m : this.medarbejder) {
            if (m.getInitial().equals(initials)) return m;
        }
        return null;
    }

    public Projekt findProjektMedID(String id) {
        for (Projekt p : this.projekter) {
            if (p.getProjektID().equals(id)) return p;
        }
        return null;
    }

    public Projekt findProjektMedNavn(String navn) {
        for (Projekt p : this.projekter) {
            if (p.getNavn().equals(navn)) return p;
        }
        return null;
    }

    public boolean isProjektleder(Projekt projekt) {
        if (this.user == null) return false;
        return this.user.getProjektLederFor().contains(projekt);
    }

}
