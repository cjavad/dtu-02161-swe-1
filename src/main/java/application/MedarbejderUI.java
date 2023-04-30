package application;

import internal.Medarbejder;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MedarbejderUI {
	public HelloFX app;
	public Medarbejder medarbejder;

	public MedarbejderUI(HelloFX app, Medarbejder medarbejder) {
		this.app = app;
		this.medarbejder = medarbejder;
	}

	public Parent lavBrugerflade() {
		GridPane root = new GridPane();

		root.add(app.lavTilbageKnap(), 0, 0);

		Label medarbejderInitial = new Label("Initial: " + medarbejder.getInitial());
		root.add(medarbejderInitial, 0, 1);

		return root;
	}
}
