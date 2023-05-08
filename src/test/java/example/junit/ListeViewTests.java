package	example.junit;

import java.util.ArrayList;
import java.util.List;

import internal.LedigAktivitetListeView;
import internal.Medarbejder;
import org.junit.Assert;


public class ListeViewTests {
	LedigAktivitetListeView listeView;


	Medarbejder mA = new Medarbejder("A");
	Medarbejder mB = new Medarbejder("B");
	Medarbejder mC = new Medarbejder("C");
	Medarbejder mD = new Medarbejder("D");

	@org.junit.Before()
	public void testConstructor() {
		List<Medarbejder> venstre = new ArrayList<>();
		List<Medarbejder> højre = new ArrayList<>();

		venstre.add(mA);
		venstre.add(mB);

		højre.add(mC);
		højre.add(mD);

		listeView = new LedigAktivitetListeView(null, venstre, højre);
	}

	@org.junit.Test()
	public void testFraVenstreTilHøjre() {
		listeView.fraVenstreTilHøjre(mB);
		Assert.assertTrue(listeView.højreListe.contains(mB));
		Assert.assertFalse(listeView.venstreListe.contains(mB));
	}

	@org.junit.Test()
	public void testFraHøjreTilVenstre() {
		listeView.fraHøjreTilVenstre(mC);
		Assert.assertTrue(listeView.venstreListe.contains(mC));
		Assert.assertFalse(listeView.højreListe.contains(mC));
	}

	@org.junit.Test()
	public void testGetHøjreListe() {
		Assert.assertTrue(listeView.getHøjreListe().equals(listeView.højreListe));
	}

	@org.junit.Test()
	public void testGetVenstreListe() {
		Assert.assertTrue(listeView.getVenstreListe().equals(listeView.venstreListe));
	}

	@org.junit.Test()
	public void testValgteObjekt() {
		listeView.setValgteObjekt(mA);
		Assert.assertTrue(listeView.getValgteObjekt().equals(mA));
	}

	@org.junit.Test()
	public void testSetHøjreListe() {
		List<Medarbejder> højre = new ArrayList<>();
		højre.add(new Medarbejder("E"));
		listeView.setHøjreListe(højre);
		Assert.assertTrue(listeView.getHøjreListe().equals(højre));
	}

	@org.junit.Test()
	public void testSetVenstreListe() {
		List<Medarbejder> venstre = new ArrayList<>();
		venstre.add(new Medarbejder("F"));
		listeView.setVenstreListe(venstre);
		Assert.assertTrue(listeView.getVenstreListe().equals(venstre));
	}
}
