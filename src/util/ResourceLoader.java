package util;

import javafx.scene.image.Image;

public enum ResourceLoader {
	LOADER;
	
	public static Image getImage(String name) {
		Image i = new Image("resources\\" + name);
		return i;
	}
}
