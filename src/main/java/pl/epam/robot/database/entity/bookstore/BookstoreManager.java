package pl.epam.robot.database.entity.bookstore;

/**
 * Helper to manage Bookstore entity
 * 
 * @author paulina
 *
 */
public interface BookstoreManager {

	public void saveNewBookstore(Bookstore bookstore);

	public void deleteBookstore(Bookstore bookstore);

}
