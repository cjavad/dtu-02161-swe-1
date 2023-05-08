package example.cucumber.tests;

import example.cucumber.ErrorMessage;
import example.cucumber.TestDateServer;
import internal.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreateProjektTest {

    public SystemApp system;
    public ErrorMessage error;
    public CreateProjektTest(SystemApp system, ErrorMessage error) {
        this.system = system;
        if (!(this.system.dateServer instanceof TestDateServer)) {
            this.system.dateServer = new TestDateServer();
        }

        this.error = error;
    }

    @Given("brugeren er logget ind som admin")
    public void brugerenErLoggetIndSomAdmin() {
        this.system.login("admin");
    }
    @When("brugeren trykker på en knap {string} og opretter et projekt med titlen {string}")
    public void brugerenTrykkerPåEnKnapOgOpretterEtProjektMedTitlen(String string, String navn) {
        try {
            this.system.lavNytProjekt(navn);
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }
    @Then("kan brugeren se projektet {string} på listen over projekter med løbenummer {int} og årstal {int}")
    public void kanBrugerenSeProjektetPåListenOverProjekterMedLøbenummerOgÅrstal(String projektNavn, Integer løbe, Integer år) {
        Projekt p = this.system.findProjektMedNavn(projektNavn);

        assertTrue(p != null);
        assertTrue(Integer.parseInt(p.getProjektID().split("-")[0]) == år);
        assertTrue(Integer.parseInt(p.getProjektID().split("-")[1]) == løbe);
    }

    @Given("brugeren er logget ind som {string}")
    public void brugerenErLoggetIndSom(String string) {
        if (this.system.getMedarbejder().stream().noneMatch(m -> m.getInitial().equals(string))) {
            this.system.getMedarbejder().add(new Medarbejder(string));
        }
        this.system.login(string);
    }
    @Then("kan brugeren se en tom liste over projekter")
    public void kanBrugerenSeEnTomListeOverProjekter() {
        assertTrue(this.system.projekter.isEmpty());
    }
    @Then("kan brugeren se en fejlmeddelelse {string}")
    public void kanBrugerenSeEnFejlmeddelelse(String message) {
        assertTrue(message.equals(this.error.getMessage()));
    }

}
