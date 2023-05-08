package application;

import internal.Projekt;

import java.awt.*;
import java.util.Optional;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.SystemAppException;
import io.cucumber.java.ht.Ak;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjektUI {
	public HelloFX app;
	public Projekt projekt;

	public ProjektUI(HelloFX app, Projekt projekt) {
		this.app = app;
		this.projekt = projekt;
	}

	public Parent lavBrugerflade() {
		GridPane root = new GridPane();

		root.add(app.lavTilbageKnap(), 0, 0);

		VBox infoBox = new VBox();
		infoBox.setMinWidth(128);

		// projekt info
		infoBox.getChildren().add(new Label("ID: " + projekt.getProjektID()));
		infoBox.getChildren().add(new Label("Navn: " + projekt.getNavn()));

		// projekt leder
		if (projekt.getProjektLeder() != null) {
			Label projektLeder = new Label("Projekt leder: " + projekt.getProjektLeder().getInitial());
			infoBox.getChildren().add(projektLeder);
		}

		Button vælgProjektLeder = new Button("Vælg projekt leder");
		vælgProjektLeder.setOnAction(e -> {
			vælgProjektLederDialog();
		});
		infoBox.getChildren().add(vælgProjektLeder);

		// medarbejder
		VBox medarbejderBox = new VBox();
		medarbejderBox.setMinWidth(256);

		ListeView mview = new ListeView(
				this.app,
				this.projekt.getMedarbejder(),
				this.app.system.getMedarbejder(),
				(m) -> {
					try {
						this.app.system.tilføjMedarbejderTilProjekt(m, this.projekt);
					} catch (SystemAppException e) {
						throw new RuntimeException(e);
					}
					this.app.visBrugerflade();
				},
				(m) -> {
					try {
						this.app.system.fjernMedarbejderFraProjekt(m, this.projekt);
					} catch (SystemAppException e) {
						throw new RuntimeException(e);
					}
					this.app.visBrugerflade();
				}
		);

		medarbejderBox.getChildren().add(new Label("Medarbejdere"));
		medarbejderBox.getChildren().add(mview.root);

		VBox aktivitetBox = new VBox();
		ListView<Button> aktivitetListe = new ListView<Button>();
		for (Aktivitet aktivitet : projekt.getAktiviteter()) {
			Button aktivitetNavn = new Button(aktivitet.getNavn());
			aktivitetNavn.setOnAction(e -> {
				app.visAktivitet(aktivitet);
			});
			aktivitetListe.getItems().add(aktivitetNavn);
		}

		aktivitetBox.getChildren().add(new Label("Aktiviteter"));
		aktivitetBox.getChildren().add(aktivitetListe);

		HBox aktivitetKnapBox = new HBox();

		Button tilføjAktivitet = new Button("Tilføj aktivitet");
		tilføjAktivitet.setOnAction(e -> {
			tilføjAktivitetDialog();
		});
		aktivitetKnapBox.getChildren().add(tilføjAktivitet);

		Button fjernAktivitet = new Button("Fjern aktivitet");
		fjernAktivitet.setOnAction(e -> {
			fjernAktivitetDialog();
		});
		aktivitetKnapBox.getChildren().add(fjernAktivitet);	
		aktivitetBox.getChildren().add(aktivitetKnapBox);

		root.add(infoBox, 0, 1);
		root.add(medarbejderBox, 1, 1);
		root.add(aktivitetBox, 2, 1);

		return root;
	}

	public void tilføjAktivitetDialog() {
		TextInputDialog dialog = new TextInputDialog("Aktivitet navn");
		dialog.setTitle("Tilføj aktivitet");
		dialog.setHeaderText("Tilføj aktivitet til projekt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			return;
		}

		try {
			this.app.system.lavAktivitet(result.get(), this.projekt);
		} catch (SystemAppException e) {
			throw new RuntimeException(e);
		}

		this.app.visBrugerflade();
	}

	public void fjernAktivitetDialog() {
		ChoiceDialog choiceDialog = new ChoiceDialog<Aktivitet>(
				null,
				this.projekt.getAktiviteter()
		);

		choiceDialog.setTitle("Fjern aktivitet");

		Optional<Aktivitet> result = choiceDialog.showAndWait();

		if (!result.isPresent()) {
			return;
		}

		try {
			this.app.system.sletAktivitet(result.get());
		} catch (SystemAppException e) {
			throw new RuntimeException(e);
		}

		this.app.visBrugerflade();
	}

	public void vælgProjektLederDialog() {
		ChoiceDialog choiceDialog = new ChoiceDialog<Medarbejder>(
				null,
				this.projekt.getMedarbejder()
		);

		choiceDialog.setTitle("Vælg projekt leder");

		Optional<Medarbejder> result = choiceDialog.showAndWait();

		if (!result.isPresent()) {
			return;
		}

		this.app.system.vælgProjektleder(this.projekt, result.get());

		this.app.visBrugerflade();
	}
}
