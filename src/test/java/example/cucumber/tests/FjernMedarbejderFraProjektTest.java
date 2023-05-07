package example.cucumber.tests;

import example.cucumber.ErrorMessage;
import internal.SystemApp;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FjernMedarbejderFraProjektTest {

    public SystemApp system;
    public Aktiv aktiv;
    public ErrorMessage error;

    public FjernMedarbejderFraProjektTest(SystemApp system, Aktiv aktiv, ErrorMessage error) {
        this.system = system;
        this.aktiv = aktiv;
        this.error = error;
    }

    @When("brugeren vælger at se medarbejderer på projektet")
    public void brugerenVælgerAtSeMedarbejdererPåProjektet() {
        this.aktiv.medarbejderList = this.aktiv.getProjekt().getMedarbejder().stream().toList();
    }
    @When("brugeren vælger at fjerne medarbejderen {string} fra projektet")
    public void brugerenVælgerAtFjerneMedarbejderenFraProjektet(String medarbejder) {
        try {
            this.system.fjernMedarbejderFraProjekt(this.system.findMedarbejder(medarbejder), this.aktiv.getProjekt());
        } catch (Exception e) {
            this.error.setMessage(e.getMessage());
        }
    }

    @Then("er {string} og {string} vist i medarbejderlisten")
    public void erOgVistIMedarbejderlisten(String navn1, String navn2) {
        assertFalse(
                this.aktiv.medarbejderList.stream().noneMatch(m ->
                        m.getInitial().equals(navn1)
                )
        );
        assertFalse(
                this.aktiv.medarbejderList.stream().noneMatch(m ->
                        m.getInitial().equals(navn2)
                )
        );
    }
    @Then("er der {int} bruger tilbage i medarbejderlisten")
    public void erDerBrugerTilbageIMedarbejderlisten(Integer int1) {
        assertTrue(this.aktiv.getProjekt().getMedarbejder().size() == int1);
    }



}
