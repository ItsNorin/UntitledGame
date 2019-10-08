/*
 * Main game object/class
 *
 * Sprites used from https://opengameart.org/content/700-sprites
 * by Philipp Lensenn, outer-court.com
 * CC Attribution 3.0 http://creativecommons.org/licenses/by/3.0/
 */
package main;

import java.util.ArrayList;
import java.util.LinkedList;

import entity.Bullet;
import entity.Creature;
import entity.Entity2D;
import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.ImageViewAnimation;
import util.ResourceLoader;
import util.TestLogger;

/**
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Game extends Pane implements Runnable {

	private ArrayList<KeyCode> input;
	
	private LinkedList<Entity2D> entities;
	
	private Player player;
	
	public Game(Stage stage) {
		// keyboard input handling
		input = new ArrayList<KeyCode>();
		
		player = new Player("playerDown.png", "playerUp.png", 
				"playerLeft.png", "playerRight.png", 
				24,20, -3, -13);
		
		entities = new LinkedList<Entity2D>();
		
		
		
		stage.setOnShown(e -> this.run()); // init everything once screen shown
	}

	
	private void addEntity(Entity2D e) {
		this.entities.add(e);
		this.getChildren().add(e.getView());
		
		// make all hitboxes visible for debug
		if(true) {
			e.getHitbox().setVisible(true);
			e.getHitbox().setFill(Color.RED);
			e.getHitbox().setOpacity(0.15);
			this.getChildren().add(e.getHitbox());
		}
		TestLogger.logEntities(entities);
	}
	
	private void removeEntity(Entity2D e) {
		entities.remove(e);
		this.getChildren().remove(e.getView());
		this.getChildren().remove(e.getHitbox());
		TestLogger.logEntities(entities);
	}
	
	private void loadLevel(int in) {
		switch (in) {
		case 1:
			//this.level = new Level1();
			break;
		case 2:
			//this.level = new Level1(); // placeholder for second level
			break;
		}
		
		entities.clear();
		
		// player
		addEntity(player);
		player.setAcceleration(0.985, 0.985);
		player.setPosition(100, 100);
	}
	
	private long prevns = 0;
	
	private void update(long ns) {
		if(prevns == 0) 
			prevns = ns;
		final long ms = (ns - prevns) / 1000000;
		
		player.handleKeyInput(input);
		
		LinkedList<Entity2D> toRemove = new LinkedList<Entity2D>();
		
		
		if(entities.size() <3) {
			ImageView iv = new ImageView();
			iv.setImage(ResourceLoader.getImage("enemyRight.png"));
			Bullet b = new Bullet(new ImageViewAnimation(iv, new Duration(500), 4, 0, 0), 10, 10, 10, 0, 0);
			b.setPosition((this.getLayoutBounds().getMaxX() - this.getLayoutBounds().getMinX()) / 2 + this.getLayoutBounds().getMinX(), 
					(this.getLayoutBounds().getMaxY() - this.getLayoutBounds().getMinY()) / 2 +  this.getLayoutBounds().getMinY());
			
			for(double i = 0; i < 360; i += 2) 
				addEntity(b.clone().setVelocityWithAngle(i, 0.08));
		}
		
		for(Entity2D e : entities) {
			TestLogger.logEntityPositionData(e);
			e.updatePosition(ms, entities);
			
			if(!isEntityInsideWindow(e, 40) && !(e instanceof Player)) 
				toRemove.add(e);
			
			if(e instanceof Bullet) {
				if(e.overlaps(player)) {
					player.getHealth().add(-((Bullet)e).getDamage());
					toRemove.add(e);
				}
			}
		}
		
		for(Entity2D e : toRemove) 
			removeEntity(e);
		
		prevns = ns;
	}
	
	/**@param e any entity
	 * @param maxDistance farthest entity's center can be from edge of screen
	 * @return true if entity is within maxDistance of screen */
	private boolean isEntityInsideWindow(Entity2D e, double maxDistance) {
		Rectangle r = new Rectangle(this.getLayoutBounds().getWidth() + 2 * maxDistance, 
				                    this.getLayoutBounds().getHeight() + 2 * maxDistance);
		
		r.setX(this.getLayoutBounds().getMinX() - maxDistance);
		r.setY(this.getLayoutBounds().getMinY() - maxDistance);
		
		return r.contains(e.getHitbox().getX() + e.getHitbox().getWidth() / 2, e.getHitbox().getY() + e.getHitbox().getHeight() / 2);
	}
	
	/** Main game loop. */
	private void initGameLoop() {
		// main game loop
		
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long ns) {
				update(ns);
			}
		};
		gameLoop.start();
	}

	/** Main runnable:
	 *  - register key handler
	 * 	- starts playing the game  */
	@Override
	public void run() {
		// keyboard input
		this.getScene().setOnKeyPressed(e -> {
			if (!input.contains(e.getCode())) {
				input.add(e.getCode());
				TestLogger.logKeys(input);
			}
		});
		
		this.getScene().setOnKeyReleased(e -> { 
			input.remove(e.getCode()); 
			TestLogger.logKeys(input);
		});
		
		loadLevel(1); // load the first level
		initGameLoop();
	}
}
