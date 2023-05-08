package example.junit;

import internal.SystemAppException;
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
			Projekt p = systemApp.findProjektMedNavn("Projekt 1");
			Assert.assertNotNull(p);
			Projekt p2 = systemApp.findProjektMedNavn("Projekt 2");
			Assert.assertNull(p2);
			p.setNavn("Projekt 2");
			Projekt p3 = systemApp.findProjektMedNavn("Projekt 2");
			systemApp.lavAktivitet("akt 2", p);

			Assert.assertEquals(p, p3);
			Assert.assertNull(p.findMedarbejder("aaaa"));
			Assert.assertNull(p.findAktivitet("akt"));

			Assert.assertEquals(p, systemApp.findProjektMedID(p.getProjektID()));
			Assert.assertNull(systemApp.findProjektMedID("2023-02"));
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
			// Tilføj aktiviteter og medarbejdere
			systemApp.lavAktivitet("akt 1", projekt);
			systemApp.opretNyMedarbejder("bbbb");
			systemApp.tilføjMedarbejderTilProjekt(systemApp.findMedarbejder("bbbb"), projekt);
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
	public void testSletMedarbejder() throws SystemAppException {
		systemApp.opretNyMedarbejder("MEB");
		Medarbejder medarbejder = systemApp.findMedarbejder("MEB");

		// Tilføj aktiviter
		systemApp.lavNytProjekt("Nyt projekt");
		Projekt p = systemApp.findProjektMedNavn("Nyt projekt");
		Assert.assertNotNull(p);
		systemApp.tilføjMedarbejderTilProjekt(medarbejder, p);
		systemApp.sletMedarbejder(medarbejder);
	}

	@org.junit.Test()
	public void testGetterAndSetters() {
		systemApp.isAdmin();	
		systemApp.getUser();
		systemApp.fåProjekter();
		systemApp.fåMedarbejdere();
		systemApp.hvorMangeProjekter();
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

		systemApp.vælgProjektleder(projekt, null);
		Assert.assertTrue(systemApp.isProjektleder(projekt));

		systemApp.opretNyMedarbejder("aaaa");
		systemApp.login("aaaa");

		Assert.assertFalse(systemApp.isProjektleder(projekt));
	}
}
