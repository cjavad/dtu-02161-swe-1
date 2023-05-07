package application;

import java.util.Optional;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.UgeDato;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

		VBox medarbejderBox = new VBox();
		ListView<Button> medarbejdeListe = new ListView<Button>();
		for (Medarbejder medarbejder : aktivitet.getAnførteMedarbejdere()) {
			Button medarbejderKnap = new Button(medarbejder.getInitial());
			medarbejderKnap.setOnAction(e -> {
				app.visMedarbejder(medarbejder);
			});
			medarbejdeListe.getItems().add(medarbejderKnap);
		}

		medarbejderBox.getChildren().add(new Label("Anførte medarbejdere"));
		medarbejderBox.getChildren().add(medarbejdeListe);

		HBox medarbejdereKnapBox = new HBox();
		Button tilføjMedarbejderKnap = new Button("Tilføj medarbejder");
		tilføjMedarbejderKnap.setOnAction(e -> {
			tilføjMedarbejderDialog();
		});
		medarbejdereKnapBox.getChildren().add(tilføjMedarbejderKnap);

		Button fjernMedarbejderKnap = new Button("Fjern medarbejder");
		fjernMedarbejderKnap.setOnAction(e -> {
			fjernMedarbejderDialog();
		});
		medarbejdereKnapBox.getChildren().add(fjernMedarbejderKnap);
		medarbejderBox.getChildren().add(medarbejdereKnapBox);

		VBox tidBox = new VBox();
		tidBox.getChildren().add(new Label("Budgetteret tid: " + aktivitet.getBugetteretTid()));

		Button setBugetteretTidKnap = new Button("Set budgetteret tid");
		setBugetteretTidKnap.setOnAction(e -> {
			setBugetteretTidDialog();
		});
		tidBox.getChildren().add(setBugetteretTidKnap);

		if (aktivitet.getStartDato() != null) {
			tidBox.getChildren().add(new Label("Start: " + aktivitet.getStartDato().toString()));
		} else {
			tidBox.getChildren().add(new Label("Start: Ikke sat"));
		}

		Button setStartTidKnap = new Button("Set start tidspunkt");
		setStartTidKnap.setOnAction(e -> {
			setStartTidDialog();
		});
		tidBox.getChildren().add(setStartTidKnap);

		if (aktivitet.getSlutDato() != null) {
			tidBox.getChildren().add(new Label("Slut: " + aktivitet.getSlutDato().toString()));
		} else {
			tidBox.getChildren().add(new Label("Slut: Ikke sat"));
		}

		Button setSlutTidKnap = new Button("Set slut tidspunkt");
		setSlutTidKnap.setOnAction(e -> {
			setSlutTidDialog();	
		});
		tidBox.getChildren().add(setSlutTidKnap);
	
		root.add(medarbejderBox, 0, 1);
		root.add(tidBox, 1, 1);

		return root;
	}

	public void setBugetteretTidDialog() {
		TextInputDialog dialog = new TextInputDialog("timer");
		dialog.setTitle("Budgetteret tid");
		dialog.setHeaderText("Budgetteret tid");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen tid sat").showAndWait();
			return;
		}

		try {
			aktivitet.setBugetteretTid(Integer.parseInt(result.get()));	
		} catch (Exception e) {		
			new Alert(Alert.AlertType.ERROR, e.toString()).showAndWait();
		}

		app.visBrugerflade();
	}

	public void setStartTidDialog() {
		TextInputDialog dialog = new TextInputDialog("uu-åååå");
		dialog.setTitle("Start tidspunkt");
		dialog.setHeaderText("Set start tidspunkt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen tid sat").showAndWait();
			return;
		}

		try {
			UgeDato dato = UgeDato.fromString(result.get());
			aktivitet.setStartDato(dato);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, e.toString()).showAndWait();
		}

		app.visBrugerflade();
	}

	public void setSlutTidDialog() {
		TextInputDialog dialog = new TextInputDialog("uu-åååå");
		dialog.setTitle("Slut tidspunkt");
		dialog.setHeaderText("Set slut tidspunkt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen tid sat").showAndWait();
			return;
		}

		try {
			UgeDato dato = UgeDato.fromString(result.get());
			aktivitet.setSlutDato(dato);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, e.toString()).showAndWait();
		}

		app.visBrugerflade();
	}

	public void tilføjMedarbejderDialog() {
		TextInputDialog dialog = new TextInputDialog("Initialer");
		dialog.setTitle("Tilføj medarbejder");
		dialog.setHeaderText("Tilføj medarbejder til aktivitet");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen medarbejder valgt").showAndWait();
			return;
		}

		Medarbejder medarbejder = app.system.findMedarbejder(result.get());

		if (medarbejder == null) {
			new Alert(Alert.AlertType.ERROR, "Medarbejder ikke fundet").showAndWait();
			return;
		}

		if (!aktivitet.getProjekt().getMedarbejder().contains(medarbejder)) {
			new Alert(Alert.AlertType.ERROR, "Medarbejder ikke tilknyttet projektet").showAndWait();
			return;
		}

		try {
			app.system.tilføjMedarbejderTilAktivitet(medarbejder, aktivitet);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			return;
		}

		app.visBrugerflade();
	}

	public void fjernMedarbejderDialog() {
		TextInputDialog dialog = new TextInputDialog("Initialer");
		dialog.setTitle("Fjern medarbejder");
		dialog.setHeaderText("Fjern medarbejder fra aktivitet");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
			new Alert(Alert.AlertType.ERROR, "Ingen medarbejder valgt").showAndWait();
			return;
		}

		Medarbejder medarbejder = app.system.findMedarbejder(result.get());

		if (medarbejder == null) {
			new Alert(Alert.AlertType.ERROR, "Medarbejder ikke fundet").showAndWait();
			return;
		}

		try {
		    app.system.fjernMedarbejderFraAktivitet(medarbejder, aktivitet);
		} catch (Exception e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			return;
		}

		app.visBrugerflade();
	}
}
