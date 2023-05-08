package example.cucumber.tests;

import example.cucumber.Aktiv;
import example.cucumber.ErrorMessage;
import internal.SystemApp;
import internal.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnførteMedarbejdereTilAktivitet {

    public SystemApp system;
    public ErrorMessage error;
    public Aktiv aktiv;

    public AnførteMedarbejdereTilAktivitet(SystemApp system, ErrorMessage error, Aktiv aktiv) {
        this.system = system;
        this.aktiv = aktiv;
        this.error = error;
    }

    @Given("projektet {string} er oprettet med løbenummer {int} og årstal {int}")
    public void projektetErOprettetMedLøbenummerOgÅrstal(String navn, Integer løbenummer, Integer år) {
        this.system.projekter.add(new Projekt(
                navn,
                this.system.recorder.formatProjektID(år, løbenummer)
        ));
    }
    @Given("en medarbejder {string} eksisterer og er tilknyttet projektet {string}")
    public void enMedarbejderEksistererOgErTilknyttetProjektet(String navn, String projekt) {
        this.system.medarbejder.add(new Medarbejder(navn));
        this.system.planner.tilføjMedarbejderTilProjekt(
                this.system.findMedarbejder(navn),
                this.system.findProjektMedNavn(projekt)
        );
    }
    @Given("en aktivitet {string} eksisterer på projektet med id'et {string} med start datoen {string} og slut datoen {string}")
    public void enAktivitetEksistererPåProjektetMedIdEtMedStartDatoenOgSlutDatoen(
            String aktivitet, String projektID, String start, String slut
    ) {
        Aktivitet a = new Aktivitet(aktivitet);
        a.setStartDato(UgeDato.fromString(start));
        a.setSlutDato(UgeDato.fromString(slut));

        this.system.planner.tilføjAktivitetTilProjekt(
                a,
                this.system.findProjektMedID(projektID)
        );
    }
    @Given("medarbejderen {string} er projektleder på projektet {string}")
    public void medarbejderenErProjektlederPåProjektet(String navn, String projektNavn) {
        this.system.planner.ændreProjektleder(
                this.system.findProjektMedNavn(projektNavn),
                this.system.findMedarbejder(navn)
        );
    }
    @Given("brugeren er logget ind som {string} på projektet {string}")
    public void brugerenErLoggetIndSomPåProjektet(String navn, String projektNavn) {
        this.system.login(navn);
        this.aktiv.setProjekt(this.system.findProjektMedNavn(projektNavn));
    }
    @When("brugeren vælger at se listen af aktiviteter")
    public void brugerenVælgerAtSeListenAfAktiviteter() {
        this.aktiv.aktivitetList = this.aktiv.getProjekt().getAktiviteter().stream().toList();
    }
    @Then("ses aktiviten {string} med start datoen {string} og slut datoen {string}")
    public void sesAktivitenMedStartDatoenOgSlutDatoen(String navn, String start, String slut) {
        assertFalse(
                this.aktiv.aktivitetList.stream().noneMatch(a ->
                        navn.equals(a.getNavn()) &&
                        UgeDato.fromString(start).equals(a.getStartDato()) &&
                        UgeDato.fromString(slut).equals(a.getSlutDato())
                )
        );
    }
    @When("brugeren vælger at se listen af anførte medarbejdere på {string}")
    public void brugerenVælgerAtSeListenAfAnførteMedarbejderePå(String navn) {
        this.aktiv.medarbejderList = this.aktiv.getProjekt().findAktivitet(navn).getAnførteMedarbejdere().stream().toList();
    }
    @Then("er der {int} medarbejdere anført på den")
    public void erDerMedarbejdereAnførtPåDen(Integer count) {
        assertTrue(this.aktiv.medarbejderList.size() == count);
    }
    @When("brugeren anfører {string} til {string}")
    public void brugerenAnførerTil(String medarbejder, String aktivitet) {
        try {
            this.system.tilføjMedarbejderTilAktivitet(
                    this.system.findMedarbejder(medarbejder),
                    this.aktiv.getProjekt().findAktivitet(aktivitet)
            );
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }
    @Then("er der {int} medarbejder anført {string}")
    public void erDerMedarbejderAnført(Integer count, String aktivitet) {
        assertTrue(this.aktiv.getProjekt().findAktivitet(aktivitet).getAnførteMedarbejdere().size() == count);
    }
    @Then("er medarbejderen {string} anført {string}")
    public void erMedarbejderenAnført(String medarbejder, String aktivitet) {
        assertTrue(this.system.findMedarbejder(medarbejder).getAnførteAktiviteter().contains(this.aktiv.getProjekt().findAktivitet(aktivitet)));
    }

    @Then("brugeren får en fejlbesked {string}")
    public void brugerenFårEnFejlbesked(String string) {
        assertTrue(string.equals(this.error.getMessage()));
    }
    @Then("er der ingen anførte medarbejderer på {string}")
    public void erDerIngenAnførteMedarbejdererPå(String string) {
        assertTrue(this.aktiv.getProjekt().findAktivitet(string).getAnførteMedarbejdere().isEmpty());
    }

    @Then("medarbejderen {string} er anført på {string}")
    public void medarbejderenErAnførtPå(String medarbejder, String aktivitet) {
        Medarbejder m = this.system.findMedarbejder(medarbejder);
        Aktivitet a = this.aktiv.getProjekt().findAktivitet(aktivitet);

        assertTrue(m.getAnførteAktiviteter().contains(a));
        assertTrue(a.getAnførteMedarbejdere().contains(m));
    }

    @Given("brugeren er logget ind {string} på {string}")
    public void brugerenErLoggetIndPå(String navn, String projektNavn) {
        this.system.login(navn);
        this.aktiv.setProjekt(this.system.findProjektMedNavn(projektNavn));
    }

    @And("medarbejderen {string} er tilknyttet til projektet ved navn {string}")
    public void medarbejderenErTilknyttetTilProjektetVedNavn(String arg0, String arg1) {
        try {
            this.system.tilføjMedarbejderTilProjekt(
                    this.system.findMedarbejder(arg0),
                    this.system.findProjektMedNavn(arg1)
            );
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }
}
