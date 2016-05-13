package pl.epam.robot.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
//	@FXML
//	private ListView<Book> booksList;
	@FXML
	private TextField filterField;
	@FXML
	private TableView<Book> bookTable;
	@FXML
	private TableColumn<Book, String> titleAndAuthor;
	@FXML
	private TableColumn<Book, String> category;
	@FXML
	private TableColumn<Book, String> bookstore;
	@FXML
	private TableColumn<Book, String> tag;

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
		//booksList.setItems(bookObservableList);
		addBookStoresToBookStoresLists(bookStores);
		bookStoreComboBox.setItems(bookStores);

		// 0. Initialize the columns.
		titleAndAuthor
				.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitleAndAuthor()));
		category.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategory().getCategoryType()));
		bookstore.setCellValueFactory(
				cellData -> new ReadOnlyStringWrapper(cellData.getValue().getBookstore().getName()));
		tag.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTags().getContent()));

		// 1. Wrap the ObservableList in a FilteredList (initially display all
		// data).
		FilteredList<Book> filteredData = new FilteredList<>(bookObservableList, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(book -> {
				// If filter text is empty, display all books.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter
				// text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (book.getTitleAndAuthor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches title and author.
				} else if (book.getTags().getContent().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches content of tags.
				} else if (book.getCategory().getCategoryType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches categories.
				} else if (book.getBookstore().getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches bookstores.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Book> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(bookTable.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		bookTable.setItems(sortedData);
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
