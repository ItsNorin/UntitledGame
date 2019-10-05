package util;

import java.util.ArrayList;

import entity.Entity2D;
import javafx.scene.input.KeyCode;

public enum TestLogger {
	LOGGER;
	public static final boolean ACTIVE = false;
	public static final boolean LOG_KEYS = false;

	public static void logEntityPositionData(Entity2D e) {
		if (ACTIVE) {
			System.out.println(e.toString());
			System.out.print("X: ");
			System.out.print(e.getX());
			System.out.print("\tY: ");
			System.out.println(e.getY());
			System.out.print("vX: ");
			System.out.print(e.getVelocity().getX());
			System.out.print("\tvY: ");
			System.out.println(e.getVelocity().getY());
			System.out.print("aX: ");
			System.out.print(e.getAcceleration().getX());
			System.out.print("\taY: ");
			System.out.println(e.getAcceleration().getY());
		}
	}
	
	public static void logKeys(ArrayList<KeyCode> input) {
		if(LOG_KEYS) {
			System.out.print("Keys: ");
			for(KeyCode kc : input) 
				System.out.print(kc.toString() + " ");
			System.out.println();
		}
	}
}
