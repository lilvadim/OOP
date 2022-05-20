package ru.nsu.vadim.fxsnake.view;

import javafx.stage.Stage;

import java.util.Optional;

public abstract class AbstractController {
    /**
     * Returns current window
     */
    protected abstract Optional<Stage> getStage();
}
