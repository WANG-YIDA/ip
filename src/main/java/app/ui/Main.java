package app.ui;

import java.io.IOException;

import app.Paradox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Paradox using FXML.
 */
public class Main extends Application {

    private Paradox paradox = new Paradox();
    private String taskListPath = "./data/taskList.txt";

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setParadox(paradox, taskListPath); // inject the Paradox instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
