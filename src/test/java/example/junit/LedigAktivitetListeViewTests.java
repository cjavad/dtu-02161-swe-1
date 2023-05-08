package example.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@org.junit.Test()
	public void sortTest() {
		List<Medarbejder> medarbejdere = new ArrayList<>();
		Medarbejder a = new Medarbejder("A");
		Medarbejder b = new Medarbejder("B");
		medarbejdere.add(a);
		medarbejdere.add(b);
		b.setUgentligeTimer(1);

		LedigAktivitetListeView view = new LedigAktivitetListeView(
			new Pair<>(new UgeDato(2018, 1), new UgeDato(2019, 1)),
			new ArrayList<>(medarbejdere),
			new ArrayList<>()
		);

		Assert.assertEquals(a, view.getHøjreListe().get(0));
		Assert.assertEquals(b, view.getHøjreListe().get(1));

		view.sorterHøjreListe();

		Assert.assertEquals(b, view.getHøjreListe().get(0));
		Assert.assertEquals(a, view.getHøjreListe().get(1));
	}

	@org.junit.Test()
	public void fritidKategorier() {
		LedigAktivitetListeView view = new LedigAktivitetListeView(
				new Pair<>(new UgeDato(2018, 1), new UgeDato(2019, 1)),
				new ArrayList<>(),
				new ArrayList<>()
		);

		List<Integer> exampleA = Arrays.asList(1, 1, 1);
		List<Integer> exampleB = Arrays.asList(0, 1, 1);
		List<Integer> exampleC = Arrays.asList(0, 0, 0);

		Assert.assertTrue(view.isC(exampleC));
		Assert.assertTrue(view.isB(exampleB));
		Assert.assertTrue(view.isA(exampleA));

		Assert.assertFalse(view.isC(exampleB));
		Assert.assertFalse(view.isB(exampleC));
		Assert.assertFalse(view.isA(exampleB));
	}
}
