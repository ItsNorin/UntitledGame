package entity;

public class Stat {
	protected String name;
	protected double max;
	protected double current;
	
	public Stat(String name, double max, double current) {
		this.name = name;
		this.max = max;
		this.current = current;
	}
	
	public Stat(String name, double max) {
		this(name, max, 0);
	}
	
	public Stat setMax(double max) {
		this.max = max;
		return this;
	}
	
	public double getCurrent() {
		return current;
	}
	
	public Stat add(double amount) {
		current = Math.min(current + amount, max);
		current = Math.max(current, 0);
		return this;
	}
	
	public Stat recover() {
		current = max;
		return this;
	}
	
	public double getMax() {
		return max;
	}
	

	public Stat setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
}
