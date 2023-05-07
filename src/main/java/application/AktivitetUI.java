package application;

import internal.Aktivitet;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class AktivitetUI {
	public HelloFX app;
	public Aktivitet aktivitet;

	public AktivitetUI(HelloFX app, Aktivitet aktivitet) {
		this.app = app;
		this.aktivitet = aktivitet;
	}

	public Parent lavBrugerflade() {
		GridPane root = new GridPane();

		root.add(app.lavTilbageKnap(), 0, 0);
		root.add(new Label("Navn: " + aktivitet.getNavn()), 1, 0);	

		return root;
	}
}
