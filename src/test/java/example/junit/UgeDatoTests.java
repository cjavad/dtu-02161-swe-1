package example.junit;

import org.junit.Assert;

import internal.UgeDato;

public class UgeDatoTests {
	UgeDato ugeDato;

	@org.junit.Before()
	public void testConstructor() {
		ugeDato = new UgeDato(2017, 1);
	}

	@org.junit.Test()
	public void testGettersAndSetters() {
		ugeDato.getUge();
		ugeDato.getÅrstal();
		ugeDato.setUge(2);
		ugeDato.setÅrstal(2018);
		Assert.assertTrue(ugeDato.toString().equals("2-2018"));
	}
}
