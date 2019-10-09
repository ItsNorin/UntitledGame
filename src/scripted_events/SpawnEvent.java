package scripted_events;

import entity.Entity2D;

public class SpawnEvent extends ScriptedEvent {
	private final String name, levelName;
	private Entity2D entity;
	
	public SpawnEvent(String name, String levelName, Entity2D entity) {
		this.name = name;
		this.levelName = levelName;
		this.entity = entity;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLevelName() {
		return levelName;
	}

	@Override
	public void run() {
		
	}

}
