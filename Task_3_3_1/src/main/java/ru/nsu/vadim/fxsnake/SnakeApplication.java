package ru.nsu.vadim.fxsnake;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnakeApplication extends Application {

    private final Injector injector = Guice.createInjector(new AppModule());

    @Inject
    private Logger logger;

    @Override
    public void init() throws Exception {
        injector.injectMembers(this);
        logger.log(Level.INFO, "Injected");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.log(Level.INFO, "Parameters: " + getParameters().getRaw());
    }

    @Override
    public void stop() throws Exception {
        logger.log(Level.INFO, "Exiting");
    }
}
