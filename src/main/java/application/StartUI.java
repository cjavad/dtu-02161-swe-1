package application;

import java.util.Optional;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;
import internal.SystemAppException;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
			loginGrid.add(logout, 0, 0);
		} else if (app.system.user != null) {	
			loginGrid.add(new Label(app.system.user.getInitial()), 1, 0);
			loginGrid.add(logout, 0, 0);
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
		for (Projekt projekt : app.system.getProjekter()) {
			Button projektButton = new Button(projekt.getNavn());
			projektButton.setOnAction(e -> {
				app.visProjekt(projekt);
			});
			projektListe.getItems().add(projektButton);
		}

		dataGrid.add(new Label("Projekter"), 0, 0);
		dataGrid.add(projektListe, 0, 1);

		HBox projetkBox = new HBox();

		// nyt projekt knap
		Button nytProjekt = new Button("Nyt projekt");
		nytProjekt.setOnAction(e -> {
			nytProjektDialog();
		});

		Button sletProjekt = new Button("Slet projekt");
		sletProjekt.setOnAction(e -> {
			sletProjektDialog();
		});

		projetkBox.getChildren().add(nytProjekt);
		projetkBox.getChildren().add(sletProjekt);

		dataGrid.add(projetkBox, 0, 2);

		// medarbejder liste
		ListView<Button> medarbejderListe = new ListView<Button>();
		for (Medarbejder medarbejder : app.system.getMedarbejder()) {
			Button medarbejderButton = new Button(medarbejder.getInitial());
			medarbejderButton.setOnAction(e -> {
				app.visMedarbejder(medarbejder);
			});
			medarbejderListe.getItems().add(medarbejderButton);
		}

		dataGrid.add(new Label("Medarbejdere"), 1, 0);
		dataGrid.add(medarbejderListe, 1, 1);

		HBox medarbejderBox = new HBox();

		// ny medarbejder knap
		Button nyMedarbejder = new Button("Ny medarbejder");
		nyMedarbejder.setOnAction(e -> {
			nyMedarbejderDialog();
		});

		Button sletMedarbejder = new Button("Slet medarbejder");
		sletMedarbejder.setOnAction(e -> {
			sletMedarbejderDialog();
		});

		medarbejderBox.getChildren().add(nyMedarbejder);
		medarbejderBox.getChildren().add(sletMedarbejder);

		dataGrid.add(medarbejderBox, 1, 2);

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
			try { 
				app.system.lavNytProjekt(navn.get());
			} catch (Exception e) {
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
			if (!app.system.isAdmin()) {
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

	public void sletProjektDialog() {
		ChoiceDialog choiceDialog = new ChoiceDialog<Projekt>(
				null,
				this.app.system.getProjekter()
		);

		choiceDialog.setTitle("Slet projekt");

		Optional<Projekt> result = choiceDialog.showAndWait();

		if (!result.isPresent()) {
			return;
		}

		this.app.system.sletProjekt(result.get());

		this.app.visBrugerflade();
	}

	public void sletMedarbejderDialog() {
		ChoiceDialog choiceDialog = new ChoiceDialog<Medarbejder>(
				null,
				this.app.system.getMedarbejder()
		);

		choiceDialog.setTitle("Slet medarbejder");

		Optional<Medarbejder> result = choiceDialog.showAndWait();

		if (!result.isPresent()) {
			return;
		}

		this.app.system.sletMedarbejder(result.get());

		this.app.visBrugerflade();
	}
}
