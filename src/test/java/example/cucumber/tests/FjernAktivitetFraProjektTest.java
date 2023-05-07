package example.cucumber.tests;

import example.cucumber.Aktiv;
import example.cucumber.ErrorMessage;
import internal.SystemApp;
import internal.SystemAppException;
import internal.UgeDato;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FjernAktivitetFraProjektTest {

    public SystemApp system;
    public Aktiv aktiv;
    public ErrorMessage error;

    public FjernAktivitetFraProjektTest(SystemApp system, Aktiv aktiv, ErrorMessage error) {
        this.system = system;
        this.aktiv = aktiv;
        this.error = error;
    }


    @Given("medarbejderen {string} er anført {string} på {string}")
    public void medarbejderenErAnførtPå(String medarbejder, String aktivitet, String projekt) {
        this.system.planner.tilføjMedarbejderTilProjekt(
                this.system.findMedarbejder(medarbejder),
                this.system.findProjektMedNavn(projekt)
        );
        this.system.planner.tilføjMedarbjederTilAktivitet(
                this.system.findMedarbejder(medarbejder),
                this.system.findProjektMedNavn(projekt).findAktivitet(aktivitet)
        );
    }
    @Given("brugeren er logget ind som admin på {string}")
    public void brugerenErLoggetIndSomAdminPå(String projekt) {
        this.aktiv.setProjekt(this.system.findProjektMedNavn(projekt));
        this.system.login("admin");
    }
    @When("brugeren vælger at se aktiviteterne på projektet")
    public void brugerenVælgerAtSeAktiviteternePåProjektet() {
        this.aktiv.aktivitetList = this.aktiv.getProjekt().getAktiviteter().stream().toList();
    }
    @Then("kan brugeren se {string} med start datoen {string} og slut datoen {string}")
    public void kanBrugerenSeMedStartDatoenOgSlutDatoen(String navn, String start, String slut) {
        assertFalse(
                this.aktiv.aktivitetList.stream().noneMatch(a ->
                        navn.equals(a.getNavn()) &&
                                UgeDato.fromString(start).equals(a.getStartDato()) &&
                                UgeDato.fromString(slut).equals(a.getSlutDato())
                )
        );
    }
    @Then("medarbejderen {string} har aktivitets tuplen \\({string}, {string})")
    public void medarbejderenHarAktivitetsTuplen(String navn, String projekt, String aktivitet) {
        assertFalse(
                this.system.findMedarbejder(navn).getAnførteAktiviteter().stream().noneMatch(a ->
                        a.getNavn().equals(aktivitet) && a.getProjekt() == this.system.findProjektMedID(projekt)
                )
        );
    }
    @When("brugeren vælger at fjerne {string} fra projektet")
    public void brugerenVælgerAtFjerneFraProjektet(String string) {
        try {
            this.system.sletAktivitet(this.aktiv.getProjekt().findAktivitet(string));
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }
    @Then("er der {int} aktiviteter på projektet")
    public void erDerAktiviteterPåProjektet(Integer count) {
        assertTrue(this.aktiv.getProjekt().getAktiviteter().size() == count);
    }
    @Then("medarbejderen {string} har ikke aktivitets tuplen \\({string}, {string})")
    public void medarbejderenHarIkkeAktivitetsTuplen(String navn, String projekt, String aktivitet) {
        assertTrue(
                this.system.findMedarbejder(navn).getAnførteAktiviteter().stream().noneMatch(a ->
                        a.getNavn().equals(aktivitet) && a.getProjekt() == this.system.findProjektMedID(projekt)
                )
        );
    }

    @Given("brugeren er logget ind som {string} på {string}")
    public void brugerenErLoggetIndSomPå(String user, String projektNavn) {
        this.system.login(user);
        this.aktiv.setProjekt(this.system.findProjektMedNavn(projektNavn));
    }

    @Then("brugeren ser en fejlbesked {string}")
    public void brugerenSerEnFejlbesked(String string) {
        assertTrue(string.equals(this.error.getMessage()));
    }

}
