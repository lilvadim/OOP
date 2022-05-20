package ru.nsu.vadim.fxsnake;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Game loop based on JavaFX AnimationTimer
 */
public class GameLoopTimer extends AnimationTimer {

    private final DoubleProperty animationDuration = new SimpleDoubleProperty(0);
    private long pauseStart;
    private long animationStart;
    private long lastFrameTimeNanos;

    private boolean isPaused;
    private boolean isActive;

    private boolean pauseScheduled;
    private boolean playScheduled;
    private boolean restartScheduled;

    private TickHandler tickHandler = secondsSinceLastFrame -> {
    };
    private int counter = 0;
    private int skip;

    /**
     * @return true if paused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * @return true if active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Set fps to be handled
     *
     * @param fps from 1 to 60
     */
    public void setFps(int fps) {
        if (fps > 60 || fps < 1) {
            throw new IllegalArgumentException();
        }
        this.skip = 60 / fps;
    }

    /**
     * Pause tick
     */
    public void pause() {
        if (!isPaused) {
            pauseScheduled = true;
        }
    }

    /**
     * Resume tick
     */
    public void resume() {
        if (isPaused) {
            playScheduled = true;
        }
    }

    /**
     * Start tick
     */
    @Override
    public void start() {
        super.start();
        isActive = true;
        restartScheduled = true;
    }

    /**
     * Stop tick
     */
    @Override
    public void stop() {
        super.stop();
        pauseStart = 0;
        isPaused = false;
        isActive = false;
        pauseScheduled = false;
        playScheduled = false;
        animationDuration.set(0);
    }

    @Override
    public void handle(long now) {
        if (pauseScheduled) {
            pauseStart = now;
            isPaused = true;
            pauseScheduled = false;
        }

        if (playScheduled) {
            animationStart += (now - pauseStart);
            isPaused = false;
            playScheduled = false;
        }

        if (restartScheduled) {
            isPaused = false;
            animationStart = now;
            restartScheduled = false;
        }

        if (!isPaused) {
            if (counter == skip) {
                counter = 0;
                long animDuration = now - animationStart;
                animationDuration.set(animDuration / 1e9);

                float secondsSinceLastFrame = (float) ((now - lastFrameTimeNanos) / 1e9);
                lastFrameTimeNanos = now;
                tickHandler.tick(secondsSinceLastFrame);
            } else {
                counter++;
            }
        }
    }

    /**
     * Sets tick handles which called on every tick
     */
    public void setTickHandler(TickHandler tickHandler) {
        this.tickHandler = tickHandler;
    }

    /**
     * Tick handler functional interface
     */
    @FunctionalInterface
    public interface TickHandler {
        void tick(float secondsSinceLastFrame);
    }
}
