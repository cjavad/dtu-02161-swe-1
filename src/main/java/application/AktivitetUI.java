package application;

import java.util.Optional;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.SystemAppException;
import internal.UgeDato;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
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

		// medarbejdere
		VBox medarbejderBox = new VBox();
		medarbejderBox.setMinWidth(256);

		ListeView mview = new ListeView(
				this.app,
				this.aktivitet.getAnførteMedarbejdere(),     // TODO :: fixme
				this.aktivitet.getProjekt().getMedarbejder(), // TODO :: fixme
				(m) -> {
					try {
						this.app.system.tilføjMedarbejderTilAktivitet(m, this.aktivitet);
					} catch (SystemAppException e) {
						throw new RuntimeException(e);
					}
					this.app.visBrugerflade();
				},
				(m) -> {
					try {
						this.app.system.fjernMedarbejderFraAktivitet(m, this.aktivitet);
					} catch (SystemAppException e) {
						throw new RuntimeException(e);
					}
					this.app.visBrugerflade();
				}
		);

		medarbejderBox.getChildren().add(new Label("Medarbejdere"));
		medarbejderBox.getChildren().add(mview.root);


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
		if (!this.app.system.isProjektleder(aktivitet.getProjekt())) return;

		TextInputDialog dialog = new TextInputDialog("timer");
		dialog.setTitle("Budgetteret tid");
		dialog.setHeaderText("Budgetteret tid");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
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
		if (!this.app.system.isProjektleder(aktivitet.getProjekt())) return;

		TextInputDialog dialog = new TextInputDialog("uu-åååå");
		dialog.setTitle("Start tidspunkt");
		dialog.setHeaderText("Set start tidspunkt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
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
		if (!this.app.system.isProjektleder(aktivitet.getProjekt())) return;


		TextInputDialog dialog = new TextInputDialog("uu-åååå");
		dialog.setTitle("Slut tidspunkt");
		dialog.setHeaderText("Set slut tidspunkt");

		Optional<String> result = dialog.showAndWait();

		if (!result.isPresent()) {
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
}
