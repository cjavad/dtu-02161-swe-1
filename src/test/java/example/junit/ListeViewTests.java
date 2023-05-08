package	example.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import internal.ListeView;

public class ListeViewTests {
	ListeView<String> listeView;

	@org.junit.Before()
	public void testConstructor() {
		List<String> venstre = new ArrayList<>();
		List<String> højre = new ArrayList<>();

		venstre.add("A");
		venstre.add("B");

		højre.add("C");
		højre.add("D");

		listeView = new ListeView<>(venstre, højre);
	}

	@org.junit.Test()
	public void testFraVenstreTilHøjre() {
		listeView.fraVenstreTilHøjre("B");
		Assert.assertTrue(listeView.højreListe.contains("B"));
		Assert.assertFalse(listeView.venstreListe.contains("B"));
	}

	@org.junit.Test()
	public void testFraHøjreTilVenstre() {
		listeView.fraHøjreTilVenstre("C");
		Assert.assertTrue(listeView.venstreListe.contains("C"));
		Assert.assertFalse(listeView.højreListe.contains("C"));
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
		listeView.setValgteObjekt("A");
		Assert.assertTrue(listeView.getValgteObjekt().equals("A"));
	}

	@org.junit.Test()
	public void testSetHøjreListe() {
		List<String> højre = new ArrayList<>();
		højre.add("E");
		listeView.setHøjreListe(højre);
		Assert.assertTrue(listeView.getHøjreListe().equals(højre));
	}

	@org.junit.Test()
	public void testSetVenstreListe() {
		List<String> venstre = new ArrayList<>();
		venstre.add("F");
		listeView.setVenstreListe(venstre);
		Assert.assertTrue(listeView.getVenstreListe().equals(venstre));
	}
}
