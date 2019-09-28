package entity;

import javafx.geometry.Point2D;
import util.AnimatedImage;

/**
 * Living creature
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Creature extends Sprite {
	public static enum FACING { 
		LEFT(0), RIGHT(1), UP(2), DOWN(3);
		
		public final int value;
		private FACING(int v) { value = v; }
	};
	
	protected Stat health;

	public Creature(
			String leftAnim, 
			String rightAnim,
			String upAnim, 
			String downAnim, 
			
			AnimatedImage[] extraAnimations, 
			double width, double height) 
	{
		super(extraAnimations, width, height);
		// TODO
		
		
		health = new Stat("Health", 10).recover();
	}
	
	public Stat getHealth() {
		return health;
	}
	
	@Override
	public Creature setPosition(Point2D pos) {
		int newAnimation = 0;
		double dx = pos.getX() - position.getX();
		double dy = pos.getY() - position.getY();
		
		if(Math.abs(dx) > Math.abs(dy)) { // horizontal animation
			if(dx < 0) {
				newAnimation = FACING.LEFT.value;
			} else {
				newAnimation = FACING.RIGHT.value;
			}
		} else {
			if(dy < 0) { // vertical animation
				newAnimation = FACING.DOWN.value;
			} else {
				newAnimation = FACING.UP.value;
			}
		}
		// TODO: finish this
		super.setPosition(pos);
		return this;
	} 

}
