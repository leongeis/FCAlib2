package api;

import java.util.List;

/**
 * Interface describing an Entity in FCA. An Entity can
 * either be an Attribute or an Object.
 * @author Leon Geis
 */
public interface Entity {

    public void setID(Entity entity);

    public Entity getID();

    public List<Entity> getDualEntities();

    public void addDualEntity(Entity entity);


}
