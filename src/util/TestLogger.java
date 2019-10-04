package util;

import entity.Entity2D;

public enum TestLogger {
	LOGGER;
	public static void logEntityPositionData(Entity2D e) {
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
