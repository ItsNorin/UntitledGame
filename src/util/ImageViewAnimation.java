package util;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ImageViewAnimation extends Transition {
	private ImageView imageView;
	private final int count;
	private final int columns;
	private final int offsetX;
	private final int offsetY;

	private int lastIndex;

	public ImageViewAnimation(
			ImageView imageView, Duration duration, 
			int count, int columns, 
			int offsetX, int offsetY) 
	{
		this.imageView = imageView;
		this.count = count;
		this.columns = columns;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		
		setCycleDuration(duration);
		setInterpolator(Interpolator.LINEAR);
		setCycleCount(INDEFINITE);
	}

	@Override
	protected void interpolate(double k) {
		final int index = Math.min((int) Math.floor(k * count), count - 1);
		if (index != lastIndex) {
			final int width = (int)Math.floor(imageView.getImage().getWidth() / columns) ;
			final int height = (int)Math.ceil(imageView.getImage().getHeight());
			final int x = (index % columns) * width + offsetX;
			final int y = (index / columns) * height + offsetY;
			imageView.setViewport(new Rectangle2D(x, y, width, height));
			lastIndex = index;
		}
	}
	
	public ImageView getImageView() {
		return imageView;
	}
}
