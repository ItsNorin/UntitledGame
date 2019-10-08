package entity;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

/**
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public final class Player extends Creature {
	
	private double KEY_PRESS_VELOCITY = 0.12;
	private double SPRINTING_MULTIPLIER = 2;

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
		boolean sprinting = false;
		
		for(KeyCode kc : keys) {
			switch(kc) {
				case A:   vX -= KEY_PRESS_VELOCITY;   break;
				case D:   vX += KEY_PRESS_VELOCITY;   break;
				case S:   vY += KEY_PRESS_VELOCITY;   break;
				case W:   vY -= KEY_PRESS_VELOCITY;   break;
				case SHIFT: sprinting = true;         break;
				default:
			}
		}
		
		// ensure total velocity doesn't exceed KEY_PRESS_VELOCITY
		if(Math.abs(vX) + Math.abs(vY) > KEY_PRESS_VELOCITY) {
			final double b = KEY_PRESS_VELOCITY * Math.sqrt(0.5);
			vX = (vX < 0) ? -b : b;
			vY = (vY < 0) ? -b : b;
		}
		
		if(sprinting) {
			vX *= SPRINTING_MULTIPLIER;
			vY *= SPRINTING_MULTIPLIER;
		}
		
		this.setVelocity(velocity.getX() + vX, velocity.getY() + vY);
	}

}
