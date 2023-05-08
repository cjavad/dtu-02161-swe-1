package application;

import internal.Aktivitet;
import internal.LedigAktivitetListeView;
import internal.Medarbejder;
import internal.Projekt;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;

public class ListeView {

    public HelloFX app;
    public LedigAktivitetListeView ledigAktivitetListeView;
    public BorderPane root;

    public ListeView(HelloFX app, String projektID, String aktivitetNavn) {
        this.app = app;

        Projekt projekt = app.system.findProjektMedID(projektID);
        Aktivitet a = projekt.findAktivitet(aktivitetNavn);

        this.ledigAktivitetListeView = new LedigAktivitetListeView(new Pair<>(a.getStartDato(), a.getSlutDato()),
                new ArrayList<>(app.system.medarbejder),
                new ArrayList<>(a.getAnførteMedarbejdere()));
    }

    public Parent lavBrugerflade() {
        // Create a windows style two list view with arrows to move items between the lists
        root = new BorderPane();

        // Create two list views
        // Create two buttons to move items between the lists
        // Add the list views and buttons to the root pane

        ListView<Label> leftList = new ListView<>();
        ListView<Label> rightList = new ListView<>();

        root.setLeft(leftList);
        root.setRight(rightList);

        GridPane buttonPane = new GridPane();
        root.setCenter(buttonPane);

        Button leftButton = new Button("<");
        Button rightButton = new Button(">");

        buttonPane.add(leftButton, 0, 0);
        buttonPane.add(rightButton, 0, 1);

        return root;
    }

    public Label constructLabelForMedarbejder(Medarbejder m) {
        return new Label(m.getInitial());
    }

    public void sync() {
        // Sync the list views with the model
        var højreListe = ledigAktivitetListeView.getHøjreListe();
        var venstreListe = ledigAktivitetListeView.getVenstreListe();

        var leftList = (ListView<Label>) root.getLeft();
        var rightList = (ListView<Label>) root.getRight();

        for (Medarbejder m : højreListe) {
            rightList.getItems().add(constructLabelForMedarbejder(m));
        }

        for (Medarbejder m : venstreListe) {
            leftList.getItems().add(constructLabelForMedarbejder(m));
        }
    }
}
