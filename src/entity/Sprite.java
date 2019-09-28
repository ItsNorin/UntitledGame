package entity;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import util.AnimatedImage;

/**
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Sprite extends Entity2D {
	protected AnimatedImage animations[];
	protected int currentAnimation;
	
	public Sprite(String imageIDs[], int frameCount[], Duration speed[], int width, int height) {
		currentAnimation = 0;
		animations = new AnimatedImage[imageIDs.length];
		for(int i = 0; i < imageIDs.length; i++) 
			animations[i] = new AnimatedImage(new Image(imageIDs[i]), frameCount[i], speed[i]);
	}
	
	public Sprite(AnimatedImage animations[], Rectangle boundingBox) {
		currentAnimation = 0;
		this.animations = animations.clone();
		this.hitBox = boundingBox;
	}
	
	public Sprite(AnimatedImage animations[], double width, double height) {
		this(animations, new Rectangle(width, height));
	}
	
	public Sprite(Sprite s) {
		this(s.animations, s.hitBox);
	}
	
	public Sprite setAnimation(int i) {
		currentAnimation = i % animations.length;
		return this;
	}
	
	@Override
	public Sprite setPosition(Point2D pos) {
		position = pos;
		for(AnimatedImage ai : animations) {
			ai.setTranslateX(pos.getX());
			ai.setTranslateY(pos.getY());
		}
		return this;
	} 
	
}
