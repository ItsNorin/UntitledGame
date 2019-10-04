package entity;

import java.util.ArrayList;

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
	
	protected FACING currentFacing;
	
	protected Duration speed;

	public Creature(
			String facingUp, String facingDown, 
			String facingLeft, String facingRight, 
			double width, double height) 
	{
		super();
		moveImages = new ArrayList<Image>();
		currentView = new ImageView();
		moveImages.add(FACING.LEFT.VALUE, ResourceLoader.getImage(facingLeft));
		moveImages.add(FACING.RIGHT.VALUE, ResourceLoader.getImage(facingRight));
		moveImages.add(FACING.UP.VALUE, ResourceLoader.getImage(facingUp));
		moveImages.add(FACING.DOWN.VALUE, ResourceLoader.getImage(facingDown));
		
		speed = new Duration(1000);
		moveAnim = new ImageViewAnimation(
				currentView, speed, 4, 4, 0, 0);
		currentFacing = FACING.DOWN;
		setAnimation(currentFacing.VALUE);
		health = new Stat("Health", 10).recover();
	}
	
	public Stat getHealth() {
		return health;
	}
	
	protected void setAnimation(int i) {
		moveAnim.stop();
		currentView.setImage(moveImages.get(i));
		moveAnim.playFromStart();
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
		// TODO: allow animation rate to depend on speed
		//moveAnim.setRate(Math.hypot(v.getX(), v.getY())/100);
		if(newFacing.VALUE != currentFacing.VALUE) {
			currentFacing = newFacing;
			setAnimation(currentFacing.VALUE);
		}
		super.setVelocity(v);
		return this;
	}
	
	@Override
	public void updatePosition(long ms, ArrayList<Entity2D> solids) {
		super.updatePosition(ms, solids);
		currentView.setX(hitbox.getX());
		currentView.setY(hitbox.getY());
	}
	
	public ImageView getView() {
		return currentView;
	}

}
