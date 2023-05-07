package application;

import internal.Medarbejder;
import internal.Projekt;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloFX extends Application {
	public Projekt projekt;
	public Medarbejder medarbejder;

	public Stage stage;
	public AppStage appStage = AppStage.Start;

    @Override
    public void start(Stage stage) {
		this.stage = stage;

		this.projekt = new Projekt("test", "someid42069");

		Medarbejder mag = new Medarbejder("MAG");
		projekt.tilføjMedarbejder(mag);
		projekt.setProjektLeder(mag);	

		appStage = AppStage.Projekt;
		visBrugerflade();
    }

	public Parent lavProjektBrugerflade() {
		ProjektUI ui = new ProjektUI(this, projekt);

        return ui.lavBrugerflade();  
	}

	public Parent lavMedarbejderBrugerflade() {
		MedarbejderUI ui = new MedarbejderUI(this, medarbejder);

		return ui.lavBrugerflade();  
	}

	public void visBrugerflade(Parent root) {
		Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
	}

	public void visBrugerflade() {
		switch (this.appStage) {
			case Start:
				break;
			case Projekt:
				visBrugerflade(lavProjektBrugerflade());
				break;
			case Medarbejder:
				visBrugerflade(lavMedarbejderBrugerflade());
				break;
		}
	}

	public void tilbage() {
		appStage = appStage.sidste();
		visBrugerflade();
	}

	public Button lavTilbageKnap() {
		Button tilbageKnap = new Button("Tilbage");

		tilbageKnap.setOnAction(e -> {
			tilbage();
			visBrugerflade();
		});

		return tilbageKnap;
	}

    public static void main(String[] args) {
        launch();
    }
}
