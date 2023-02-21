package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EnterView {
	private Controller c;
	private TextField userName;
	private PasswordField password;
	private Scene scene;
	private VBox vBox , dataVBox;
	private Button bt;
	private Label label;
	private Alert window;
	private View view;

	public EnterView(Stage primaryStage) {
		primaryStage.setTitle("Log in window");
		userName = new TextField();
		userName.setMaxWidth(200);
		userName.setPromptText("User name");
		userName.setFocusTraversable(false);
		password = new PasswordField();
		password.setMaxWidth(200);
		password.setPromptText("Password");
		password.setFocusTraversable(false);
		vBox = new VBox();
		vBox.setPadding(new Insets(50));
		vBox.setSpacing(55);
		dataVBox = new VBox();
		dataVBox.setPadding(new Insets(10));
		dataVBox.setSpacing(25);
		Image image = new Image("login.jpeg");
		vBox.setBackground(new Background(new BackgroundImage(image,
		    BackgroundRepeat.NO_REPEAT,
		    BackgroundRepeat.NO_REPEAT,
		    BackgroundPosition.CENTER,
		    new BackgroundSize(vBox.getWidth(), vBox.getHeight(),
		    	    false, false, true, true))));
		bt = new Button("Log-in");
		bt.setMinSize(70, 35);
		bt.setStyle("-fx-border-color: #191970 ; -fx-background-color: lightblue ");
		bt.setTextFill(Color.BLACK);
		label = new Label("Welcome to EXAM GENERATION project!");
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
		label.setTextFill(Color.SILVER);
		dataVBox.getChildren().addAll(userName, password, bt);
		dataVBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(label,dataVBox );
		scene = new Scene(vBox, 700, 500);
		enterToDataBase(primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void enterToDataBase(Stage primaryStage) {
		bt.setOnAction(e -> {
			if (password.getText().isEmpty() || userName.getText().isEmpty()) {
				errorMessage("You must enter both user name and password!");
			} else {
				String res = c.createConnection(userName.getText(), password.getText());
				if (res.equals("incorrect values!"))
					errorMessage(res);
				else {
					view = new View(new Stage());
					view.setC(c); 
					primaryStage.close();
				}
			}
		});
	}

		
	

	public void errorMessage(String message) {
		window = new Alert(AlertType.ERROR);
		window.setHeaderText(message);
		window.showAndWait();

	}

	public void setC(Controller c) {
		this.c = c;
	}

}
