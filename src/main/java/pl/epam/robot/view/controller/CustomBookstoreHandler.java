package pl.epam.robot.view.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;

import javafx.scene.control.TextInputDialog;

public class CustomBookstoreHandler {
	private TextInputDialog name = new TextInputDialog();
	private TextInputDialog urlPrefix = new TextInputDialog();
	private TextInputDialog urlSufix = new TextInputDialog();
	private TextInputDialog urlStep = new TextInputDialog("a");
	private TextInputDialog start = new TextInputDialog();
	private TextInputDialog finish = new TextInputDialog();
	private TextInputDialog pattern = new TextInputDialog();
	private TextInputDialog attr = new TextInputDialog();

	public Optional<String> nameInput;
	private Optional<String> urlPrefixInput;
	private Optional<String> urlSufixInput;
	private Optional<String> urlStepInput;
	private Optional<String> startInput;
	private Optional<String> finishInput;
	private Optional<String> patternInput;
	private Optional<String> attrInput;

	private Properties properties = new Properties();
	private InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
	private static final String PROPERTIES_FILE_NAME = "URL.properties";
	private final static Logger logger = Logger.getLogger("logger");

	public CustomBookstoreHandler() {
		setDialogsProperties();
	}

	private void setDialogsProperties() {
		setNameDialog();
		setURLPrefixDialog();
		setURLSufixDialog();
		setStepDialog();
		setURLStartDialog();
		setURLFinishDialog();
		setURLPatternDialog();
		setURLAttrDialog();
	}

	private void setNameDialog() {
		name.setTitle("");
		name.setContentText("Bookstore Name *");
		name.setHeaderText("Input Name");
	}

	private void setURLPrefixDialog() {
		urlPrefix.setTitle("");
		urlPrefix.setContentText("Bookstore urlPrefix *");
		urlPrefix.setHeaderText("Input URL Prefx");
	}

	private void setURLSufixDialog() {
		urlSufix.setTitle("");
		urlSufix.setContentText("Bookstore urlSufix");
		urlSufix.setHeaderText("Input URL Sufix");
	}

	private void setStepDialog() {
		urlStep.setTitle("");
		urlStep.setContentText("URL Step");
		urlStep.setHeaderText("Input URL Step (if single URL skip)");
	}

	private void setURLStartDialog() {
		start.setTitle("");
		start.setContentText("Bookstore urlStart");
		start.setHeaderText("Input URL Start");
	}

	private void setURLFinishDialog() {
		finish.setTitle("");
		finish.setContentText("Bookstore urlFinish");
		finish.setHeaderText("Input URL Finish");
	}

	private void setURLPatternDialog() {
		pattern.setTitle("");
		pattern.setContentText("Bookstore urlPattern *");
		pattern.setHeaderText("Input URL Pattern");
	}

	private void setURLAttrDialog() {
		attr.setTitle("");
		attr.setContentText("Bookstore urlAttr");
		attr.setHeaderText("Input URL Attr");
	}

	public String getNameValue() {
		return name.getContentText();
	}

	public void showDialog() {
		nameInput = name.showAndWait();
		urlPrefixInput = urlPrefix.showAndWait();
		urlSufixInput = urlSufix.showAndWait();
		urlStepInput = urlStep.showAndWait();
		startInput = start.showAndWait();
		finishInput = finish.showAndWait();
		patternInput = pattern.showAndWait();
		attrInput = attr.showAndWait();

	}

	public void addBookstoreToPropertiesFile() {

		String numberOfURLsValue = getNumberOfURLsValue();
		File file = new File("/home/szymon/workspace/BookStoreRobot/BookstoreRobot/src/main/resources/URL.properties");
		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(
					"/home/szymon/workspace/BookStoreRobot/BookstoreRobot/src/main/resources/URL.properties", true);
		} catch (IOException e) {
			logger.error("Cant find 'URL.properties' file");
		}
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);

		try {
			replaceSelected(file, "NumberOfURLs=" + numberOfURLsValue);
		} catch (IOException e1) {
			logger.error("Cant change numberOfURLsValue value");
		}
		try {
			bufferWritter.append("\n");
			bufferWritter.append("name" + numberOfURLsValue + "=" + nameInput.get() + "\n");
			bufferWritter.append("urlPrefix" + numberOfURLsValue + "=" + urlPrefixInput.get() + "\n");
			bufferWritter.append("urlSufix" + numberOfURLsValue + "=" + urlSufixInput.get() + "\n");
			bufferWritter.append("urlStep" + numberOfURLsValue + "=" + urlStepInput.get() + "\n");
			bufferWritter.append("start" + numberOfURLsValue + "=" + startInput.get() + "\n");
			bufferWritter.append("finish" + numberOfURLsValue + "=" + finishInput.get() + "\n");
			bufferWritter.append("pattern" + numberOfURLsValue + "=" + patternInput.get() + "\n");
			bufferWritter.append("attr" + numberOfURLsValue + "=" + attrInput.get() + "\n");
			bufferWritter.append("\n");
			bufferWritter.close();
		} catch (IOException e) {
			logger.error("Cant write to 'URL.properties' file");
		}

	}

	private String getNumberOfURLsValue() {
		String NumberOfURLs = "NumberOfURLs";
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Cant find 'URL.properties' file");
			}
		}
		return properties.getProperty(NumberOfURLs);
	}

	private static void replaceSelected(File file, String type) throws IOException {
		List<String> lines = new ArrayList<String>();

		BufferedReader in = new BufferedReader(new FileReader(file));
		String line = in.readLine();
		while (line != null) {
			if (line.startsWith(type)) {
				String lastChar = line.substring(line.length() - 1, line.length());
				int number = Integer.parseInt(lastChar);
				line = line.substring(0, line.length() - 1) + (number + 1);
			}
			lines.add(line);
			line = in.readLine();
		}
		in.close();

		PrintWriter out = new PrintWriter(file);
		for (String l : lines)
			out.println(l);
		out.close();

	}
}
