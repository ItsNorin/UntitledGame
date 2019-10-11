package entity;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.animation.Animation.Status;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.ImageViewAnimation;
import util.ResourceManager;

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
	
	protected StatWithBar health;
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
	 * @param numFrames number of frames in each facing animation. All images must have same number of frames
	 */
	public Creature(
			String facingUp, String facingDown, 
			String facingLeft, String facingRight, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset,
			int numFrames) 
	{
		super();
		
		this.hitbox.setWidth(width);
		this.hitbox.setHeight(height);
		this.spriteXOffset = spriteXOffset;
		this.spriteYOffset = spriteYOffset;
		
		moveImages = new ArrayList<Image>();
		currentView = new ImageView();
		moveImages.add(FACING.LEFT.VALUE, ResourceManager.getImage(facingLeft));
		moveImages.add(FACING.RIGHT.VALUE, ResourceManager.getImage(facingRight));
		moveImages.add(FACING.UP.VALUE, ResourceManager.getImage(facingUp));
		moveImages.add(FACING.DOWN.VALUE, ResourceManager.getImage(facingDown));
		
		moveAnim = new ImageViewAnimation(currentView, new Duration(1000), numFrames, 0, 0);
		
		currentFacing = FACING.DOWN;
		
		health = new StatWithBar("Health", 100, this.getHitbox().getWidth(), 6).recover();
	}
	
	// to make instantiation of creatures less argument heavy
	public static class CreatureParameters {
		public String up, down, left, right;
		public double width, height, spriteXOffset, spriteYOffset;
		public double maxHealth;
		public int numFrames;
		
		public CreatureParameters(
				String facingUp, String facingDown, 
				String facingLeft, String facingRight, 
				double width, double height, 
				double spriteXOffset, double spriteYOffset,
				int numFrames) 
		{
			up = facingUp;
			down = facingDown;
			left = facingLeft;
			right = facingRight;
			this.width = width;
			this.height = height;
			this.spriteXOffset = spriteXOffset;
			this.spriteYOffset = spriteYOffset;
			this.numFrames = numFrames;
			maxHealth = 100;
		}
	}
	
	public Creature(CreatureParameters cp) {
		this(cp.up, cp.down, cp.left, cp.right, cp.width, cp.height, cp.spriteXOffset, cp.spriteYOffset, cp.numFrames);
		health.setMax(cp.maxHealth).recover();
	}
	
	public StatWithBar getHealthBar() {
		return health;
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
		
		if(dv < 0.00005) 
			moveAnim.pauseAndGoToStart();
		else if(moveAnim.getStatus() != Status.RUNNING)
			moveAnim.playRepeat();
		
		if(newFacing.VALUE != currentFacing.VALUE) {
			currentFacing = newFacing;
			currentView.setImage(moveImages.get(currentFacing.VALUE));
			moveAnim.pauseAndGoToStart();
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
		health.fitUnder(hitbox, 3);
	}
	
	@Override
	public Creature setPosition(double xPos, double yPos) {
		super.setPosition(xPos, yPos);
		currentView.setX(hitbox.getX() + spriteXOffset);
		currentView.setY(hitbox.getY() + spriteYOffset);
		health.fitUnder(hitbox, 3);
		return this;
	}
	
	@Override
	public ImageView getView() {
		return currentView;
	}

	@Override 
	public void cleanup() {
		moveAnim.stop();
	}
}
