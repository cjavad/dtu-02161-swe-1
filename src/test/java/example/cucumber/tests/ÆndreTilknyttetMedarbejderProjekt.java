package example.cucumber.tests;

import example.cucumber.Aktiv;
import example.cucumber.ErrorMessage;
import internal.Projekt;
import internal.SystemApp;
import internal.SystemAppException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ÆndreTilknyttetMedarbejderProjekt {
    public SystemApp system;
    public Aktiv aktiv;
    public ErrorMessage error;

    public ÆndreTilknyttetMedarbejderProjekt(SystemApp system, Aktiv aktiv, ErrorMessage error) {
        this.system = system;
        this.aktiv = aktiv;
        this.error = error;
    }

    @Given("en medarbejder {string} eksistere")
    public void enMedarbejderEksistere(String arg0) {
        this.system.login("admin");
        this.system.opretNyMedarbejder(arg0);
    }

    @And("projektet {string} eksistere")
    public void projektetEksistere(String arg0) {
        this.system.fåProjekter().add(new Projekt("", arg0));
    }

    @And("medarbejderen {string} er tilknyttet projektet {string}")
    public void medarbejderenErTilknyttetProjektet(String arg0, String arg1) {
        try {
            this.system.tilføjMedarbejderTilProjekt(this.system.findMedarbejder(arg0), this.system.findProjektMedID(arg1));
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }

    @And("medarbejderen {string} er projektleder for projektet {string}")
    public void medarbejderenErProjektlederForProjektet(String arg0, String arg1) {
        this.system.findProjektMedID(arg1).setProjektLeder(this.system.findMedarbejder(arg0));
    }

    @When("medarbejderen tilknytter bruger {string} til projektet {string}")
    public void medarbejderenTilknytterBrugerTilProjektet(String arg0, String arg1) {
        try {
            this.system.tilføjMedarbejderTilProjekt(this.system.findMedarbejder(arg0), this.system.findProjektMedID(arg1));
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }

    @Then("medarbejderene {string}, {string}, {string} er tilknyttet projektet {string}")
    public void medarbejdereneErTilknyttetProjektet(String arg0, String arg1, String arg2, String arg3) {
        Projekt p = this.system.findProjektMedID(arg3);
        assertTrue( p.findMedarbejder(arg0) != null );
        assertTrue( p.findMedarbejder(arg1) != null );
        assertTrue( p.findMedarbejder(arg2) != null );
    }

    @Given("medarbejderen er logget ind som {string} på {string}")
    public void medarbejderenErLoggetIndSomPa(String arg0, String arg1) {
        this.system.login(arg0);
        this.aktiv.setProjekt(this.system.findProjektMedID(arg1));
    }

    @When("medarbejderen tilknytter bruger {string} til projektet")
    public void medarbejderenTilknytterBrugerTilProjektet(String arg0) {
        try {
            this.system.tilføjMedarbejderTilProjekt(this.system.findMedarbejder(arg0), this.aktiv.getProjekt());
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }

    @Then("medarbejderene {string}, {string} er tilknyttet projektet {string}")
    public void medarbejdereneErTilknyttetProjektet(String arg0, String arg1, String arg2) {
        Projekt p = this.system.findProjektMedID(arg2);
        assertTrue( p.findMedarbejder(arg0) != null );
        assertTrue( p.findMedarbejder(arg1) != null );
    }

    @And("der er ingen projektleder for projektet {string}")
    public void derErIngenProjektlederForProjektet(String arg0) {
        assertTrue( this.system.findProjektMedID(arg0).getProjektLeder() == null );
    }

    @Then("medarbejderen bliver vist en fejlmeddelelse {string}")
    public void medarbejderenBliverVistEnFejlmeddelelse(String arg0) {
        assertEquals(arg0, this.error.getMessage());
    }
}
