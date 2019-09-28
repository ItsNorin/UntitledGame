package util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Animated Image based on an image
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class AnimatedImage extends ImageView {
    private final Rectangle2D[] cellClips;
    private int numCells;
    private final Timeline timeline;
    private int frameCounter;

    public AnimatedImage(Image animationImage, int numCells, Duration frameTime) {
        this.numCells = numCells;

        double cellWidth  = animationImage.getWidth() / numCells;
        double cellHeight = animationImage.getHeight();

        cellClips = new Rectangle2D[numCells];
        for (int i = 0; i < numCells; i++) {
            cellClips[i] = new Rectangle2D(
                    i * cellWidth, 0,
                    cellWidth, cellHeight
            );
        }

        setImage(animationImage);
        setViewport(cellClips[0]);

        timeline = new Timeline(
                new KeyFrame(frameTime, event -> {
                    frameCounter = (frameCounter + 1) % numCells;
                    setViewport(cellClips[frameCounter]);
                })
        );
    }
    
    public AnimatedImage(String imageDir, int numCells, int durationMs) {
    	this(new Image(imageDir), numCells, new Duration(durationMs));
    }

    public AnimatedImage playOnce() {
        frameCounter = 0;
        timeline.stop();
        timeline.setCycleCount(numCells);
        timeline.playFromStart();
        return this;
    }

    public AnimatedImage playRepeat() {
        frameCounter = 0;
        timeline.stop();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.playFromStart();
        return this;
    }

    public AnimatedImage stop() {
        frameCounter = 0;
        setViewport(cellClips[frameCounter]);
        timeline.stop();
        return this;
    }
}

