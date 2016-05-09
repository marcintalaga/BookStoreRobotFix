package pl.epam.robot.database.dao.tags;

import pl.epam.robot.database.dao.GenericDAO;
import pl.epam.robot.database.entity.tags.Tag;

/**
 * Data Access Object for Tag entity
 * 
 * @author paulina
 *
 */
public interface TagDAO extends GenericDAO<Tag, Integer> {

	public Tag findById(int id);

}
