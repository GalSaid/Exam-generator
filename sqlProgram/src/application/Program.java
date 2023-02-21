package application;


import java.io.Serializable;
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Management;
import view.EnterView;


public class Program extends Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void start(Stage stage) throws Exception {
		Management model = new Management();
		EnterView v = new EnterView(stage);
		Controller c = new Controller(model, v);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
