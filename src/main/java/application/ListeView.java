package application;

import internal.LedigAktivitetListeView;
import internal.Medarbejder;
import internal.Projekt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ListeView {

    public HelloFX app;

    public ListView<Medarbejder> vs;
    public ListView<Label> hs;

    public LedigAktivitetListeView ledigAktivitetListeView;
    public Consumer<Medarbejder> moveLeft;
    public Consumer<Medarbejder> moveRight;
    public List<Medarbejder> medarbejderList;
    public List<Medarbejder> allMedarbejder;

    public GridPane root;

    public ListeView(HelloFX app, List<Medarbejder> medarbejderList, List<Medarbejder> allMedarbejder, Consumer<Medarbejder> moveLeft, Consumer<Medarbejder> moveRight) {
        this(app, medarbejderList, allMedarbejder, moveLeft, moveRight, null);
    }

    public ListeView(HelloFX app, List<Medarbejder> medarbejderList, List<Medarbejder> allMedarbejder, Consumer<Medarbejder> moveLeft, Consumer<Medarbejder> moveRight, LedigAktivitetListeView lw) {
        this.app = app;
        this.medarbejderList = medarbejderList;
        this.allMedarbejder = allMedarbejder;

        this.moveLeft = moveLeft;
        this.moveRight = moveRight;

        this.ledigAktivitetListeView = lw;
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
                var l = new Label(medarbejder.getInitial());

                if (this.ledigAktivitetListeView != null) {

                    var kategori = this.ledigAktivitetListeView.opdelPåBaggrundAfFritid(medarbejder);
                    // Select color based on kategori (A, B eller C) = (Green, Yellow, Red)
                    if (Objects.equals(kategori, "A")) {
                        l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.GREEN, null, null)));
                    } else if (Objects.equals(kategori, "B")) {
                        l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.YELLOW, null, null)));
                    } else if (Objects.equals(kategori, "C")) {
                        l.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.RED, null, null)));
                    }

                    l.setTextFill(javafx.scene.paint.Color.WHITE);
                }

                hs.getItems().add(l);
            }
        }

        Button leftButton = new Button("<<");
        leftButton.setMinWidth(50);
        leftButton.setOnAction((event) -> {
            if (this.hs.getSelectionModel().getSelectedItem() != null) {
                var a  = this.hs.getSelectionModel().getSelectedItem();
                this.moveLeft.accept(app.system.findMedarbejder(a.getText()));
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
