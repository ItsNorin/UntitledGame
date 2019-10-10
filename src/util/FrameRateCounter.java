package util;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class FrameRateCounter {

	private final Label label;
	private final AnimationTimer frameRateMeter;

	private final long[] frameTimes;
	private int frameTimeIndex = 0;
	private boolean arrayFilled = false;

	/**
	 * @param messageWithFormat message to display. Must include formatting for a
	 *                          double. ex: "FPS %.1f"
	 * @param numFrames         number of past frames to average over.
	 */
	public FrameRateCounter(String messageWithFormat, final int numFrames) {
		label = new Label();
		label.setStyle("-fx-text-fill: yellow; -fx-font-weight: bold; -fx-background-color: black;");

		frameTimes = new long[numFrames];
		frameTimeIndex = 0;
		arrayFilled = false;

		frameRateMeter = new AnimationTimer() {
			@Override
			public void handle(long now) {
				long timeNFramesAgo = frameTimes[frameTimeIndex];
				frameTimes[frameTimeIndex] = now;
				frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;

				if (arrayFilled) {
					long elapsedNanosAverage = (now - timeNFramesAgo) / frameTimes.length;
					double framesPerSecond = 1_000_000_000.0 / elapsedNanosAverage;
					label.setText(String.format(messageWithFormat, framesPerSecond));
				} else if (frameTimeIndex == 0)
					arrayFilled = true;
			}
		};
		frameRateMeter.start();
	}

	/**
	 * Will average over past 60 frames
	 * 
	 * @param messageWithFormat message to display. Must include formatting for a
	 *                          double. ex: "FPS %.1f"
	 */
	public FrameRateCounter(String messageWithFormat) {
		this(messageWithFormat, 60);
	}

	public FrameRateCounter() {
		this("%.1f");
	}

	public Label getLabel() {
		return label;
	}
}
