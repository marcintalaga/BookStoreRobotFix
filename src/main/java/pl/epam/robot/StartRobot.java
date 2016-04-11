package pl.epam.robot;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.epam.robot.entity.Book;
import pl.epam.robot.view.controller.Controller;
import pl.epam.utils.HibernateUtils;

public class StartRobot extends Application {
	private static final String APPLICATION_TITLE = "Free Ebook From BookStore App";

	@Override
	public void start(Stage primaryStage) {
		Controller customControl = new Controller();

		primaryStage.setScene(new Scene(customControl));
		primaryStage.setTitle(APPLICATION_TITLE);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
