package example.cucumber.tests;

import example.cucumber.Aktiv;
import example.cucumber.ErrorMessage;
import internal.SystemApp;
import internal.SystemAppException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class FjernMedarbejderFraAktivitetTest {

    public SystemApp system;
    public Aktiv aktiv;
    public ErrorMessage error;

    public FjernMedarbejderFraAktivitetTest(SystemApp system, Aktiv aktiv, ErrorMessage error) {
        this.system = system;
        this.aktiv = aktiv;
        this.error = error;
    }

    @When("brugeren vælger at fjerne {string} fra {string}")
    public void brugerenVælgerAtFjerneFra(String navn, String aktivitet) {
        try {
            this.system.fjernMedarbejderFraAktivitet(
                    this.system.findMedarbejder(navn),
                    this.aktiv.getProjekt().findAktivitet(aktivitet)
            );
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }

    }
    @Then("er der {int} aktivitet på projektet")
    public void erDerAktivitetPåProjektet(Integer int1) {
        assertTrue(this.aktiv.getProjekt().getAktiviteter().size() == int1);
    }

}
