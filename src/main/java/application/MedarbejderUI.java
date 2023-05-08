package application;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

		ListView<Button> aktivitetListe = new ListView<Button>();
		for (Aktivitet aktivitet : medarbejder.getAnførteAktiviteter()) {
			Button aktivitetNavn = new Button(aktivitet.getNavn());
			aktivitetNavn.setOnAction(e -> {
				this.app.visAktivitet(aktivitet);
			});
			aktivitetListe.getItems().add(aktivitetNavn);
		}

		root.add(new Label("Aktiviteter"), 1, 2);
		root.add(aktivitetListe, 1, 3);

		return root;
	}
}
