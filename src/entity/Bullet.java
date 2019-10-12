package entity;

import java.util.LinkedList;

import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.ImageViewAnimation;
import util.ResourceManager;

/**
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Bullet extends Entity2D {
	protected ImageViewAnimation flyingAnimation;
	protected double spriteXOffset, spriteYOffset;
	
	protected double damage;
	/*
	public Bullet(String imageStr, 
			double damage,
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		this(ResourceLoader.getImage(imageStr), damage, width, height, spriteXOffset, spriteYOffset);
	}*/
	
	/**
	 * @param animation Animation for this bullet, will be cloned
	 * @param damage amount of health this bullet will remove when hitting a target
	 * @param width width of hitbox
	 * @param height height of hitbox
	 * @param spriteXOffset x offset of sprite animation relative to hitbox
	 * @param spriteYOffset y offset of sprite animation relative to hitbox
	 */
	public Bullet(ImageViewAnimation animation, 
			double damage, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		this.flyingAnimation = animation.clone();
		
		this.hitbox.setWidth(width);
		this.hitbox.setHeight(height);
		
		this.spriteXOffset = spriteXOffset;
		this.spriteYOffset = spriteYOffset;
		
		this.damage = damage;
		
		this.flyingAnimation.playRepeat();
	}
	
	/**
	 * @param imageName
	 * @param animationLength
	 * @param numFrames
	 * @param imageXOffset
	 * @param imageYOffset
	 * 
	 * @param damage amount of health this bullet will remove when hitting a target
	 * @param width width of hitbox
	 * @param height height of hitbox
	 * @param spriteXOffset x offset of sprite animation relative to hitbox
	 * @param spriteYOffset y offset of sprite animation relative to hitbox
	 */
	public Bullet(
			String imageName, int animationLength, int numFrames, int imageXOffset, int imageYOffset,
			double damage, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		ImageView iv = new ImageView();
		iv.setImage(ResourceManager.getImage(imageName));
		this.flyingAnimation = new ImageViewAnimation(iv, new Duration(animationLength), numFrames, imageXOffset, imageYOffset);
		
		this.hitbox.setWidth(width);
		this.hitbox.setHeight(height);
		
		this.spriteXOffset = spriteXOffset;
		this.spriteYOffset = spriteYOffset;
		
		this.damage = damage;
		
		this.flyingAnimation.playRepeat();
	}
	
	public static class BulletParams {
		public String imageName;
		public int animationLength, numFrames, imageXOffset, imageYOffset;
		public double damage, width, height, spriteXOffset, spriteYOffset;
		public int lifespanMS;
		
		public BulletParams() {
			imageName = "enemyRight.png";
			animationLength = 1000;
			numFrames = 4;
			damage = 5;
			width = 30;
			height = 30;
			lifespanMS = 0;
		}
	}
	
	public Bullet(BulletParams bp) {
		this(bp.imageName, bp.animationLength, bp.numFrames, bp.imageXOffset, bp.imageYOffset,
			bp.damage, bp.width, bp.height, bp.spriteXOffset, bp.spriteYOffset);
	}
	
	/** creates an exact copy of this bullet */
	public Bullet clone() {
		Bullet b = new Bullet(flyingAnimation, damage, hitbox.getWidth(), hitbox.getHeight(), spriteXOffset, spriteYOffset);
		b.setPosition(this.getPosition()).setAcceleration(this.getAcceleration()).setVelocity(this.getVelocity());
		return b;
	}
	
	@Override
	public void updatePosition(long ms, LinkedList<Entity2D> solids) {
		super.updatePosition(ms, solids);
		getView().setX(hitbox.getX() + spriteXOffset);
		getView().setY(hitbox.getY() + spriteYOffset);
		
		this.rotation = Math.toDegrees(Math.atan2(this.velocity.getY(), this.velocity.getX()));
		getView().setRotate(this.rotation);
	}
	
	public double getDamage() {
		return damage;
	}

	@Override
	public ImageView getView() {
		return flyingAnimation.getImageView();
	}

	@Override
	public void cleanup() {
		flyingAnimation.stop();
	}
}

