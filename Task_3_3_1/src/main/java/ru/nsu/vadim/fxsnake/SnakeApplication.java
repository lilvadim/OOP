package ru.nsu.vadim.fxsnake;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.logging.Logger;

public class SnakeApplication extends Application {

    private final Injector injector = Guice.createInjector(new AppModule());
    @Inject
    @Named("MainMenu")
    private Provider<FXMLLoader> mainMenuLoaderProvider;

    @Inject
    private Logger logger;

    @Override
    public void init() throws Exception {
        injector.injectMembers(this);
        logger.info("Injected");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Parameters: " + getParameters().getRaw());
        primaryStage.setScene(new Scene(mainMenuLoaderProvider.get().load()));
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        logger.info("Exiting");
    }
}
