package entity;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

/**
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public final class Player extends Creature {
	
	private static final double KEY_PRESS_VELOCITY = 0.3;

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
	public Player(
			String facingUp, String facingDown, 
			String facingLeft, String facingRight, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset) 
	{
		super(facingUp, facingDown, facingLeft, facingRight, width, height, spriteXOffset, spriteYOffset);
	}
	
	public void handleKeyInput(ArrayList<KeyCode> keys) {
		// movement
		double vX = 0, vY = 0;
		for(KeyCode kc : keys) {
			switch(kc) {
			case A:   vX -= KEY_PRESS_VELOCITY;   break;
			case D:   vX += KEY_PRESS_VELOCITY;   break;
			case S:   vY += KEY_PRESS_VELOCITY;   break;
			case W:   vY -= KEY_PRESS_VELOCITY;   break;
			default:
			}
		}
		this.setVelocity(velocity.getX() + vX, velocity.getY() + vY);
	}

}
