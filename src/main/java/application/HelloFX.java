package application;


import internal.IDGenerator;
import internal.UgeDato;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloFX extends Application {
    public GridPane root;
    public IDGenerator idGenerator;

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Button b = new Button("Click me");
        b.setOnAction(e -> showID());
        idGenerator = new IDGenerator();
        root = new GridPane();
        root.add(l, 0, 0);
        root.add(b, 0, 1);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void showID () {
        // Update label with ID
        root.getChildren().stream().filter(n -> n instanceof Label).forEach(n -> {
            Label l = (Label) n;
            UgeDato dato = new UgeDato(2021, 1);
            l.setText("ID: " + idGenerator.getProjektID(dato));
        });
    }
}

