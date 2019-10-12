package entity;

import java.util.LinkedList;

import javafx.util.Duration;
import util.ImageViewAnimation;

public class HomingBullet extends Bullet {
	protected Entity2D tracking;
	
	protected Duration lifespan;
	protected long startMS;

	public HomingBullet(
			ImageViewAnimation animation, double damage, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		super(animation, damage, width, height, spriteXOffset, spriteYOffset);
		tracking = null;
		lifespan = null;
		resetStart();
	}
	
	public HomingBullet(
			String imageName, int animationLength, int numFrames, int imageXOffset, int imageYOffset,
			double damage, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		super(imageName, animationLength, numFrames, imageXOffset, imageYOffset, damage, width, height, spriteXOffset, spriteYOffset);
		tracking = null;
		lifespan = null;
		resetStart();
	}
	
	public HomingBullet(BulletParams bp) {
		super(bp);
		tracking = null;
		lifespan = (bp.lifespanMS == 0) ? null : new Duration(bp.lifespanMS);
		resetStart();
	}

	/** creates an exact copy of this bullet */
	@Override
	public HomingBullet clone() {
		HomingBullet b = new HomingBullet(flyingAnimation, damage, hitbox.getWidth(), hitbox.getHeight(), spriteXOffset, spriteYOffset);
		b.setPosition(this.getPosition()).setAcceleration(this.getAcceleration()).setVelocity(this.getVelocity());
		b.tracking = tracking;
		b.lifespan = (lifespan == null) ? null : new Duration(lifespan.toMillis());
		return b;
	}
	
	public HomingBullet track(Entity2D e) {
		tracking = e;
		return this;
	}
	
	public HomingBullet setLifespan(Duration lifespan) {
		this.lifespan = lifespan;
		return this;
	}
	
	public HomingBullet resetStart() {
		startMS = System.currentTimeMillis();
		return this;
	}
	
	public boolean isActive() {
		if(lifespan == null)
			return true;
		return System.currentTimeMillis() <= startMS + lifespan.toMillis();
	}
	
	@Override
	public void updatePosition(long ms, LinkedList<Entity2D> solids) {
		if(this.isActive()) {
			setVelocityAtPointKeepV(tracking.getCenterX(), tracking.getCenterY());
			super.updatePosition(ms, solids);
		}
	}

}
