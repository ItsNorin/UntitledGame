package util;

import javafx.animation.Animation.Status;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ImageViewAnimation extends AnimationTimer {
	private ImageView imageView;
	private Duration animationLength;
	private final int numFrames;
	private final int offsetX;
	private final int offsetY;

	private int currentIndex;
	private long prevStartNS = 0;
	
	private Status status;

	private enum PlayStatus {
		PLAY_ONCE, REPEAT, RESET
	}

	private PlayStatus playStatus;

	/**
	 * @param imageView
	 * @param animationLength duration of animation
	 * @param numFrames       number of frames
	 * @param offsetX         x offset of first frame in image
	 * @param offsetY         y offset of first frame in image
	 */
	public ImageViewAnimation(ImageView imageView, Duration animationLength, int numFrames, int offsetX, int offsetY) {
		this.imageView = imageView;
		this.numFrames = numFrames;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.currentIndex = -1;

		this.status = Status.STOPPED;
		this.playStatus = PlayStatus.REPEAT;
		this.animationLength =  animationLength;

		this.start();
	}
	
	/** creates copy of animation for given ImageView*/
	public ImageViewAnimation clone() {
		ImageView iv = new ImageView();
		iv.setImage(imageView.getImage());
		iv.setX(imageView.getX());
		iv.setY(imageView.getY());
		
		ImageViewAnimation i = new ImageViewAnimation(iv, new Duration(animationLength.toMillis()), numFrames, offsetX, offsetY);
		i.status = this.status;
		i.playStatus = this.playStatus;
		i.prevStartNS = this.prevStartNS;
		i.setFrame(currentIndex);
		return i;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Status getStatus() {
		return status;
	}

	@Override
	public void handle(long ns) {
		int index = 0;

		// if is running, calculate index
		if (status == Status.RUNNING) {
			if (prevStartNS == 0)
				prevStartNS = ns;

			long timeMS = (ns - prevStartNS) / 1000000;

			// if time has exceeded length of animation, restart
			if (timeMS > animationLength.toMillis()) {
				timeMS = 0;
				prevStartNS = ns;
				// stop if only need to play once
				if(playStatus == PlayStatus.PLAY_ONCE)
					status = Status.STOPPED;
			}

			index = (int) ((timeMS * numFrames) / animationLength.toMillis());
		}

		setFrame(index); // change image frame if needed
	}

	/** change frame of animation to given index */
	private void setFrame(int index) {
		if (index != currentIndex) 
			if (imageView != null)
				if (imageView.getImage() != null) {
					final int width = (int) Math.floor(imageView.getImage().getWidth() / numFrames);
					final int height = (int) Math.ceil(imageView.getImage().getHeight());
					imageView.setViewport(new Rectangle2D(index * width + offsetX, offsetY, width, height));
					currentIndex = index;
				}
	}

	/** Change length of animation */
	public void setLength(double millis) {
		this.animationLength = new Duration(Math.max(100, millis));
	}

	/** Play animation once from start */
	public void playOnce() {
		status = Status.RUNNING;
		playStatus = PlayStatus.PLAY_ONCE;
	}

	/** Start playing animation repeatedly */
	public void playRepeat() {
		status = Status.RUNNING;
		playStatus = PlayStatus.REPEAT;
	}

	public void pause() {
		status = Status.PAUSED;
	}

	public void resume() {
		status = Status.RUNNING;
	}
	
	public void pauseAndGoToStart() {
		status = Status.PAUSED;
		setFrame(0);
	}
}
