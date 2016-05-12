package pl.epam.robot.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import pl.epam.robot.StartRobot;
import pl.epam.robot.database.entity.book.Book;
import pl.epam.robot.parsing.BookstoreResources;
import pl.epam.robot.parsing.FreeBookParser;
import pl.epam.robot.propertiesReader.PropertiesReader;

/**
 * GUI handler
 * 
 * @author paulina
 *
 */
public class Controller extends AnchorPane implements Initializable {

	private static final String RESOURCE_FXML = "/SimpleAppJavaFX.fxml";
	private static final String DEFAULT_COMBOBOX_VALUE = "Bookstores";

	@FXML
	private MenuItem quitMenuItem;
	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	@FXML
	private Button getBooksButton;
	@FXML
	private ComboBox<String> bookStoreComboBox;
	@FXML
	private ListView<Book> booksList;

	private Map<String, List<Book>> bookstoresWithBooksMap = new HashMap<>();
	private List<BookstoreResources> bookStoreResources = new ArrayList<>();
	private ObservableList<String> bookStores = FXCollections.observableArrayList();
	private ObservableList<Book> bookObservableList = FXCollections.observableArrayList();

	public Controller() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RESOURCE_FXML));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setLocation(StartRobot.class.getResource("/SimpleAppJavaFX.fxml"));
		setBookStoresWithBooksMap();
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		booksList.setItems(bookObservableList);
		addBookStoresToBookStoresLists(bookStores);
		bookStoreComboBox.setItems(bookStores);
	}

	@FXML
	private void handleQuitMenuItemAction() {
		System.exit(0);
	}

	@FXML
	private void handleAddAction() {

		String name = null;
		CustomBookstoreHandler newBookStoreHandler = new CustomBookstoreHandler();
		newBookStoreHandler.showDialog();
		if (newBookStoreHandler.nameInput.isPresent()) {
			name = newBookStoreHandler.nameInput.get();
		}

		bookStoreComboBox.getItems().add(name);
		newBookStoreHandler.addBookstoreToPropertiesFile();
	}

	@FXML
	private void handleRemoveAction() {
		int selectedIndex = bookStoreComboBox.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			bookStoreComboBox.getItems().remove(selectedIndex);
		}
		bookStores = bookStoreComboBox.getItems();
		if (bookStoreComboBox.getItems().isEmpty()) {
			bookStoreComboBox.setValue(DEFAULT_COMBOBOX_VALUE);
		}
	}

	@FXML
	private void handleAddBooksToListAction() {
		String selectedBookStore = bookStoreComboBox.getSelectionModel().getSelectedItem();
		List<Book> books = bookstoresWithBooksMap.get(selectedBookStore);
		if (books == null) {
			bookObservableList.clear();
		} else {
			bookObservableList.setAll(books);
		}
	}

	private void setBookStoresWithBooksMap() {
		FreeBookParser parser = new FreeBookParser();
		bookStoreResources = parser.freeBooks();
		for (BookstoreResources bSResources : bookStoreResources) {
			List<Book> booksList = new ArrayList<>();
			booksList.addAll(bSResources.getBooks());
			bookstoresWithBooksMap.put(bSResources.getBookstoreName(), booksList);
		}
	}

	private void addBookStoresToBookStoresLists(List<String> bookStoresList) {
		PropertiesReader propertiesReader = new PropertiesReader();
		bookStoresList.addAll(propertiesReader.getBookstoresNames());
	}

}
