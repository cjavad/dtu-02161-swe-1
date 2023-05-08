package example.junit;

import java.util.ArrayList;

import org.junit.Assert;

import internal.LedigAktivitetListeView;
import internal.Medarbejder;
import internal.UgeDato;
import javafx.util.Pair;

public class LedigAktivitetListeViewTests {
	@org.junit.Test()
	public void testOpdelPåBaggrundAfFritid() {
		Medarbejder medarbejder = new Medarbejder("MEB");

		Assert.assertThrows(AssertionError.class, () -> {
			new LedigAktivitetListeView(
				new Pair<>(null, new UgeDato(2018, 1)),
				new ArrayList<>(),
				new ArrayList<>()
			).opdelPåBaggrundAfFritid(medarbejder);
		});

		Assert.assertThrows(AssertionError.class, () -> {
			new LedigAktivitetListeView(
				new Pair<>(new UgeDato(2018, 1), null),
				new ArrayList<>(),
				new ArrayList<>()
			).opdelPåBaggrundAfFritid(medarbejder);
		});

		Assert.assertThrows(AssertionError.class, () -> {
			new LedigAktivitetListeView(
				new Pair<>(new UgeDato(2018, 1), new UgeDato(2017, 1)),
				new ArrayList<>(),
				new ArrayList<>()
			).opdelPåBaggrundAfFritid(medarbejder);
		});

		Assert.assertThrows(AssertionError.class, () -> {
			new LedigAktivitetListeView(
				new Pair<>(new UgeDato(2018, 1), new UgeDato(2019, 1)),
				new ArrayList<>(),
				new ArrayList<>()
			).opdelPåBaggrundAfFritid(null);
		});

		new LedigAktivitetListeView(
			new Pair<>(new UgeDato(2018, 1), new UgeDato(2019, 1)),
			new ArrayList<>(),
			new ArrayList<>()
		).opdelPåBaggrundAfFritid(medarbejder);
	}
}
