/*
 * Main game object/class
 *
 * Sprites used from https://opengameart.org/content/700-sprites
 * by Philipp Lensenn, outer-court.com
 * CC Attribution 3.0 http://creativecommons.org/licenses/by/3.0/
 */
package main;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Player;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import levels.Level;
import util.FrameRateCounter;
import util.ResourceLoader;
import util.TestLogger;

/**
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Game extends Pane implements Runnable {

	private HashMap<String, Level> levels;
	private Level currentLevel;
	
	private ArrayList<KeyCode> input;
	private Player player;
	
	private final FrameRateCounter frc;
	
	public Game(Stage stage) {
		levels = new HashMap<String, Level>();
		currentLevel = null;
		
		// TODO: load levels from xmls
		levels.put("1", new Level("1"));
		
		// keyboard input handling
		input = new ArrayList<KeyCode>();
		
		player = (Player)ResourceLoader.loadEntityFromXML("defaultPlayer.xml");
		
		frc = new FrameRateCounter();
		frc.getLabel().setViewOrder(-100);
		
		 // init everything once screen shown
		stage.setOnShown(e -> this.run());
	}

	/** Main runnable:
	 *  - register key handler
	 * 	- starts playing the game  */
	@Override
	public void run() {
		// add framerate counter
		this.getChildren().add(frc.getLabel());
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
		
		setCurrentLevel("1"); // load the first level
		initGameLoop();
	}
	
	private void setCurrentLevel(String levelName) {
		if(currentLevel != null)
			currentLevel.end();
		currentLevel = levels.get(levelName);
		currentLevel.init(this, player);
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

	
	private void update(long ns) {
		player.handleKeyInput(input);
		currentLevel.update();
	}
	
}
