package ru.nsu.vadim.fxsnake.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.nsu.vadim.fxsnake.GameLoopTimer;
import ru.nsu.vadim.snake.Field;
import ru.nsu.vadim.snake.Snake;
import ru.nsu.vadim.snake.SpeedVector;
import ru.nsu.vadim.snake.XYPair;
import ru.nsu.vadim.snake.point.EnvironmentPoint;
import ru.nsu.vadim.snake.point.FoodPointType;
import ru.nsu.vadim.snake.point.Point;

import javax.inject.Inject;
import javax.inject.Provider;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class GameScreenController extends AbstractController implements Initializable {

    private final Field field;
    private final Snake snake;
    private final GameLoopTimer gameLoopTimer;
    private final IntegerProperty scoreProperty = new SimpleIntegerProperty(0);

    @FXML
    private Label scoreVal;
    @FXML
    private VBox root;
    @FXML
    private Pane container;

    @Inject
    private Provider<MainViewController> mainViewControllerProvider;
    @Inject
    private Preferences preferences;
    private Point currentFoodPoint;
    private double scale;


    @Inject
    public GameScreenController(
            Field field,
            Snake snake,
            GameLoopTimer gameLoopTimer) {
        this.field = field;
        this.snake = snake;
        this.gameLoopTimer = gameLoopTimer;

        currentFoodPoint = field.generateFoodPoint();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initConfig();

        scoreVal.textProperty().bind(scoreProperty.asString());

        container.prefHeightProperty().bind(container.widthProperty());
        container.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
        });
        gameLoopTimer.setFps(3);
        gameLoopTimer.setTickHandler(this::tick);
        updateView();
    }

    private void initConfig() {
        scale = preferences.getDouble("SCALE", 1);
    }

    private void tick(float secondsSinceLastFrame) {
        if (snake.getPoints().stream()
                .anyMatch(point -> XYPair.intersect(point, currentFoodPoint))) {
            snake.incHead();
            scoreProperty.set(scoreProperty.get() + 1);
            field.clear(currentFoodPoint.getX(), currentFoodPoint.getY());
            currentFoodPoint = field.generateFoodPoint();
        } else {
            snake.move();
        }
        updateView();
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT -> {
                if (snake.getSpeedVector() != SpeedVector.LEFT) {
                    snake.setSpeedVector(SpeedVector.RIGHT);
                }
            }
            case LEFT -> {
                if (snake.getSpeedVector() != SpeedVector.RIGHT) {
                    snake.setSpeedVector(SpeedVector.LEFT);
                }
            }
            case UP -> {
                if (snake.getSpeedVector() != SpeedVector.DOWN) {
                    snake.setSpeedVector(SpeedVector.UP);
                }
            }
            case DOWN -> {
                if (snake.getSpeedVector() != SpeedVector.UP) {
                    snake.setSpeedVector(SpeedVector.DOWN);
                }
            }
            case ESCAPE -> {
                if (!gameLoopTimer.isPaused()) {
                    pause();
                    mainViewControllerProvider.get().openSettings();
                } else {
                    resume();
                }
            }
        }
    }

    public void pause() {
        gameLoopTimer.pause();
    }

    public void resume() {
        gameLoopTimer.resume();
    }

    private void updateView() {
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                var point = field.get(x, y);
                var rect = createRectangle(point);
                if (point.getPointType() == EnvironmentPoint.EMPTY) {
                    rect.setStyle("-fx-fill: black;");
                } else if (point.getPointType() == EnvironmentPoint.OBSTACLE) {
                    rect.setStyle("-fx-fill: gray;");
                } else if (point.getPointType() == FoodPointType.FOOD_POINT) {
                    rect.setStyle("-fx-fill: LimeGreen");
                }
                container.getChildren().add(rect);
            }
        }
        for (var snakePoint : snake.getPoints()) {
            var rect = createRectangle(snakePoint);
            rect.setStyle("-fx-fill: blue;");
            container.getChildren().add(rect);
        }
    }

    private Rectangle createRectangle(Point point) {
        var rect = new Rectangle();
        double screenWidthMost = Screen.getPrimary().getBounds().getWidth() * scale;
        double screenHeightMost = Screen.getPrimary().getBounds().getHeight() * scale;
        double side = Math.min(screenHeightMost, screenWidthMost);
        rect.setWidth(side / field.getWidth());
        rect.setHeight(side / field.getHeight());
        rect.setX((side / field.getWidth()) * point.getX());
        rect.setY((side / field.getHeight()) * point.getY());
        return rect;
    }

    @Override
    protected Stage getStage() {
        return (Stage) container.getScene().getWindow();
    }

    public Pane getRoot() {
        return root;
    }

    public void start() {
        gameLoopTimer.start();
    }
}
