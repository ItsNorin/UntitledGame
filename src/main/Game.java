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
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.TestLogger;
/**
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 *
 */
public class Game extends Pane implements Runnable {

	// instance variables
	private ArrayList<Entity2D> entities;
	
	private Creature test;
	
	public Game(Stage stage) {
		// create the score counter
		//this.score = new SimpleIntegerProperty(0);

		test = new Creature("playerDown.png", "playerUp.png", 
				"playerLeft.png", "playerRight.png", 
				10,10);
		entities = new ArrayList<Entity2D>();
		entities.add(test);
		test.setVelocity(0.5, 0.7);
		test.setAcceleration(0.9986, 0.998);
		test.setPosition(100, 100);
		
		
		//this.enemyCount = 0;
		this.loadLevel(1); // load the first level of our game.
		this.initGUI(); // load/initialize the GUI
		// run our main loop once the stage is shown
		stage.setOnShown(e -> this.run());
	}

	/**
	 * initialized the gui/overlay to track player lives, level, total score, etc.
	 * 
	 * @return a new HBOX with our GUI elements
	 */
	private void initGUI() {

	}

	/**
	 * Loads the default/background images for our lvlMap
	 * 
	 * @param in the integer value for the lvlMap image to load all images are
	 *           'lvlMap' + in
	 */
	private void loadLevel(int in) {
		//entities.clear();

		switch (in) {
		case 1:
			//this.level = new Level1();
			break;
		case 2:
			//this.level = new Level1(); // placeholder for second level
			break;
		}
		
		//background.setImage(level.getImage()); // get the background image
		
		for(Entity2D e : entities) {
			if(e instanceof Creature) {
				e.getHitbox().setVisible(true);
				this.getChildren().add(e.getHitbox());
				this.getChildren().add(((Creature)e).getView());
			}
		}
		
	}

	private long prevns = 0;
	private void update(long ns) {
		if(prevns == 0) 
			prevns = ns;
		
		long ms = (ns - prevns) / 1000000;
		for(Entity2D e : entities) {
			TestLogger.logEntityPositionData(e);
			e.updatePosition(ms, entities);
		}
		prevns = ns;
	}
	
	/**
	 * Main game loop.
	 */
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

	/**
	 * Main runnable: 1) register our key handler 2) register our game over listener
	 * 3) starts playing the game
	 */
	@Override
	public void run() {
		// System.out.println("layoutX: " + this.getScene().getX());
		// System.out.println("layoutY: " + this.getScene().getY());
		//this.getScene().setOnKeyPressed(e -> player.moveHandler(e));
		// this.getScene().setOnKeyPressed(player::moveHandler); java8+ method reference
		// style
		// start the game loop now
		this.initGameLoop();
	}
}
