package ru.nsu.vadim.fxsnake.view;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller, acts as mediator for other controllers
 */
public class MainViewController extends AbstractController implements Initializable {

    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);

    @FXML
    public VBox gameScreen;
    @FXML
    private VBox root;
    @FXML
    private GameScreenController gameScreenController;
    @FXML
    private SettingsController settingsController;
    @FXML
    private Stage settingsWindow;

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    /**
     * Switch scene of primary window and start game
     */
    @FXML
    private void switchAndStartGame() {
        gameScreen.setVisible(true);
        gameScreenController.start();
        gameRunning.set(true);
    }

    /**
     * Pause the game
     */
    public void pauseGame() {
        gameScreenController.pause();
    }

    /**
     * Resume the game
     */
    public void resumeGame() {
        gameScreenController.resume();
    }

    /**
     * Open settings window
     */
    @FXML
    public void openSettings() {
        settingsWindow.show();
    }

    /**
     * If game screen is opened
     */
    public boolean isGameRunning() {
        return gameRunning.get();
    }

    public BooleanProperty gameRunningProperty() {
        return gameRunning;
    }

    public void switchToMainMenu() {
        gameScreen.setVisible(false);
        gameScreenController.stop();
        gameRunning.set(false);
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    /**
     * Restart the game
     */
    public void restartGame() {
        gameScreenController.restart();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsWindow.initOwner(getStage());
        settingsWindow.initStyle(StageStyle.UNDECORATED);
    }
}
