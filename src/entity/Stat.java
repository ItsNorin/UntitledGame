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
	
	public Stat setCurrent(double current) {
		this.current = Math.max(Math.min(current, max), 0);
		return this;
	}
	
	public double getCurrent() {
		return current;
	}
	
	public Stat add(double amount) {
		return setCurrent(current + amount);
	}
	
	public Stat subtract(double amount) {
		return setCurrent(current - amount);
	}
	
	public Stat recover() {
		return setCurrent(max);
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
