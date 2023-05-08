package example.junit;

import internal.Aktivitet;
import internal.Projekt;

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
}
