package ru.nsu.vadim.fxsnake;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import ru.nsu.vadim.fxsnake.view.MainViewController;
import ru.nsu.vadim.snake.Field;
import ru.nsu.vadim.snake.Snake;
import ru.nsu.vadim.snake.SpeedVector;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.prefs.Preferences;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MainViewController.class).in(Singleton.class);
    }

    @Provides
    @Named("MainMenu")
    FXMLLoader mainMenuLoader(Injector injector) {
        FXMLLoader fxmlLoader
                = new FXMLLoader(MainViewController.class.getResource("MainView.fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        return fxmlLoader;
    }

    @Provides
    Field field(Preferences preferences) {
        return new Field(
                preferences.getInt("WIDTH", 25), preferences.getInt("HEIGHT", 25));
    }

    @Provides
    Snake snake(Field field) {
        return Snake.createOnePointSnake(0, 0, SpeedVector.RIGHT, field.getWidth(), field.getHeight());
    }

    @Provides
    ObjectProperty<SpeedVector> speedVectorObjectProperty(Snake snake) {
        return new SimpleObjectProperty<>(snake.getSpeedVector());
    }

    @Provides
    Preferences properties() {
        return Preferences.userNodeForPackage(SnakeApplication.class);
    }
}
