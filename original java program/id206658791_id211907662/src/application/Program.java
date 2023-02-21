package application;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.CloseQuestion;
import model.Management;
import model.OpenQuestion;
import view.View;

public class Program extends Application implements Serializable {
	private static final long serialVersionUID = 1L;


	@Override
	public void start(Stage stage) throws Exception {
		View v = new View(stage);
		Management model = new Management();
		Controller c = new Controller(model, v);
		CloseQuestion q1 = model.addCloseQuestion("what is the day of rest in Israel?"); // hard-coded
		model.addAnswers(q1, "Sunday", false);
		model.addAnswers(q1, "Monday", false);
		model.addAnswers(q1, "Friday", false);
		model.addAnswers(q1, "Wednesday", false);
		OpenQuestion q2 = model.addOpenQuestion("What is the day of rest in Israel?", "Saturday");
		OpenQuestion q3 = model.addOpenQuestion("How many days are there in a year?", "365 days");
		CloseQuestion q4 = model.addCloseQuestion("What is the 12th month?");
		model.addAnswers(q4, "April", false);
		model.addAnswers(q4, "December", true);
		model.addAnswers(q4, "July", false);
		model.addAnswers(q4, "March", false);
		CloseQuestion q5 = model.addCloseQuestion("Which month is considered summer?");
		model.addAnswers(q5, "January", false);
		model.addAnswers(q5, "June", true);
		model.addAnswers(q5, "August", true);
		model.addAnswers(q5, "December", false);
		model.addAnswers(q5, "November", false);
		c.openBinaryFile();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
