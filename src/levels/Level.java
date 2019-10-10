package levels;

import java.util.LinkedList;

import entity.Bullet;
import entity.Entity2D;
import entity.Player;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import util.ImageViewAnimation;
import util.ResourceLoader;
import util.TestLogger;

/**
 * A game level
 * TODO: allow levels to be loaded from an XML
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 *
 */
public final class Level {
	private static Pane pane;
	private static boolean aLevelExists;
	
	static {
		aLevelExists = false;
		pane = null;
	}
	
	private final String name;
	
	private final LinkedList<Entity2D> entities;
	
	private Player player;
	
	private final long levelStartMS;
	private long prevFrameMS;
	
	
	public Level(String name) {
		this.name = name;
		entities = new LinkedList<Entity2D>();
		levelStartMS = System.currentTimeMillis();
		prevFrameMS = levelStartMS;
	}
	
	
	public LinkedList<Entity2D> getEntities() {
		return entities;
	}
	
	/** @param e adds entity to level */
	public void addEntity(Entity2D e) {
		this.entities.add(e);
		addEntityToPane(e, true);
		TestLogger.logEntities(entities);
	}
	
	/** @param e removes entity from level */
	public void removeEntity(Entity2D e) {
		e.cleanup();
		entities.remove(e);
		removeEntityFromPane(e);
		TestLogger.logEntities(entities);
	}
	
	/** adds entity to pane, making it renderable 
	 * @param e Entity
	 * @param hitboxVisible whether entity's hitbox should be visible */
	private static void addEntityToPane(Entity2D e, boolean hitboxVisible) {
		if(aLevelExists) {
			pane.getChildren().add(e.getView());
			
			if(hitboxVisible) { // make all hitboxes visible for debug
				e.getHitbox().setVisible(true);
				e.getHitbox().setFill(Color.RED);
				e.getHitbox().setOpacity(0.15);
				pane.getChildren().add(e.getHitbox());
			}
		}
	}
	
	/** @param e Removes entity from pane, stays in level */
	private static void removeEntityFromPane(Entity2D e) {
		if(aLevelExists) {
			pane.getChildren().remove(e.getView());
			pane.getChildren().remove(e.getHitbox());
		}
	}
	
	
	
	/**@param e any entity
	 * @param maxDistance farthest entity's center can be from edge of screen
	 * @return true if entity is within maxDistance of screen */
	private static boolean isRectangleInWindow(Rectangle r, double windowXMod, double windowYMod) {
		Rectangle window = new Rectangle(pane.getLayoutBounds().getWidth() + 2 * windowXMod, 
				                    pane.getLayoutBounds().getHeight() + 2 * windowYMod);
		
		window.setX(pane.getLayoutBounds().getMinX() - windowXMod);
		window.setY(pane.getLayoutBounds().getMinY() - windowYMod);
		
		return window.contains(r.getX(), r.getY())
			&& window.contains(r.getX(), r.getY() + r.getHeight())
			&& window.contains(r.getX() + r.getWidth(), r.getY())
			&& window.contains(r.getX() + r.getWidth(), r.getY() + r.getHeight());
	}
	
	
	public String getName() {
		return name;
	}
	
	
	/**
	 * If no other level exists, will initialize this level.
	 * @param renderable Pane in which the level will be contained
	 */
	public boolean init(Pane pane, Player player) {
		if(!aLevelExists) {
			aLevelExists = true;
			Level.pane = pane;
			
			this.player = player;
			player.setBounds(0, pane.getWidth(), 0, pane.getHeight());
			player.setPosition(pane.getWidth() / 2, pane.getHeight() / 4 );
			player.setAcceleration(0.99, 0.99);
			entities.add(player);
			
			for(Entity2D e : entities) 
				addEntityToPane(e, true);
			
			return true;
		}
		return false;
	}
	
	
	/** updates this level, causing everything inside it to update */
	public void update() {
		final long dMS = System.currentTimeMillis() - prevFrameMS;
		if(aLevelExists) {
			// all entities that need to be removed
			LinkedList<Entity2D> toRemove = new LinkedList<Entity2D>();
			
			if(entities.size() <3) {
				ImageView iv = new ImageView();
				iv.setImage(ResourceLoader.getImage("enemyRight.png"));
				Bullet b = new Bullet(new ImageViewAnimation(iv, new Duration(500), 4, 0, 0), 10, 10, 10, 0, 0);
				b.setPosition((pane.getLayoutBounds().getMaxX() - pane.getLayoutBounds().getMinX()) / 2 + pane.getLayoutBounds().getMinX(), 
						(pane.getLayoutBounds().getMaxY() - pane.getLayoutBounds().getMinY()) / 2 +  pane.getLayoutBounds().getMinY());
				
				for(double i = 0; i < 360; i += 0.5) 
					addEntity(b.clone().setVelocityWithAngle(i, 0.4));
			}
			
			// update all entities
			for(Entity2D e : entities) {
				TestLogger.logEntityPositionData(e);
				
				e.updatePosition(dMS, entities);
				
				if(e instanceof Player) {
					
					
				} else {
					// if entity's hitbox is outside of window, remove it
					if(!isRectangleInWindow(e.getHitbox(), 40, 40)) 
						toRemove.add(e);
				}
				
				if(e instanceof Bullet) {
					if(e.overlaps(player)) {
						player.getHealth().add(-((Bullet)e).getDamage());
						toRemove.add(e);
					}
				}
			}
			
			// remove all unneeded entities
			for(Entity2D e : toRemove) 
				removeEntity(e);
		}
		prevFrameMS = System.currentTimeMillis();
	}
	
	
	/**
	 * Ends this level
	 * Will remove everything this level added to pane
	 */
	public void end() {
		if(aLevelExists) {
			for(Entity2D e : entities)
				removeEntityFromPane(e);
		}
		Level.pane = null;
		Level.aLevelExists = false;
	}
	
}
