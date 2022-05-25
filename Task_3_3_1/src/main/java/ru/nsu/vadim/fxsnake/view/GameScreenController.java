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
import javafx.stage.Stage;
import ru.nsu.vadim.fxsnake.GameLoopTimer;
import ru.nsu.vadim.snake.Field;
import ru.nsu.vadim.snake.Snake;
import ru.nsu.vadim.snake.SpeedVector;
import ru.nsu.vadim.snake.XYPair;
import ru.nsu.vadim.snake.point.EnvironmentPoint;
import ru.nsu.vadim.snake.point.FoodPoint;
import ru.nsu.vadim.snake.point.Point;
import ru.nsu.vadim.snake.point.PointGenerator;

import javax.inject.Inject;
import javax.inject.Provider;
import java.net.URL;
import java.util.*;
import java.util.prefs.Preferences;

public class GameScreenController extends AbstractController implements Initializable {

    private final Provider<Field> fieldProvider;
    private final Provider<Snake> snakeProvider;
    private final GameLoopTimer gameLoopTimer;
    private final IntegerProperty score = new SimpleIntegerProperty(0);

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
    @Inject
    private PointGenerator pointGenerator;

    private final List<Point> obstacles = new ArrayList<>();
    private final Set<Point> foods = new HashSet<>();
    private Field field;
    private double scale;
    private Snake snake;
    private int scoreGoal;
    private int food;


    @Inject
    public GameScreenController(
            Provider<Field> fieldProvider,
            Provider<Snake> snakeProvider,
            GameLoopTimer gameLoopTimer) {
        this.fieldProvider = fieldProvider;
        this.snakeProvider = snakeProvider;
        this.gameLoopTimer = gameLoopTimer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();
        addKeyHandler();
        setupGameLoopTimer();
    }

    private void setupGameLoopTimer() {
        gameLoopTimer.setFps(3);
        gameLoopTimer.setTickHandler(this::tick);
    }

    private void bindProperties() {
        scoreVal.textProperty().bind(score.asString());
    }

    private void addKeyHandler() {
        container.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
        });
    }

    /**
     * Restart game
     */
    public void restart() {
        container.requestFocus();
        initValues();
        updateView();
        gameLoopTimer.start();
    }

    /**
     * Initialize all values
     */
    private void initValues() {
        score.set(0);
        foods.clear();
        obstacles.clear();
        container.getChildren().clear();
        scale = preferences.getDouble("SCALE", 1);
        scoreGoal = preferences.getInt("SCORE", Integer.MAX_VALUE);
        food = preferences.getInt("FOODS", 5);
        field = fieldProvider.get();
        snake = snakeProvider.get();
        generateObstacles();
        generateFoodPoints();
    }

    private void tick(float secondsSinceLastFrame) {
        if (score.get() >= scoreGoal
                || !snake.canMove()
                || obstacles.stream().anyMatch(point -> XYPair.intersect(point, snake.head()))) {
            restart();
            return;
        }
        if (foods.stream().anyMatch(point -> XYPair.intersect(point, snake.head()))) {
            score.set(score.get() + 1);
            field.clear(snake.head().x(), snake.head().y());
            foods.removeIf(point -> XYPair.intersect(point, snake.head()));
            snake.incHead();
            generateFoodPoints();
        } else {
            snake.move();
        }
        updateView();
    }

    private void generateFoodPoints() {
        while (foods.size() < food) {
            Point foodPoint = pointGenerator.random(FoodPoint.FOOD_POINT);
            if (obstacles.stream().noneMatch(point -> XYPair.intersect(point, foodPoint))
                    && snake.getPoints().stream().noneMatch(point -> XYPair.intersect(point, foodPoint))) {
                field.set(foodPoint);
                foods.add(foodPoint);
            }
        }
    }

    private void generateObstacles() {
        var min = Math.min(field.getHeight(), field.getWidth());
        for (int i = 0; i < min; i++) {
            Point obstacle = pointGenerator.random(EnvironmentPoint.OBSTACLE);
            if (obstacle.x() > 0 && obstacle.y() > 0) {
                field.set(obstacle);
                obstacles.add(obstacle);
            } else {
                i--;
            }
        }
    }

    private void handleKeyPress(KeyEvent event) {
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

    /**
     * Pause the game
     */
    public void pause() {
        gameLoopTimer.pause();
    }

    /**
     * Resume the game
     */
    public void resume() {
        gameLoopTimer.resume();
    }

    /**
     * Update the game screen
     */
    private void updateView() {
        container.getChildren().clear();
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                var point = field.get(x, y);
                var rect = createRectangle(point);
                if (point.pointType() == EnvironmentPoint.EMPTY) {
                    rect.setStyle("-fx-fill: black;");
                } else if (point.pointType() == EnvironmentPoint.OBSTACLE) {
                    rect.setStyle("-fx-fill: gray;");
                } else if (point.pointType() == FoodPoint.FOOD_POINT) {
                    double arc = rect.getWidth();
                    rect.setStyle("-fx-fill: LimeGreen;"
                            + "-fx-arc-width: " + arc + ";"
                            + "-fx-arc-height: " + arc + ";");
                }

                var bgRect = createRectangle(point);
                bgRect.setStyle("-fx-fill: black");
                container.getChildren().add(bgRect);

                container.getChildren().add(rect);
            }
        }
        for (var snakePoint : snake.getPoints()) {
            var rect = createRectangle(snakePoint);
            rect.setStyle("-fx-fill: blue;");
            container.getChildren().add(rect);
        }
    }

    /**
     * Creates rectangle with size and coordinates relatively to window size
     *
     * @param point point with coordinates
     * @return new rectangle
     */
    private Rectangle createRectangle(Point point) {
        var rect = new Rectangle();

        var side = Math.min(container.getHeight() / field.getHeight(),
                container.getWidth() / field.getWidth())
                * scale;

        rect.setWidth(side);
        rect.setHeight(side);
        rect.setX(side * point.x());
        rect.setY(side * point.y());
        return rect;
    }

    @Override
    protected Stage getStage() {
        return (Stage) container.getScene().getWindow();
    }

    /**
     * Start game, equivalent to {@code restart()} call
     */
    public void start() {
        restart();
    }

    /**
     * Stops game
     */
    public void stop() {
        gameLoopTimer.stop();
    }
}
