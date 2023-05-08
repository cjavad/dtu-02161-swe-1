package example.junit;

import org.junit.Assert;

import internal.Medarbejder;
import internal.Projekt;
import internal.SystemApp;

public class SystemAppTests {
	SystemApp systemApp;

	@org.junit.Before()
	public void testConstructor() {
		systemApp = new SystemApp();
		Assert.assertTrue(systemApp.login("admin"));
	}

	@org.junit.Test()
	public void testTilføjProjekt() {
		try {
			Assert.assertTrue(systemApp.lavNytProjekt("Projekt 1"));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@org.junit.Test()
	public void testSletProjekt() {
		try {
			Assert.assertTrue(systemApp.lavNytProjekt("Projekt 1"));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

		Projekt projekt = systemApp.findProjektMedNavn("Projekt 1");

		try {
			systemApp.sletProjekt(projekt);
		} catch (Exception e) {
			Assert.assertTrue(false);
		}
	}

	@org.junit.Test()
	public void testTilføjMedarbejder() {
		systemApp.opretNyMedarbejder("MEB");
	}

	@org.junit.Test()
	public void testSletMedarbejder() {
		systemApp.opretNyMedarbejder("MEB");
		Medarbejder medarbejder = systemApp.findMedarbejder("MEB");
		systemApp.sletMedarbejder(medarbejder);
	}

	@org.junit.Test()
	public void testGetterAndSetters() {
		systemApp.isAdmin();	
		systemApp.getUser();
		systemApp.getProjektListeView();
		systemApp.getProjekter();
		systemApp.getMedarbejder();
	}

	@org.junit.Test()
	public void testIsProjektLeder() {
		try {
			Assert.assertTrue(systemApp.lavNytProjekt("Projekt 1"));
		} catch (Exception e) {
			Assert.assertTrue(false);
		}

		Projekt projekt = systemApp.findProjektMedNavn("Projekt 1");
		Assert.assertTrue(systemApp.isProjektleder(projekt));	
	}
}
