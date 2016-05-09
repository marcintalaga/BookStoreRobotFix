package pl.epam.robot.database.entity.tags;

/**
 * Helper to manage Tag entity
 * 
 * @author paulina
 *
 */
public interface TagDAOProxy {

	public void saveNewTag(Tag tag);

	public void deleteTag(Tag tag);

	public Tag getTagById(int id);
}
