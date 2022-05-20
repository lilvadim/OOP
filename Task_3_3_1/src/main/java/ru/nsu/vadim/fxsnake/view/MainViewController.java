package ru.nsu.vadim.fxsnake.view;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Main controller, acts as mediator for other controllers
 */
public class MainViewController extends AbstractController {

    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);

    @FXML
    private VBox root;
    @FXML
    private GameScreenController gameScreenController;
    @FXML
    private SettingsController settingsController;

    @Override
    protected Optional<Stage> getStage() {
        return Optional.ofNullable((Stage) root.getScene().getWindow());
    }

    /**
     * Switch scene of primary window and start game
     */
    @FXML
    private void switchAndStartGame() {
        getStage().orElseThrow().getScene().setRoot(gameScreenController.getRoot());
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
        settingsController.openWindow();
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
        Stage.getWindows().get(0).getScene().setRoot(root);
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
}
