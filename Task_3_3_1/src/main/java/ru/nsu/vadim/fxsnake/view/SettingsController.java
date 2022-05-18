package ru.nsu.vadim.fxsnake.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.inject.Inject;
import javax.inject.Provider;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends AbstractController implements Initializable {

    private static final String RESTART_WARNING = "Changes will be applied after restart";

    @FXML
    private ToggleGroup scoresOpt;
    @FXML
    private Label widthVal;
    @FXML
    private Slider widthSlider;
    @FXML
    private Label heightVal;
    @FXML
    private Slider heightSlider;
    @FXML
    private Label scaleVal;
    @FXML
    private Slider scaleSlider;
    @FXML
    private Label restartWarning;
    @FXML
    private VBox root;

    @Inject
    private Provider<MainViewController> mainViewControllerProvider;

    public VBox getRoot() {
        return root;
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    public void openWindow() {
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(Stage.getWindows().get(0));
        stage.setScene(
                getRoot().getScene() == null ? new Scene(getRoot()) : getRoot().getScene());
        stage.show();
    }

    public void closeWindow() {
        if (getStage() != null) {
            getStage().close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getRoot().sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        closeWindow();
                        mainViewControllerProvider.get().resumeGame();
                    }
                });
            }
        });
    }
}
