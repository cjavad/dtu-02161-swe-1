package internal;

import io.cucumber.java.ht.Ak;
import io.cucumber.java.hu.Ha;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Medarbejder {

    private final String initial;
    private Set<String> projekter;
    private Set<String> projektLederFor;
    private Set<String> anførteAktiviteter;
    private int ugentligeTimer;

    public Medarbejder(String initial) {
        this.initial = initial;
        this.projekter = new HashSet<String>();
        this.projektLederFor = new HashSet<String>();
        this.anførteAktiviteter = new HashSet<String>();
        this.ugentligeTimer = 0;
    }

    // TODO :: start / slut uge input

    /**
     *
     * @param start
     * @param slut
     * @param aktiviteter En liste af alle aktiviteter som medarbejderen er anført til, som der har minimum én uge i start og slutdato
     * @return
     */
    public int[] beregnFritid(UgeDato start, UgeDato slut, List<Aktivitet> aktiviteter) {
        //Precondition, alle aktiviteter har IDer i anførte aktiviterer, og de har alle en start og slut dato, der gør, at de er inde i den valgte start og slutdato
        aktiviteter.forEach((aktivitet) -> {
            assert this.anførteAktiviteter.contains(aktivitet);
            assert aktivitet.getStartDato() != null;
            assert aktivitet.getSlutDato() != null;
        });

        int[] fritid = new int[slut.ugeDiff(start)];

        aktiviteter.forEach((aktivitet) -> {
            int startIndex, endIndex;

            startIndex = Math.max(aktivitet.getStartDato().ugeDiff(start), 0);
            endIndex = Math.min(aktivitet.getStartDato().ugeDiff(slut), start.ugeDiff(slut));

            for (int i = startIndex; i < endIndex; i++) {
                fritid[i] += aktivitet.beregnArbejdePerMedarbejder();
            }

        });

        return fritid;
    }

    public String getInitial() {
        return initial;
    }

    public Set<String> getProjekter() {
        return projekter;
    }

    public void setProjekter(Set<String> projekter) {
        this.projekter = projekter;
    }

    public Set<String> getProjektLederFor() {
        return projektLederFor;
    }

    public void setProjektLederFor(Set<String> projektLederFor) {
        this.projektLederFor = projektLederFor;
    }

    public Set<String> getAnførteAktiviteter() {
        return anførteAktiviteter;
    }

    public void setAnførteAktiviteter(Set<String> anførteAktiviteter) {
        this.anførteAktiviteter = anførteAktiviteter;
    }

    public int getUgentligeTimer() {
        return ugentligeTimer;
    }

    public void setUgentligeTimer(int ugentligeTimer) {
        this.ugentligeTimer = ugentligeTimer;
    }
}
