package entity;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.Animation.Status;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.ImageViewAnimation;
import util.ResourceLoader;

/**
 * Living creature
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Creature extends Entity2D {
	public static enum FACING { 
		LEFT(0), RIGHT(1), UP(2), DOWN(3);
		
		public final int VALUE;
		private FACING(int v) { VALUE = v; }
	};
	
	protected Stat health;
	protected ArrayList<Image> moveImages;
	protected ImageViewAnimation moveAnim;
	
	protected ImageView currentView;
	double spriteXOffset, spriteYOffset;
	
	protected FACING currentFacing;

	/**
	 * @param facingUp name of image for facing up animation
	 * @param facingDown name of image for facing down animation
	 * @param facingLeft name of image for facing left animation
	 * @param facingRight name of image for facing right animation
	 * @param width width of hitbox
	 * @param height height of hitbox
	 * @param spriteXOffset x offset of sprite animation relative to hitbox
	 * @param spriteYOffset y offset of sprite animation relative to hitbox
	 */
	public Creature(
			String facingUp, String facingDown, 
			String facingLeft, String facingRight, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		super();
		
		this.hitbox.setWidth(width);
		this.hitbox.setHeight(height);
		this.spriteXOffset = spriteXOffset;
		this.spriteYOffset = spriteYOffset;
		
		moveImages = new ArrayList<Image>();
		currentView = new ImageView();
		moveImages.add(FACING.LEFT.VALUE, ResourceLoader.getImage(facingLeft));
		moveImages.add(FACING.RIGHT.VALUE, ResourceLoader.getImage(facingRight));
		moveImages.add(FACING.UP.VALUE, ResourceLoader.getImage(facingUp));
		moveImages.add(FACING.DOWN.VALUE, ResourceLoader.getImage(facingDown));
		
		moveAnim = new ImageViewAnimation(currentView, new Duration(1000), 4, 0, 0);
		
		currentFacing = FACING.DOWN;
		setAnimation(currentFacing.VALUE);
		moveAnim.playRepeat();
		
		health = new Stat("Health", 10).recover();
	}
	
	public Stat getHealth() {
		return health;
	}
	
	protected void setAnimation(int i) {
		moveAnim.stop();
		currentView.setImage(moveImages.get(i));
	}
	
	@Override
	public Creature setVelocity(Point2D v) {
		FACING newFacing;
		if(Math.abs(v.getX()) > Math.abs(v.getY())) { // horizontal animation
			if(v.getX() < 0) {
				newFacing = FACING.LEFT;
			} else {
				newFacing = FACING.RIGHT;
			}
		} else {
			if(v.getY() < 0) { // vertical animation
				newFacing = FACING.DOWN;
			} else {
				newFacing = FACING.UP;
			}
		}
		
		double dv = Math.hypot(v.getX(), v.getY());
		moveAnim.setLength(250 / dv);
		if(dv < 0.05) {
			moveAnim.stop();
		}
		else if(moveAnim.getStatus() != Status.RUNNING)
				moveAnim.playRepeat();
		
		if(newFacing.VALUE != currentFacing.VALUE) {
			currentFacing = newFacing;
			setAnimation(currentFacing.VALUE);
			moveAnim.playRepeat();
		}
		super.setVelocity(v);
		return this;
	}
	
	@Override
	public void updatePosition(long ms, LinkedList<Entity2D> solids) {
		super.updatePosition(ms, solids);
		currentView.setX(hitbox.getX() + spriteXOffset);
		currentView.setY(hitbox.getY() + spriteYOffset);
	}
	
	@Override
	public Creature setPosition(double xPos, double yPos) {
		super.setPosition(xPos, yPos);
		currentView.setX(xPos + spriteXOffset);
		currentView.setY(yPos + spriteYOffset);
		return this;
	}
	
	@Override
	public ImageView getView() {
		return currentView;
	}

}
