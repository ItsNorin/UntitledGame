package entity;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.Node;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public final class Player extends Creature {
	
	private double KEY_PRESS_VELOCITY = 0.12;
	private double SPRINTING_MULTIPLIER = 2;
	private double minX, maxX, minY, maxY;
    private final Pane uiContainer;
    private final Rectangle uiHealthBorder, uiHealthFill;

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
	public Player(
			String facingUp, String facingDown, 
			String facingLeft, String facingRight, 
			double width, double height, 
			double spriteXOffset, double spriteYOffset,
			int numFrames) 
	{
		super(facingUp, facingDown, facingLeft, facingRight, width, height, spriteXOffset, spriteYOffset, numFrames);
		setBounds(0,0,0,0);
        uiHealthBorder = new Rectangle(getHitbox().getX() - getHitbox().getWidth(), getHitbox().getY() + getHitbox().getHeight(), getHitbox().getWidth(), 6);
        uiHealthBorder.setFill(Color.DARKRED);
        uiHealthBorder.setStroke(Color.BLACK);
        uiHealthBorder.setStrokeWidth(1.0);
        uiHealthFill = new Rectangle(getHitbox().getX() - getHitbox().getWidth() + 1, getHitbox().getY() + getHitbox().getHeight() + 1, (int) (getHitbox().getWidth()*health.getCurrent()/health.getMax()), 4);
        uiHealthFill.setFill(Color.RED);
        uiContainer = new Pane(uiHealthBorder, uiHealthFill);
	}
	
	public Player(Creature.CreatureParameters cp) {
		super(cp);
        uiHealthBorder = new Rectangle(getHitbox().getX() - getHitbox().getWidth(), getHitbox().getY() + getHitbox().getHeight(), getHitbox().getWidth(), 6);
        uiHealthBorder.setFill(Color.DARKRED);
        uiHealthBorder.setStroke(Color.BLACK);
        uiHealthBorder.setStrokeWidth(1.0);
        uiHealthFill = new Rectangle(getHitbox().getX() - getHitbox().getWidth() + 1, getHitbox().getY() + getHitbox().getHeight() + 1, (int) (getHitbox().getWidth()*health.getCurrent()/health.getMax()), 4);
        uiHealthFill.setFill(Color.RED);
        uiContainer = new Pane(uiHealthBorder, uiHealthFill);
	}
    
    public Node getUI() {
      return uiContainer;
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
	
	@Override
	public void updatePosition(long ms, LinkedList<Entity2D> solids) {
		// allows player to be forced to stay in a certain area
		super.updatePosition(ms, solids);
		double newX = hitbox.getX(), newY = hitbox.getY();
		double newVX = this.velocity.getX(), newVY = this.velocity.getY();
		
		if(minX != maxX) {
			if(hitbox.getX() + hitbox.getWidth() > maxX) {
				newX = maxX - hitbox.getWidth();
				newVX = 0;
			}
			else if(hitbox.getX() < minX) {
				newX = minX;
				newVX = 0;
			}
		}
		
		if(minY != maxY) {
			if(hitbox.getY() + hitbox.getHeight() > maxY)  {
				newY = maxY - hitbox.getHeight();
				newVY = 0;
			}
			else if(hitbox.getY() < minY) {
				newY = minY;
				newVY = 0;
			}
		}
		
		this.setVelocity(newVX, newVY);
		this.setPosition(newX, newY);
        
        uiHealthBorder.setX(getHitbox().getX());
        uiHealthBorder.setY(getHitbox().getY() + getHitbox().getHeight());
        uiHealthFill.setX(getHitbox().getX() + 1);
        uiHealthFill.setY(getHitbox().getY() + getHitbox().getHeight() + 1);
        uiHealthFill.setWidth(getHitbox().getWidth() * health.getCurrent() / health.getMax() - 2);
	}
	
	/** Sets bounds for player's position */
	public void setBounds(double minX, double maxX, double minY, double maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

}
