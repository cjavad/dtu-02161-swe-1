package application;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

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

		GridPane aktivitetInfo = new GridPane();

		root.add(aktivitetInfo, 2, 3);

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

		projektListe.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			aktivitetInfo.getChildren().clear();
		});

		aktivitetListe.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
			aktivitetInfo.getChildren().clear();
			aktivitetInfo.getChildren().add(new Label("Navn: " + newValue.getText()));
		});

		root.add(new Label("Aktiviteter"), 1, 2);
		root.add(aktivitetListe, 1, 3);

		// Create button to set medarbejder ugentlige timer in the buttom of the page and also show current amount of hours per week
		Button setUgentligeTimer = new Button("Set ugentlige timer");
		setUgentligeTimer.setOnAction(e -> {
			// Input integer from dialog
			TextInputDialog dialog = new TextInputDialog(String.valueOf(this.medarbejder.getUgentligeTimer()));
			dialog.setTitle("Indtast ugentlige timer");
			dialog.setHeaderText("Indtast ugentlige timer");

			Optional<String> result = dialog.showAndWait();

			if (!result.isPresent()) return;

			this.medarbejder.setUgentligeTimer(Integer.parseInt(result.get()));
			this.app.visBrugerflade();
			//int ugentligeTimer = app.visDialog("Indtast ugentlige timer", "Ugentlige timer");
			// Set medarbejder ugentlige timer
		});
		root.add(setUgentligeTimer, 0, 4);

		return root;
	}


}
