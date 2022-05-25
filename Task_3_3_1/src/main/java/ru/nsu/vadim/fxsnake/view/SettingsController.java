package ru.nsu.vadim.fxsnake.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Provider;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SettingsController extends AbstractController implements Initializable {

    @FXML
    private Label foodsVal;
    @FXML
    private Slider foodsSlider;

    @FXML
    private VBox inGameMenu;

    @FXML
    private RadioButton goalBtn;
    @FXML
    private RadioButton unlimitedBtn;

    @FXML
    private Label scoreVal;

    @FXML
    private Slider scoreSlider;

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
    private VBox root;

    @Inject
    private Provider<MainViewController> mainViewControllerProvider;
    @Inject
    private Preferences preferences;

    public VBox getRoot() {
        return root;
    }

    @Override
    protected Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    public void closeWindow() {
        getStage().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();
        initValues();
        addEscapeKeyHandler();
    }

    private void addEscapeKeyHandler() {
        getRoot().sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        resume();
                    }
                });
            }
        });
    }

    private void initValues() {
        widthSlider.setValue(preferences.getInt("WIDTH", 25));
        heightSlider.setValue(preferences.getInt("HEIGHT", 25));
        scaleSlider.setValue(preferences.getDouble("SCALE", 1) * 100);
        scoreSlider.setValue(preferences.getInt("SCORE", 10));
        foodsSlider.setValue(preferences.getInt("FOODS", 10));

        if (preferences.getInt("SCORE", Integer.MAX_VALUE) == Integer.MAX_VALUE) {
            unlimitedBtn.setSelected(true);
        } else {
            goalBtn.setSelected(true);
        }
    }

    private void bindProperties() {
        inGameMenu.visibleProperty().bind(mainViewControllerProvider.get().gameRunningProperty());

        widthVal.setText(String.valueOf(widthSlider.getValue()));
        widthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            widthSlider.setValue(newValue.intValue());
            widthVal.setText(String.valueOf(newValue.intValue()));
            preferences.putInt("WIDTH", newValue.intValue());
        });

        heightVal.setText(String.valueOf(heightSlider.getValue()));
        heightSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            heightSlider.setValue(newValue.intValue());
            heightVal.setText(String.valueOf(newValue.intValue()));
            preferences.putInt("HEIGHT", newValue.intValue());
        });

        scaleVal.setText(String.valueOf(scaleSlider.getValue()));
        scaleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scaleSlider.setValue(newValue.intValue());
            scaleVal.setText(String.valueOf(newValue.doubleValue() / 100));
            preferences.putDouble("SCALE", newValue.doubleValue() / 100);
        });

        scoreVal.setText(String.valueOf(scoreSlider.getValue()));
        scoreSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            scoreSlider.setValue(newValue.intValue());
            scoreVal.setText(String.valueOf(newValue.intValue()));
            preferences.putInt("SCORE", newValue.intValue());
        });

        scoresOpt.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == goalBtn) {
                preferences.putInt("SCORE", (int) scoreSlider.getValue());
            } else {
                preferences.putInt("SCORE", Integer.MAX_VALUE);
            }
        });

        foodsVal.setText(String.valueOf(scoreSlider.getValue()));
        foodsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            foodsSlider.setValue(newValue.intValue());
            foodsVal.setText(String.valueOf(newValue.intValue()));
            preferences.putInt("FOODS", newValue.intValue());
        });
    }

    @FXML
    private void exitToMainMenu() {
        closeWindow();
        mainViewControllerProvider.get().switchToMainMenu();
    }

    @FXML
    private void resume() {
        closeWindow();
        mainViewControllerProvider.get().resumeGame();
    }

    @FXML
    private void restart() {
        closeWindow();
        mainViewControllerProvider.get().restartGame();
    }
}
