package pl.epam.robot;
import static org.mockito.Mockito.mock;

import org.testng.annotations.Test;

import pl.epam.robot.view.controller.Controller;

public class RobotTests {
	
	Controller controller;
	
	@Test
	public void create() {
		controller = mock(Controller.class);		
	}
}
