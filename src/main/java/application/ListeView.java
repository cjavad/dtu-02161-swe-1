package application;

import internal.Medarbejder;
import internal.Projekt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ListeView {

    public HelloFX app;

    public ListView<Medarbejder> vs;
    public ListView<Medarbejder> hs;

    public Consumer<Medarbejder> moveLeft;
    public Consumer<Medarbejder> moveRight;
    public Set<Medarbejder> medarbejderList;
    public Set<Medarbejder> allMedarbejder;

    public GridPane root;

    public ListeView(HelloFX app, Set<Medarbejder> medarbejderList, Set<Medarbejder> allMedarbejder, Consumer<Medarbejder> moveLeft, Consumer<Medarbejder> moveRight) {
        this.app = app;
        this.medarbejderList = medarbejderList;
        this.allMedarbejder = allMedarbejder;

        this.moveLeft = moveLeft;
        this.moveRight = moveRight;

        init();
    }

    public void init() {
        this.root = new GridPane();

        this.vs = new ListView<>();
        this.hs = new ListView<>();

        this.vs.setMaxWidth(128);
        this.hs.setMaxWidth(128);

        this.root.setMaxWidth(128 + 128 + 50);

        for (Medarbejder medarbejder : this.allMedarbejder) {
            if (this.medarbejderList.contains(medarbejder)) {
                vs.getItems().add(medarbejder);
            } else {
                hs.getItems().add(medarbejder);
            }
        }

        Button leftButton = new Button("<<");
        leftButton.setMinWidth(50);
        leftButton.setOnAction((event) -> {
            if (this.hs.getSelectionModel().getSelectedItem() != null) {
                this.moveLeft.accept(this.hs.getSelectionModel().getSelectedItem());
            }
        });

        Button rightButton = new Button(">>");
        rightButton.setMinWidth(50);
        rightButton.setOnAction((event) -> {
            if (this.vs.getSelectionModel().getSelectedItem() != null) {
                this.moveRight.accept(this.vs.getSelectionModel().getSelectedItem());
            }
        });

        this.root.add(this.vs, 0, 0);

        GridPane buttons = new GridPane();
        buttons.add(leftButton, 0, 0);
        buttons.add(rightButton, 0, 1);
        root.add(buttons, 1, 0);

        this.root.add(this.hs, 2, 0);
    }


}
