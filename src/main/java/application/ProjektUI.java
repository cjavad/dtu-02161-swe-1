package application;

import internal.Projekt;
import internal.Medarbejder;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

		Label projektId = new Label("Projekt id: " + projekt.getProjektID());
		root.add(projektId, 0, 1);

		// NOTE: kan ikke implementere projekt navn eller år, da projekt klassen ikke indeholder dem
		
		GridPane medarbejdere = new GridPane();

		medarbejdere.add(new Label("Medarbejdere"), 0, 0);

		GridPane medarbejdeListe = new GridPane();

		int index = 0;
		for (Medarbejder medarbejder : projekt.getMedarbejder()) {
			String label = medarbejder.getInitial();

			if (projekt.getProjektLeder() != null && 
				medarbejder.getInitial() == projekt.getProjektLeder().getInitial()) 
			{
				label += " (Projekt Leder)";
			}

			Button medarbejderInitial = new Button(label);

			medarbejderInitial.setOnAction(e -> {
				vælgMedarbejder(medarbejder);
			});

			medarbejdeListe.add(medarbejderInitial, 0, index);


			index += 1;
		}

		medarbejdere.add(medarbejdeListe, 0, 1);

		root.add(medarbejdere, 0, 2);

		return root;
	}

	public void vælgMedarbejder(Medarbejder medarbejder) {
		app.medarbejder = medarbejder;
		app.appStage = AppStage.Medarbejder;
		app.visBrugerflade();
	}
}
