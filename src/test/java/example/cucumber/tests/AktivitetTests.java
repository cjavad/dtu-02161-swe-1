package example.cucumber.tests;

import example.cucumber.Aktiv;
import example.cucumber.ErrorMessage;
import internal.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class AktivitetTests {

    public SystemApp system;
    public Aktiv aktiv;
    public ErrorMessage error;

    public AktivitetTests(SystemApp system, Aktiv aktiv, ErrorMessage error) {
        this.system = system;
        this.aktiv = aktiv;
        this.error = error;
    }

    @Given("brugeren {string} findes")
    public void brugerenFindes(String string) {
        this.system.medarbejder.add(new Medarbejder(string));
    }
    @Given("Medarbejderen er logget ind som Softwarehuset A\\/S")
    public void medarbejderenErLoggetIndSomSoftwarehusetAS() {
        this.system.login("admin");
    }
    @Given("Medarbejderen opretter et projekt {string}")
    public void medarbejderenOpretterEtProjekt(String projektID) {
        // Check if projektID already exist
        this.system.projekter.stream().filter(p -> p.getProjektID().equals(projektID)).findFirst().ifPresentOrElse(
                p -> assertTrue(false),
                () -> this.system.projekter.add(new Projekt("", projektID))
        );
    }

    @Given("Medarbejderen tilknytter brugeren {string} som projektleder til {string}")
    public void medarbejderenTilknytterBrugerenSomProjektlederTil(String medarbejder, String projektID) {
        this.system.planner.ændreProjektleder(
                this.system.findProjektMedID(projektID),
                this.system.findMedarbejder(medarbejder)
        );
    }
    @Given("Medarbejderen opretter et projekt {string} \\(Uden projektleder)")
    public void medarbejderenOpretterEtProjektUdenProjektleder(String projektID) {
        this.system.projekter.stream().filter(p -> p.getProjektID().equals(projektID)).findFirst().ifPresentOrElse(
                p -> assertTrue(true), // Simply ignore
                () -> this.system.projekter.add(new Projekt("", projektID))
        );
    }

    @Given("Medarbejderen opretter en aktivitet {string} til projektet {string} med start tidspunktet {string} og slut tidspunktet {string}")
    public void medarbejderenOpretterEnAktivitetTilProjektetMedStartTidspunktetOgSlutTidspunktet(String aktivitet, String projektID, String start, String slut) {
        Aktivitet a = new Aktivitet(aktivitet);
        a.setStartDato(UgeDato.fromString(start));
        a.setSlutDato(UgeDato.fromString(slut));

        this.system.planner.tilføjAktivitetTilProjekt(
                a,
                this.system.findProjektMedID(projektID)
        );
    }
    @Given("Medarbejderen er logget ind som {string} på projektet {string}")
    public void medarbejderenErLoggetIndSomPåProjektet(String navn, String projektID) {
        this.system.login(navn);
        this.aktiv.setProjekt(this.system.findProjektMedID(projektID));
    }

    @When("medarbejderen prøver at slette aktiviteten {string}")
    public void medarbejderenPrøverAtSletteAktiviteten(String string) {
        try {
            this.system.sletAktivitet(this.aktiv.getProjekt().findAktivitet(string));
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }
    @Then("fejlbeskeden er givet {string}")
    public void fejlbeskedenErGivet(String string) {
        assertTrue(string.equals(this.error.getMessage()));
    }
    @Then("der er en aktivitet i projektet {string}")
    public void derErEnAktivitetIProjektet(String string) {
        assertTrue(this.system.findProjektMedID(string).getAktiviteter().size() == 1);
    }

    @Given("en medarbejder er logget ind som {string} på projektet {string}")
    public void enMedarbejderErLoggetIndSomPåProjektet(String string, String string2) {
        this.system.login(string);
        this.aktiv.setProjekt(this.system.findProjektMedID(string2));
    }
    @Then("er der ingen aktiviteter i projektet")
    public void erDerIngenAktiviteterIProjektet() {
       assertTrue(this.aktiv.getProjekt().getAktiviteter().isEmpty());
    }

    @When("medarbejderen prøver at oprette en aktivitet {string} på projekt {string} med start datoen {string} og slut datoen {string}")
    public void medarbejderenPrøverAtOpretteEnAktivitetMedStartDatoenOgSlutDatoen(String navn, String projekt, String start, String slut) {
        Aktivitet a = new Aktivitet(navn);
        a.setStartDato(UgeDato.fromString(start));
        a.setSlutDato(UgeDato.fromString(slut));

        try {
            this.system.lavAktivitet(navn, this.system.findProjektMedID(projekt));
        } catch (SystemAppException e) {
            this.error.setMessage(e.getMessage());
        }
    }

    @Then("er der {string} aktivitet i projektet {string}")
    public void erDerEnAktivitetIProjektet(String count, String arg1) {
        Integer c = Integer.valueOf(count);
        Projekt p = this.system.findProjektMedID(arg1);
        assertTrue(p != null);
        assertTrue(p.getAktiviteter().size() == c);
    }

    @Then("er der en aktivitet i projektet {string} ved navn {string}")
    public void erDerEnAktivitetIProjektetVedNavn(String arg0, String arg1) {
        assertTrue(this.system.findProjektMedID(arg0).findAktivitet(arg1) != null);
    }
}
