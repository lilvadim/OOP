package ru.nsu.vadim.fxsnake.view;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController extends AbstractController {

    private final BooleanProperty gameRunning = new SimpleBooleanProperty(false);

    @FXML
    private VBox root;
    @FXML
    private GameScreenController gameScreenController;
    @FXML
    private SettingsController settingsController;

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    @FXML
    private void switchAndStartGame() {
        getStage().getScene().setRoot(gameScreenController.getRoot());
        gameScreenController.start();
        gameRunning.set(true);
    }

    public void pauseGame() {
        gameScreenController.pause();
    }

    public void resumeGame() {
        gameScreenController.resume();
    }

    @FXML
    public void openSettings() {
        settingsController.openWindow();
    }

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

    public void restartGame() {
        gameScreenController.restart();
    }
}
