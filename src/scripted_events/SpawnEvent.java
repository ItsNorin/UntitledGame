package scripted_events;

import entity.Entity2D;
import javafx.geometry.Point2D;
import levels.Level;

public class SpawnEvent extends ScriptedEvent {
	protected Entity2D template;
	protected Point2D aimAtPoint;
	
	public SpawnEvent(String name, Level level, Entity2D toClone) {
		super(name, level);
		template = toClone;
		aimAtPoint = null;
	}
	
	/** Spawned entity will go towards given point */
	public void aimAt(Point2D aimAt) {
		aimAtPoint = aimAt;
	}
	
	/** Spawned entity will go towards given point */
	public void aimAt(double x, double y) {
		aimAt(new Point2D(x,y));
	}
	
	/** Access entity that will be copied */
	public Entity2D getTemplate() {
		return template;
	}

	/** spawns entity facing given point */
	@Override
	public void run() {
		Entity2D eCopy = template.clone();
		
		if(aimAtPoint != null) 
			eCopy.setVelocityAtPointKeepV(aimAtPoint.getX(), aimAtPoint.getY());
		
		level.addEntity(eCopy);
	}

}
