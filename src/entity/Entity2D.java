package entity;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * A basic 2D entity, stores position, rotation, velocity, and acceleration
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Entity2D {

	protected boolean isVisible;

	/** position of entity in space */
	protected Point2D position;
	/** velocity of entity in UNIT per millisecond */
	protected Point2D velocity;
	/**
	 * multiplier for velocity per millisecond - 0: stop instantly - 0.5: reduce
	 * velocity in half every millisecond - 1: no change
	 */
	protected Point2D acceleration;

	protected Rectangle hitBox;

	/**
	 * rotation in degrees 0 is facing right 90 is facing up 180 - left 270 - down
	 */
	protected double rotation;
	/** change in rotation per millisecond */
	protected double rotationalVelocity;
	/** multiplier for rotational velocity per millisecond */
	protected double rotationalAcceleration;

	public Entity2D(double boundingBoxWidth, double boundingBoxHeight, Point2D position, Point2D velocity,
			Point2D acceleration, double rotationalVelocity, double rotationalAcceleration, boolean isVisible) {
		hitBox = new Rectangle(boundingBoxWidth, boundingBoxHeight);
		hitBox.setFill(Color.AQUAMARINE);
		hitBox.setOpacity(0.1);

		this.position = position; // TODO: Ensure its copying and not re-using same point
		this.isVisible = isVisible;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.rotationalAcceleration = rotationalAcceleration;
		this.rotationalVelocity = rotationalVelocity;
	}

	public Entity2D(Entity2D e) {
		this(e.hitBox.getWidth(), e.hitBox.getHeight(), e.position, e.velocity, e.acceleration, e.rotationalVelocity,
				e.rotationalAcceleration, e.isVisible);
	}

	public Entity2D() {
		this(1, 1, Point2D.ZERO, new Point2D(1., 1.), Point2D.ZERO, 0, 0, false);
	}

	public Entity2D setPosition(Point2D pos) {
		position = pos;
		return this;
	}

	public Entity2D setPosition(double xPos, double yPos) {
		return setPosition(new Point2D(xPos, yPos));
	}

	public Point2D position() {
		return position;
	}

	/**
	 * adds to entity's current position
	 * 
	 * @param dX x offset
	 * @param dY y offset
	 */
	public Entity2D relocate(double dX, double dY) {
		return setPosition(position.add(dX,dY));
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

	/** unit vector in the direction the point is facing */
	public Point2D getFacingUnitVect() {
		return new Point2D(Math.cos(Math.toRadians(rotation)), Math.sin(Math.toRadians(rotation)));
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

	/**
	 * @param dVX x acceleration in UNIT/ms^2
	 * @param dVY y acceleration in UNIT/ms^2
	 */
	public Entity2D setAcceleration(double dVX, double dVY) {
		return setAcceleration(new Point2D(dVX, dVY));
	}

	public Entity2D setVisibility(boolean isVisible) {
		this.isVisible = isVisible;
		return this;
	}

	public boolean isVisible() {
		return isVisible;
	}
	
	/**Finds distance to closest entity's hitbox
	 * 0 for x and/or y if hitboxes overlap
	 * @param e any valid entity
	 * @return distance to entity's hitbox from the edge of this hitbox
	 */
	public Point2D distance(final Entity2D e) {
		double dxLeft = hitBox.getX() - (e.hitBox.getX() + e.getBoundingBox().getWidth());
		double dxRight = e.getBoundingBox().getX() - (hitBox.getX() + hitBox.getWidth());
		double dx = Math.min(Math.abs(dxLeft), Math.abs(dxRight));

		if (dxLeft < 0 && dxRight < 0) 
			dx = 0; // hitboxes intersect
		
		double dyLeft = hitBox.getY() - (e.getBoundingBox().getY() + e.getBoundingBox().getHeight());
		double dyRight = e.getBoundingBox().getY() - (hitBox.getY() + hitBox.getHeight());
		double dy = Math.min(Math.abs(dyLeft), Math.abs(dyRight));

		if (dyLeft < 0 && dyRight < 0) 
			dy = 0; // hitboxes intersect
			
		return new Point2D((dxLeft < dxRight ? -dx : dx), (dyLeft < dyRight ? -dy : dy));
	}

	/**Finds closest entity based on hit boxes
	 * @param entities
	 * @return closest entity, or this if no entities found */
	public Entity2D closestEntity(Entity2D entities[]) {
		Entity2D e = this;
		double currentDist = Double.MAX_VALUE;
		
		for(Entity2D s : entities) {
			Point2D dist = this.distance(s);
			double tempDist = Math.hypot(dist.getX(), dist.getY());
			if(tempDist < currentDist) {
				currentDist = tempDist;
				e = s;
			}
		}
		
		return e;
	}
	
	/**
	 * Finds distance to closest entity's hitbox
	 * 0 if they overlap
	 * @param solids all entities to check hitboxs of
	 * @return distance to closest hitbox
	 */
	public Point2D distanceToClosestEntity(Entity2D entities[]) {
		return distance(closestEntity(entities));
	}

	/**
	 * @param s any sprite
	 * @return true if sprite bounding boxes are overlapping
	 */
	public boolean collides(Entity2D s) {
		Shape r = Shape.intersect(hitBox, s.hitBox);
		return r.getBoundsInLocal().getWidth() != -1 || r.getBoundsInLocal().getHeight() != -1;
	}

	/**
	 * Updates entity's position and rotation based on velocity and acceleration
	 * 
	 * @param ms Milliseconds since last update
	 */
	public void updatePosition(long ms, Entity2D solids[]) {
		// TODO: Ensure this works
		setVelocity(velocity.getX() * Math.pow(acceleration.getX(), ms),
				velocity.getY() * Math.pow(acceleration.getY(), ms));
		setRotationalVelocity(rotationalVelocity + Math.pow(rotationalAcceleration, ms));

		for (Entity2D e : solids) {
			if (collides(e)) {
				// TODO do proper collision detection
			}
		}

		setPosition(position.add(velocity.multiply(ms)));
		setRotation(rotation * Math.pow(rotationalVelocity, ms));

		updateBoundingBox();
	}

	protected void updateBoundingBox() {
		hitBox.setRotate(this.rotation);
		hitBox.setX(position.getX());
		hitBox.setY(position.getY());
	}

	public Rectangle getBoundingBox() {
		return hitBox;
	}
}
