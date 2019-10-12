package entity;

import java.util.LinkedList;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * A basic 2D entity with a hitbox, stores position, rotation, velocity, and acceleration
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public abstract class Entity2D {
	/** velocity of entity in UNIT per millisecond */
	protected Point2D velocity;
	/**
	 * multiplier for velocity per millisecond - 0: stop instantly - 0.5: reduce
	 * velocity in half every millisecond - 1: no change
	 */
	protected Point2D acceleration;

	protected Rectangle hitbox;

	/**
	 * rotation in degrees 0 is facing right 90 is facing up 180 - left 270 - down
	 */
	protected double rotation;
	/** change in rotation per millisecond */
	protected double rotationalVelocity;
	/** multiplier for rotational velocity per millisecond */
	protected double rotationalAcceleration;

	public Entity2D(double hitBoxWidth, double hitBoxHeight, 
			Point2D position, Point2D velocity, Point2D acceleration, 
			double rotationalVelocity, double rotationalAcceleration, 
			boolean isVisible) 
	{
		hitbox = new Rectangle(hitBoxWidth, hitBoxHeight);
		hitbox.setVisible(isVisible);
		hitbox.setX(position.getX());
		hitbox.setY(position.getY());

		this.velocity = velocity;
		this.acceleration = acceleration;
		this.rotationalAcceleration = rotationalAcceleration;
		this.rotationalVelocity = rotationalVelocity;
	}

	public Entity2D(Entity2D e) {
		this(e.hitbox.getWidth(), e.hitbox.getHeight(), e.getPosition(), e.velocity, e.acceleration, e.rotationalVelocity,
				e.rotationalAcceleration, e.hitbox.isVisible());
	}

	public Entity2D() {
		this(1, 1, Point2D.ZERO, Point2D.ZERO, new Point2D(1., 1.), 0, 0, false);
	}

	public Entity2D setPosition(double xPos, double yPos) {
		hitbox.setX(xPos);
		hitbox.setY(yPos);
		return this;
	}

	public Entity2D setPosition(Point2D pos) {
		return setPosition(pos.getX(), pos.getY());
	}
	
	public Point2D getPosition() {
		return new Point2D(getX(), getY());
	}

	public double getX() {
		return hitbox.getX();
	}
	public double getY() {
		return hitbox.getY();
	}
	
	public double getCenterX() {
		return hitbox.getX() + hitbox.getWidth() / 2;
	}
	
	public double getCenterY() {
		return hitbox.getY() + hitbox.getHeight() / 2;
	}
	
	/**
	 * adds to entity's current position
	 * 
	 * @param dX x offset
	 * @param dY y offset
	 */
	public Entity2D offset(double dX, double dY) {
		return setPosition(getX() + dX, getY() + dY);
	}

	/** velocity in UNIT/ms */
	public Point2D getVelocity() {
		return velocity;
	}

	/** @param v velocity in UNIT/ms */
	public Entity2D setVelocity(Point2D v) {
		velocity = v;
		return this;
	}

	/**
	 * @param vX x velocity in UNIT/ms
	 * @param vY y velocity in UNIT/ms
	 */
	public Entity2D setVelocity(double vX, double vY) {
		return setVelocity(new Point2D(vX, vY));
	}

	public Entity2D setVelocityWithAngle(double theta, double v) {
		return setVelocity(Math.cos(theta) * v, Math.sin(theta) * v);
	}
	
	public Entity2D setVelocityAtPoint(double x, double y, double v) {
		return setVelocityWithAngle(Math.atan2(y-this.getCenterY(), x-this.getCenterX()), v);
	}
	
	public Entity2D setVelocityAtPointKeepV(double x, double y) {
		return setVelocityAtPoint(x, y, getTotalVelocity());
	}
	
	/** @return current angle in radians */
	public double getVelocityTheta() {
		return Math.atan2(velocity.getY(), velocity.getX());
	}
	
	public double getTotalVelocity() {
		return Math.hypot(velocity.getX(), velocity.getY());
	}

	public Entity2D setRotation(double r) {
		rotation = r;
		return this;
	}

	/** rotational velocity in degrees per millisecond */
	public double rotationalVelocity() {
		return rotationalVelocity;
	}

	/** @param vr rotational velocity in degrees per millisecond */
	public Entity2D setRotationalVelocity(double vr) {
		rotationalVelocity = vr;
		return this;
	}

	/** rotational acceleration in degrees/ms^2 */
	public double getRotationalAcceleration() {
		return rotationalAcceleration;
	}

	/** @param dvr rotational acceleration in degrees/ms^2 */
	public Entity2D setRotationalAcceleration(double dvr) {
		rotationalAcceleration = dvr;
		return this;
	}

	/** acceleration in UNIT/ms^2 */
	public Point2D getAcceleration() {
		return acceleration;
	}

	/** @param dv x and y acceleration in UNIT/ms^2 */
	public Entity2D setAcceleration(Point2D dv) {
		acceleration = dv;
		return this;
	}

	/**set acceleration, which is a multiplier for velocity per millisecond - 0:
	 * stop instantly - 0.5: reduce velocity in half every millisecond - 1: no
	 * change
	 * 
	 * @param dVX x velocity multiplier per millisecond
	 * @param dVY y velocity multiplier per millisecond
	 */
	public Entity2D setAcceleration(double dVX, double dVY) {
		return setAcceleration(new Point2D(dVX, dVY));
	}

	/**
	 * Finds distance to closest entity's hitbox 0 for x and/or y if hitboxes
	 * overlap
	 * 
	 * @param e any valid entity
	 * @return distance to entity's hitbox from the edge of this hitbox
	 */
	public Point2D distance(final Entity2D e) {
		double dxLeft = getX() - (e.getX() + e.getHitbox().getWidth());
		double dxRight = e.getHitbox().getX() - (getX() + hitbox.getWidth());
		double dx = Math.min(Math.abs(dxLeft), Math.abs(dxRight));

		if (dxLeft < 0 && dxRight < 0)
			dx = 0; // hitboxes intersect

		double dyLeft = hitbox.getY() - (e.getHitbox().getY() + e.getHitbox().getHeight());
		double dyRight = e.getHitbox().getY() - (hitbox.getY() + hitbox.getHeight());
		double dy = Math.min(Math.abs(dyLeft), Math.abs(dyRight));

		if (dyLeft < 0 && dyRight < 0)
			dy = 0; // hitboxes intersect

		return new Point2D((dxLeft < dxRight ? -dx : dx), (dyLeft < dyRight ? -dy : dy));
	}

	/**
	 * Finds closest entity based on hit boxes
	 * 
	 * @param entities
	 * @return closest entity, or this if no entities found
	 */
	public Entity2D closestEntity(final LinkedList<Entity2D> entities) {
		Entity2D e = this;
		double currentDist = Double.MAX_VALUE;

		for (Entity2D s : entities) {
			Point2D dist = this.distance(s);
			double tempDist = Math.hypot(dist.getX(), dist.getY());
			if (tempDist < currentDist) {
				currentDist = tempDist;
				e = s;
			}
		}

		return e;
	}

	/**
	 * Finds distance to closest entity's hitbox 0 if they overlap
	 * 
	 * @param solids all entities to check hitboxs of
	 * @return distance to closest hitbox
	 */
	public Point2D distanceToClosestEntity(final LinkedList<Entity2D> entities) {
		return distance(closestEntity(entities));
	}

	/**
	 * @param s any entity
	 * @return true if sprite bounding boxes are overlapping
	 */
	public boolean overlaps(final Entity2D s) {
		
		if(s == this) // cannot collide with itsself
			return false;
		// quick check with Manhattan distance, improves performance with lots of entities. 
		if(Math.abs(this.hitbox.getX() - s.hitbox.getX()) > hitbox.getWidth() + s.hitbox.getWidth())
			return false;
		if(Math.abs(this.hitbox.getY() - s.hitbox.getY()) > hitbox.getHeight() + s.hitbox.getHeight())
			return false;
		// do complete collision detection
		Shape r = Shape.intersect(hitbox, s.hitbox);
		return r.getBoundsInLocal().getWidth() != -1 || r.getBoundsInLocal().getHeight() != -1;
	}

	/**
	 * Updates entity's position and rotation based on velocity and acceleration
	 * 
	 * @param ms Milliseconds since last update
	 */
	public void updatePosition(final long ms, final LinkedList<Entity2D> entities) {
		setVelocity(velocity.getX() * Math.pow(acceleration.getX(), ms),
				    velocity.getY() * Math.pow(acceleration.getY(), ms));
		setRotationalVelocity(rotationalVelocity + Math.pow(rotationalAcceleration, ms));

		Point2D vMs = velocity.multiply(ms);
		setPosition(getX() + vMs.getX(), getY() + vMs.getY());
		setRotation(rotation * Math.pow(rotationalVelocity, ms));
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public abstract ImageView getView();
	
	/** stop doing anything, clean up entity for garbage collection */
	public abstract void cleanup();
	
	/** create a copy of this entity */
	public abstract Entity2D clone();
}
