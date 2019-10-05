/*
 * Main game object/class
 *
 * Sprites used from https://opengameart.org/content/700-sprites
 * by Philipp Lensenn, outer-court.com
 * CC Attribution 3.0 http://creativecommons.org/licenses/by/3.0/
 */
package main;

import java.util.ArrayList;

import entity.Creature;
import entity.Entity2D;
import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.TestLogger;

/**
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Game extends Pane implements Runnable {

	private ArrayList<KeyCode> input;
	
	private ArrayList<Entity2D> entities;
	
	private Player player;
	
	public Game(Stage stage) {
		// keyboard input handling
		input = new ArrayList<KeyCode>();
		
		player = new Player("playerDown.png", "playerUp.png", 
				"playerLeft.png", "playerRight.png", 
				24,20, -3, -13);
		
		entities = new ArrayList<Entity2D>();
		
		
		this.loadLevel(1); // load the first level
		
		stage.setOnShown(e -> this.run()); // init everything once screen shown
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
		entities.add(player);
		player.setVelocity(0.5, 0);
		player.setAcceleration(0.98, 0.98);
		player.setPosition(100, 100);
		
		// add all entities to screen
		for(Entity2D e : entities) {
			if(e instanceof Creature) {
				this.getChildren().add(((Creature)e).getView());
				
				// make all hitboxes visible for debug
				if(true) {
					e.getHitbox().setVisible(true);
					e.getHitbox().setFill(Color.RED);
					e.getHitbox().setOpacity(0.15);
					this.getChildren().add(e.getHitbox());
				}
			}
		}
	}

	private long prevns = 0;
	private void update(long ns) {
		if(prevns == 0) 
			prevns = ns;
		final long ms = (ns - prevns) / 1000000;
		
		player.handleKeyInput(input);
		
		for(Entity2D e : entities) {
			TestLogger.logEntityPositionData(e);
			e.updatePosition(ms, entities);
		}
		prevns = ns;
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
		
		initGameLoop();
	}
}
