package example.junit;

import org.junit.Assert;

import internal.Aktivitet;
import internal.Medarbejder;

public class MedarbejderTests {
	@org.junit.Test
	public void testTilføjAktivitet() {
		Medarbejder medarbejder = new Medarbejder("Test");
		Aktivitet aktivitet = new Aktivitet("Test");

		Assert.assertThrows(AssertionError.class, () -> {
			medarbejder.tilføjAktivitet(aktivitet);
		});
	}

	@org.junit.Test
	public void testForekomsterAfAktivitet() {
		Medarbejder medarbejder = new Medarbejder("Test");

		Assert.assertThrows(AssertionError.class, () -> {
			medarbejder.forekomsterAfAktivitet(null);
		});
	}
}
