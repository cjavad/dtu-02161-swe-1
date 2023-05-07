package application;

import internal.Medarbejder;
import internal.Projekt;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

		ListView<Button> projektListe = new ListView<Button>();
		for (Projekt projekt : medarbejder.getProjekter()) {
			Button projektKnap = new Button(projekt.getNavn());
			projektKnap.setOnAction(e -> {
				app.visProjekt(projekt);
			});
			projektListe.getItems().add(projektKnap);
		}

		root.add(new Label("Projekter"), 0, 2);
		root.add(projektListe, 0, 3);

		return root;
	}
}
