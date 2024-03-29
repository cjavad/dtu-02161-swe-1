package example.junit;

import org.junit.Assert;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;
import internal.UgeDato;

public class AktivitetTests {
	Aktivitet aktivitet;

	@org.junit.Before()
	public void testConstructor() {
		aktivitet = new Aktivitet("Aktivitet 1");
	}

	@org.junit.Test()
	public void testGettersAndSetters() {
		aktivitet.getProjekt();
		aktivitet.setProjekt(new Projekt("Projekt 1", "et id"));
		aktivitet.getBugetteretTid();
		aktivitet.toString();
	}

	@org.junit.Test()
	public void testTilføjMedarbejder() {
		Assert.assertThrows(AssertionError.class, () -> {
		    aktivitet.tilføjMedarbjeder(null);
		});

		Medarbejder medarbejder = new Medarbejder("MEB");
		Assert.assertThrows(AssertionError.class, () -> {
		    aktivitet.tilføjMedarbjeder(medarbejder);
		});

		Projekt projekt = new Projekt("Projekt 1", "et id");

		Assert.assertThrows(AssertionError.class, () -> {
		    aktivitet.tilføjMedarbjeder(medarbejder);
		});

		aktivitet.setProjekt(projekt);
		projekt.tilføjMedarbejder(medarbejder);
		aktivitet.tilføjMedarbjeder(medarbejder);

		Assert.assertTrue(aktivitet.getAnførteMedarbejdere().contains(medarbejder));
		Assert.assertTrue(projekt.findMedarbejder(medarbejder.toString()).equals(medarbejder));
	}

	@org.junit.Test()
	public void testIsLegalDatoAssignment() {
		UgeDato startdato = new UgeDato(2017, 1);
		UgeDato slutdato = new UgeDato(2018, 1);
		Assert.assertTrue(aktivitet.isLegalDatoAssignment(startdato, slutdato));
		Assert.assertFalse(aktivitet.isLegalDatoAssignment(slutdato, startdato));
		Assert.assertEquals(0, aktivitet.beregnArbejdePerMedarbejder());

		aktivitet.setStartDato(slutdato);
		Assert.assertThrows(Error.class, () -> aktivitet.setSlutDato(startdato));
		aktivitet.setSlutDato(slutdato);
		Assert.assertThrows(Error.class, () -> aktivitet.setStartDato(new UgeDato(2019, 1)));
	}
}
