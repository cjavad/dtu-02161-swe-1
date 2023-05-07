package application;

import java.util.Optional;

import internal.Medarbejder;
import internal.Projekt;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

public class StartUI {
	public HelloFX app;

	public StartUI(HelloFX app) {
		this.app = app;
	}

	public Parent lavBrugerflade() {
		GridPane root = new GridPane();

		root.add(lavLoginBrugerflade(), 0, 0);
		root.add(lavDataBrugerflade(), 0, 1);

		return root;
	}

	public Parent lavLoginBrugerflade() {
		GridPane loginGrid = new GridPane();

		Button logout = new Button("Logout");

		logout.setOnAction(e -> {
			app.system.logout();
			app.visBrugerflade();
		});

		if (app.system.isAdmin) {
			loginGrid.add(new Label("admin"), 1, 0);
			loginGrid.add(logout, 2, 0);	
		} else if (app.system.user != null) {	
			loginGrid.add(new Label(app.system.user.getInitial()), 0, 0);
			loginGrid.add(logout, 1, 0);	
		} else {
			Button login = new Button("Login");

			login.setOnAction(e -> {
				loginDialog();
			});

			loginGrid.add(login, 0, 0);
		}

		return loginGrid;
	}
	
	public Parent lavDataBrugerflade() {
		GridPane dataGrid = new GridPane();

		// projekt liste
		ListView<Button> projektListe = new ListView<Button>();
		for (Projekt projekt : app.system.projekter) {
			Button projektButton = new Button(projekt.getNavn());
			projektButton.setOnAction(e -> {
				app.visProjekt(projekt);
			});
			projektListe.getItems().add(projektButton);
		}

		dataGrid.add(new Label("Projekter"), 0, 0);
		dataGrid.add(projektListe, 0, 1);

		// nyt projekt knap
		Button nytProjekt = new Button("Nyt projekt");
		nytProjekt.setOnAction(e -> {
			nytProjektDialog();
		});
		dataGrid.add(nytProjekt, 0, 2);

		// medarbejder liste
		ListView<Button> medarbejderListe = new ListView<Button>();
		for (Medarbejder medarbejder : app.system.medarbejder) {
			Button medarbejderButton = new Button(medarbejder.getInitial());
			medarbejderButton.setOnAction(e -> {
				app.visMedarbejder(medarbejder);
			});
			medarbejderListe.getItems().add(medarbejderButton);
		}

		dataGrid.add(new Label("Medarbejdere"), 1, 0);
		dataGrid.add(medarbejderListe, 1, 1);

		// ny medarbejder knap
		Button nyMedarbejder = new Button("Ny medarbejder");
		nyMedarbejder.setOnAction(e -> {
			nyMedarbejderDialog();
		});
		dataGrid.add(nyMedarbejder, 1, 2);

		return dataGrid;
	}

	public void loginDialog() {
		TextInputDialog initialInput = new TextInputDialog("Initialer");
		initialInput.setTitle("Login");
		initialInput.setHeaderText("Login");

		Optional<String> initial = initialInput.showAndWait();

		if (initial.isPresent()) {
			if (!app.system.login(initial.get())) {
				new Alert(Alert.AlertType.ERROR, "Initialer findes ikke").showAndWait();
			}
		}

		app.visBrugerflade();
	}

	public void nytProjektDialog() {
		TextInputDialog navnInput = new TextInputDialog("Navn");
		navnInput.setTitle("Projekt");
		navnInput.setHeaderText("Nyt projekt");

		Optional<String> navn = navnInput.showAndWait();

		if (navn.isPresent()) {	
			if (!app.system.lavNytProjekt(navn.get(), null)) {
				new Alert(Alert.AlertType.ERROR, "Kunne ikke oprette projekt").showAndWait();
			}
		}

		app.visBrugerflade();
	}

	public void nyMedarbejderDialog() {
		TextInputDialog initialInput = new TextInputDialog("Initialer");
		initialInput.setTitle("Medarbejder");
		initialInput.setHeaderText("Ny medarbejder");

		Optional<String> initial = initialInput.showAndWait();

		if (initial.isPresent()) {
			if (!app.system.isAdmin) {
				new Alert(Alert.AlertType.ERROR, "Kun admin kan oprette medarbejdere").showAndWait();
				return;
			}

			if (app.system.findMedarbejder(initial.get()) == null) {
				app.system.opretNyMedarbejder(initial.get());
			} else {	
				new Alert(Alert.AlertType.ERROR, "Initialer findes allerede").showAndWait();
			}
		}

		app.visBrugerflade();
	}
}
