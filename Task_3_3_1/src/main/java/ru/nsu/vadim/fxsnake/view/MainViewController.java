package ru.nsu.vadim.fxsnake.view;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController extends AbstractController {

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
}
