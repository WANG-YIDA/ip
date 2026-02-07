package app.ui;

import app.Paradox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Paradox paradox;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image paradoxImage = new Image(this.getClass().getResourceAsStream("/images/paradox.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Paradox instance */
    public void setParadox(Paradox paradox, String taskListPath) {
        this.paradox = paradox;
        String initMsg = this.paradox.initialize(taskListPath);
        dialogContainer.getChildren().addAll(
                DialogBox.getParadoxDialog(initMsg, paradoxImage)
        );
    }

    /**
     * Creates two dialog boxes: one echoing user input, and one containing Paradox's reply.
     * Appends both to the dialog container and clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = paradox.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getParadoxDialog(response, paradoxImage)
        );
        userInput.clear();
    }
}
