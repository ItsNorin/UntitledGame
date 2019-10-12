package entity;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StatWithBar extends Stat {
	private Rectangle border, fill;
	Group bar;

	public StatWithBar(String name, double max, double width, double thickness, Color fillColor, Color darkFillColor) {
		super(name, max);

		border = new Rectangle(0, 0, width, thickness);
		border.setFill(darkFillColor);
		border.setStroke(Color.BLACK);
		border.setStrokeWidth(1.0);

		fill = new Rectangle(0, 0, width, thickness);
		fill.setFill(fillColor);

		bar = new Group(border, fill);
	}

	public StatWithBar(String name, double max, double width, double thickness) {
		this(name, max, width, thickness, Color.RED, Color.DARKRED);
	}

	public StatWithBar clone() {
		return new StatWithBar(name, max, border.getWidth(), border.getHeight(), (Color)border.getFill(), (Color)fill.getFill())
					.setVisible(this.bar.isVisible())
					.setCurrent(this.current);
	}

	public StatWithBar setVisible(boolean isVisible) {
		bar.setVisible(false);
		return this;
	}

	public Group getBar() {
		return bar;
	}

	/**
	 * Align this bar directly below given rectangle
	 * 
	 * @param r       any rectangle
	 * @param yOffset modify y position
	 * @return this
	 */
	public StatWithBar fitUnder(Rectangle r, double yOffset) {
		if (border.getWidth() != r.getWidth())
			setBarWidth(r.getWidth());
		double newX = r.getX(), newY = r.getY() + r.getHeight() + yOffset;
		border.setX(newX);
		border.setY(newY);
		fill.setX(newX);
		fill.setY(newY);
		return this;
	}

	/**
	 * Align this bar directly below given StatWithBar
	 * 
	 * @param sb      any bar
	 * @param yOffset modify y position
	 * @return this
	 */
	public StatWithBar fitUnder(StatWithBar sb, double yOffset) {
		return fitUnder(sb.border, yOffset);
	}

	private void updateFillLength() {
		fill.setWidth(this.current / this.max * border.getWidth());
	}

	public StatWithBar setBarWidth(double width) {
		border.setWidth(width);
		updateFillLength();
		return this;
	}

	@Override
	public StatWithBar setCurrent(double current) {
		super.setCurrent(current);
		updateFillLength();
		return this;
	}

	@Override
	public StatWithBar recover() {
		super.recover();
		updateFillLength();
		return this;
	}
}
