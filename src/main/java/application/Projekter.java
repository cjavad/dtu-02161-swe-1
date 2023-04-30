package application;

import internal.Projekt;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Projekter {
	public GridPane root;
	public System system;

	public Projekter(System system) {
		this.root = new GridPane();
		this.system = system;	
	}

	public Parent lavBrugerflade() {
		int index = 0;
		for (Projekt projekt : system.projekter) {
			Button projektKnap = new Button(projekt.getProjektID());
			root.add(projektKnap, 0, index);

			index += 1;
		}

		return root;
	}
}
