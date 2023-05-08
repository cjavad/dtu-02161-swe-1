package application;

import internal.Aktivitet;
import internal.Medarbejder;
import internal.Projekt;
import internal.SystemApp;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;

public class HelloFX extends Application {
	public SystemApp system;
	public Projekt projekt;
	public Medarbejder medarbejder;
	public Aktivitet aktivitet;

	public Stage stage;
	public AppStage appStage = AppStage.Start;

    @Override
    public void start(Stage stage) {
		this.stage = stage;

		// Try to read './system.ser' and deserialize it into this.system
		ObjectInputStream in = null;

		try {
			FileInputStream fileIn = new FileInputStream("./system.ser");
			in = new ObjectInputStream(fileIn);
			this.system = (SystemApp) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception ignored) {
			this.system = new SystemApp();
		}

		appStage = AppStage.Start;
		visBrugerflade();
    }

	public void stop() {
		// Try to write this.system to './system.ser'
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./system.ser"));
			out.writeObject(this.system);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Parent lavStartBrugerflade() {
		StartUI ui = new StartUI(this);
		return ui.lavBrugerflade();  
	}

	public Parent lavProjektBrugerflade() {
		ProjektUI ui = new ProjektUI(this, projekt);
        return ui.lavBrugerflade();  
	}

	public Parent lavMedarbejderBrugerflade() {
		MedarbejderUI ui = new MedarbejderUI(this, medarbejder);
		return ui.lavBrugerflade();  
	}

	public Parent lavAktivitetBrugerflade() {
		AktivitetUI ui = new AktivitetUI(this, aktivitet);
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
				visBrugerflade(lavStartBrugerflade());
				break;
			case Projekt:
				visBrugerflade(lavProjektBrugerflade());
				break;
			case Medarbejder:
				visBrugerflade(lavMedarbejderBrugerflade());
				break;
			case Aktivitet:
				visBrugerflade(lavAktivitetBrugerflade());
				break;
		}
	}

	public void visProjekt(Projekt projekt) {
		this.projekt = projekt;
		appStage = AppStage.Projekt;
		visBrugerflade();
	}

	public void visMedarbejder(Medarbejder medarbejder) {
		this.medarbejder = medarbejder;
		appStage = AppStage.Medarbejder;
		visBrugerflade();
	}

	public void visAktivitet(Aktivitet aktivitet) {
		this.aktivitet = aktivitet;
		appStage = AppStage.Aktivitet;
		visBrugerflade();
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
