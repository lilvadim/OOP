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
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
        scoreVal.textProperty().bind(score.asString());
        container.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
            }
        });
        gameLoopTimer.setFps(3);
        gameLoopTimer.setTickHandler(this::tick);
    }

    public void restart() {
        foods.clear();
        gameLoopTimer.start();
        init();
        updateView();
    }

    private void init() {
        scale = preferences.getDouble("SCALE", 1);
        scoreGoal = preferences.getInt("SCORE", Integer.MAX_VALUE);
        food = preferences.getInt("FOODS", 5);
        field = fieldProvider.get();
        snake = snakeProvider.get();
        generateFoodPoints();
    }

    private void tick(float secondsSinceLastFrame) {
        if (score.get() == scoreGoal || !snake.canMove()) {
            score.set(0);
            restart();
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
            foods.add(field.generateFoodPoint());
        }
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
                if (point.pointType() == EnvironmentPoint.EMPTY) {
                    rect.setStyle("-fx-fill: black;");
                } else if (point.pointType() == EnvironmentPoint.OBSTACLE) {
                    rect.setStyle("-fx-fill: gray;");
                } else if (point.pointType() == FoodPointType.FOOD_POINT) {
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
        double maxSide = Math.min(screenHeightMost, screenWidthMost);
        double divide = Math.max(field.getHeight(), field.getWidth());
        rect.setWidth(maxSide / divide);
        rect.setHeight(maxSide / divide);
        rect.setX((maxSide / divide) * point.x());
        rect.setY((maxSide / divide) * point.y());
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
        restart();
    }

    public void stop() {
        gameLoopTimer.stop();
    }
}
