package pl.epam.robot.database.entity.tags;

public interface TagManager {
	
	public void saveNewTag(Tag tag);
	
	public void deleteTag(Tag tag);
}