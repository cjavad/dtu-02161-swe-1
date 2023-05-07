package example.cucumber;

import internal.SystemApp;
import internal.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnførteMedarbejdereTilAktivitet {

    public SystemApp system;
    public Projekt aktiveProjekt;
    public TestDateServer dateServer;

    public List<Aktivitet> aktivitetList;
    public List<Medarbejder> medarbejderList;

    public AnførteMedarbejdereTilAktivitet(SystemApp system) {
        this.system = system;
        this.dateServer = new TestDateServer();
        this.aktiveProjekt = null;
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
        this.aktiveProjekt = this.system.findProjektMedNavn(projektNavn);
    }
    @When("brugeren vælger at se listen af aktiviteter")
    public void brugerenVælgerAtSeListenAfAktiviteter() {
        this.aktivitetList = this.aktiveProjekt.getAktiviteter().stream().toList();
    }
    @Then("ses aktiviten {string} med start datoen {string} og slut datoen {string}")
    public void sesAktivitenMedStartDatoenOgSlutDatoen(String navn, String start, String slut) {
        assertFalse(
                this.aktivitetList.stream().noneMatch(a ->
                        navn.equals(a.getNavn()) &&
                        UgeDato.fromString(start).equals(a.getStartDato()) &&
                        UgeDato.fromString(slut).equals(a.getSlutDato())
                )
        );
    }
    @When("brugeren vælger at se listen af anførte medarbejdere på {string}")
    public void brugerenVælgerAtSeListenAfAnførteMedarbejderePå(String navn) {
        this.medarbejderList = this.aktiveProjekt.findAktivitet(navn).getAnførteMedarbejdere().stream().toList();
    }
    @Then("er der {int} medarbejdere anført på den")
    public void erDerMedarbejdereAnførtPåDen(Integer count) {
        assertTrue(this.medarbejderList.size() == count);
    }
    @When("brugeren anfører {string} til {string}")
    public void brugerenAnførerTil(String medarbejder, String aktivitet) {
        this.system.tilføjMedarbejderTilAktivitet(
                this.system.findMedarbejder(medarbejder),
                this.aktiveProjekt.findAktivitet(aktivitet)
        );
    }
    @Then("er der {int} medarbejder anført {string}")
    public void erDerMedarbejderAnført(Integer count, String aktivitet) {
        assertTrue(this.aktiveProjekt.findAktivitet(aktivitet).getAnførteMedarbejdere().size() == count);
    }
    @Then("er medarbejderen {string} anført {string}")
    public void erMedarbejderenAnført(String medarbejder, String aktivitet) {
        assertTrue(this.system.findMedarbejder(medarbejder).getAnførteAktiviteter().contains(this.aktiveProjekt.findAktivitet(aktivitet)));
    }

    @Then("brugeren får en fejlbesked {string}")
    public void brugerenFårEnFejlbesked(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("er der ingen anførte medarbejderer på {string}")
    public void erDerIngenAnførteMedarbejdererPå(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}
