/*
 * Main game object/class
 *
 * Sprites used from https://opengameart.org/content/700-sprites
 * by Philipp Lensenn, outer-court.com
 * CC Attribution 3.0 http://creativecommons.org/licenses/by/3.0/
 */
package main;

import java.util.ArrayList;

import entity.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.AnimatedImage;

/**
 * 
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 *
 */
public class Game extends Pane implements Runnable {

	// instance variables
	private ArrayList<Sprite> sprites;
	/*
	private final ImageView background = new ImageView();
	private Level level;
	private SimpleIntegerProperty score;
	private int enemyCount;
	private int chestCount;
	private HBox GUIView;
	private Label scoreLbl; // text label for displaying current score
	private Label livesLbl; // text label for displaying remaining lives
	private Label healthLbl; // text label for displaying remaining health
*/
	/**
	 * Constructor
	 */
	public Game(Stage stage) {
		// create the score counter
		//this.score = new SimpleIntegerProperty(0);

		// create our mobs arraylist
		sprites = new ArrayList<>();
		
		//sprites.add(new Sprite());
		AnimatedImage t = new AnimatedImage("resources/playerDown.png", 4, 100).playRepeat();
		getChildren().add(t);
		
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
		
		
		//this.getChildren().add(background); // add the lvlMap imageview to scene
		//this.initMobs(enemyCount); // initialize our mobs
	}

	/**
	 * Initializes the default mobs for a given level
	
	private void initMobs(int count) {
		player = (player == null ? new Player(this.level) : player); // load the player
		player.setStartPoint(level.playerStart()[0], level.playerStart()[1]);
		mobs.add(player); // add the player to the mob array first, so always index 0
		// add our enemies to the arraylist after the player
		for (int i = 0; i < count; i++) {
			mobs.add(new Enemy(this.level, i));
		}
		// set the starting coordinates for each enemy, skip the player/index 0
		for (int i = 1; i < mobs.size(); i++) {
			Double[] coords = level.getEnemyCoords().get(i - 1);
			mobs.get(i).setStartPoint(coords[0], coords[1]);
		}
		// iterate over the list of mobs and...
		for (int i = 0; i < mobs.size(); i++) {
			MOB m = mobs.get(i);
			hitBoxes.add(m.getHitbox()); // add it's hitbox to our hitbox list
			this.getChildren().add(m.getHitbox()); // add the hitbox to the scene
			this.getChildren().add(m.getView()); // add it's view to our scene
		}
	}
	
	private void initTreasure(int count) {
		for (int i = 0; i < count; i++) {
			Double[] coords = level.treasureCoords().get(i);
			Treasure t = new Treasure(i, coords[0], coords[1]);
			treasure.add(t);
			hitBoxes.add(t.getHitbox());
			this.getChildren().addAll(t.getHitbox(), t.getView());
		}
	}

	private Treasure getTreasure(Rectangle r) {
		for (int i = 0; i < treasure.size(); i++) {
			if (treasure.get(i).getHitbox().equals(r)) {
				return treasure.get(i);
			}
		}
		return null;
	}
 */

	/**
	 * Main game loop method.
	 */
	private void initGameLoop() {
		// main game loop
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long ns) {
				long ms = ns / 1000000;
				// check if we're ready to move the enemies
				/*for (int i = 0; i < entities.size(); i++) {
					Entity2D e = entities.get(i);
					//e.updatePosition(ms);
				}
				*/
				// check if our player isDead/we need to respawn
				
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
