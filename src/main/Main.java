package main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author ItsNorin: <a href="http://github.com/ItsNorin">Github</a>
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
	    double width;
	    double height;
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            width = 976;   
            height = 679;
        } else {
            width = 960; 
            height = 662; 
        }
		// create game/pane
		Parent root = new Game(primaryStage);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX Test Game");
		primaryStage.setWidth(width);
		primaryStage.setHeight(height);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
