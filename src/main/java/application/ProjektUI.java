package application;

import internal.Projekt;

import java.util.Optional;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.SystemAppException;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
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
		
		// medarbejdere
		VBox medarbejderBox = new VBox();
		ListView<Button> medarbejdeListe = new ListView<Button>();
		for (Medarbejder medarbejder : projekt.getMedarbejder()) {
			String label = medarbejder.getInitial();

			if (projekt.getProjektLeder() != null && 
				medarbejder.getInitial() == projekt.getProjektLeder().getInitial()) 
			{
				label += " (Projekt Leder)";
			}

			Button medarbejderInitial = new Button(label);
			medarbejderInitial.setOnAction(e -> {
				app.visMedarbejder(medarbejder);
			});
			medarbejdeListe.getItems().add(medarbejderInitial);
		}

		medarbejderBox.getChildren().add(new Label("Medarbejdere"));
		medarbejderBox.getChildren().add(medarbejdeListe);

		HBox medarbejderKnapBox = new HBox();	

		Button tilføjMedarbejder = new Button("Tilføj medarbejder");
		tilføjMedarbejder.setOnAction(e -> {
			tilføjMedarbejderDialog();
		});
		medarbejderKnapBox.getChildren().add(tilføjMedarbejder);

		Button fjernMedarbejder = new Button("Fjern medarbejder");
		fjernMedarbejder.setOnAction(e -> {
			fjernMedarbejderDialog();
		});
		medarbejderKnapBox.getChildren().add(fjernMedarbejder);
		medarbejderBox.getChildren().add(medarbejderKnapBox);

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

	public void tilføjMedarbejderDialog() {
		TextInputDialog dialog = new TextInputDialog("Medarbejder initial");
		dialog.setTitle("Tilføj medarbejder");
		dialog.setHeaderText("Tilføj medarbejder til projekt");

		DialogPane customDialogPane = dialog.getDialogPane();
		// Inject custom ListView content to select medarbejdere
		ListeView lw = new ListeView(app, projekt.getProjektID(), null);
		customDialogPane.getChildren().add(lw.lavBrugerflade());

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen medarbejder initial indtastet").showAndWait();
			return;
		}

		Medarbejder medarbejder = app.system.findMedarbejder(result.get());
		if (medarbejder != null) {
			try {
				app.system.tilføjMedarbejderTilProjekt(medarbejder, projekt);
			} catch (SystemAppException e) {
				new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			}
		} else {
			new Alert(Alert.AlertType.ERROR, "Medarbejder '" + result.get() + "' ikke fundet").showAndWait();
		}

		app.visBrugerflade();
	}

	public void fjernMedarbejderDialog() {
		TextInputDialog dialog = new TextInputDialog("Medarbejder initial");
		dialog.setTitle("Fjern medarbejder");
		dialog.setHeaderText("Fjern medarbejder fra projekt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen medarbejder initial indtastet").showAndWait();
			return;
		}

		Medarbejder medarbejder = app.system.findMedarbejder(result.get());

		try {
			app.system.fjernMedarbejderFraProjekt(medarbejder, projekt);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, "Medarbejder '" + result.get() + "' ikke fundet").showAndWait();
		}

		app.visBrugerflade();
	}

	public void tilføjAktivitetDialog() {
		TextInputDialog dialog = new TextInputDialog("Aktivitet navn");
		dialog.setTitle("Tilføj aktivitet");
		dialog.setHeaderText("Tilføj aktivitet til projekt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen aktivitet navn indtastet").showAndWait();
			return;
		}

		if (projekt.findAktivitet(result.get()) != null) {
			new Alert(Alert.AlertType.ERROR, "Aktivitet '" + result.get() + "' findes allerede").showAndWait();
			return;
		}

		Aktivitet aktivitet = new Aktivitet(result.get());
		aktivitet.setProjekt(projekt);
		projekt.tilføjAktivitet(aktivitet);

		app.visBrugerflade();
	}

	public void fjernAktivitetDialog() {
		TextInputDialog dialog = new TextInputDialog("Aktivitet navn");
		dialog.setTitle("Fjern aktivitet");
		dialog.setHeaderText("Fjern aktivitet fra projekt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen aktivitet navn indtastet").showAndWait();
			return;
		}

		Aktivitet aktivitet = projekt.findAktivitet(result.get());
		if (aktivitet != null) {
			projekt.fjernAktivitet(aktivitet);
		} else {
			new Alert(Alert.AlertType.ERROR, "Aktivitet '" + result.get() + "' ikke fundet").showAndWait();
		}

		app.visBrugerflade();
	}

	public void vælgProjektLederDialog() {
		TextInputDialog dialog = new TextInputDialog("Medarbejder initial");
		dialog.setTitle("Vælg projekt leder");
		dialog.setHeaderText("Vælg projekt leder");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen medarbejder initial indtastet").showAndWait();
			return;
		}

		Medarbejder medarbejder = app.system.findMedarbejder(result.get());
		if (medarbejder == null) {
			new Alert(Alert.AlertType.ERROR, "Medarbejder '" + result.get() + "' ikke fundet").showAndWait();
			return;
		}

		if (projekt.getMedarbejder().contains(medarbejder)) {
			new Alert(Alert.AlertType.ERROR, "Medarbejder '" + result.get() + "' er ikke medarbejder på projektet").showAndWait();
			return;
		}

		if (app.medarbejder == projekt.getProjektLeder() || projekt.getProjektLeder() == null || app.system.isAdmin) {
			projekt.setProjektLeder(medarbejder);
		} else {
			new Alert(Alert.AlertType.ERROR, "Du har ikke rettigheder til at ændre projekt leder").showAndWait();
		}

		app.visBrugerflade();
	}
}
