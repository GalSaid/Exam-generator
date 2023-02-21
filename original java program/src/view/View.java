package view;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CloseQuestion;
import model.Test;

public class View implements Serializable {
	private static final long serialVersionUID = 1L;
	private BorderPane bpRoot;
	private HBox hb1, hb2, hb3, hb4, hb5, hbTop, hbBottom;
	private VBox vBox, vBox1, vBox2, vBox3, vBox4, vBox5, vBox6;
	private Button btShowQuestionsAndAnswersStock, btAddQuestionAndAnswer, btCreateManualTest, btCreateAutomaticTest,
			btCopyAnExistingTest, btAdd, btClose, btChange, btAddAnswer, btAddCloseQuestion, btCreateTest,
			btAddOpenQuestion, btUpdate, btCreateCopyTest, btAddAnswers, btOk, btOk1, btOk2, btOk3;
	private RadioButton deleteAnswer, updateQuestionText, updateAnswerText;
	private RadioButton rbTrue, rbFalse;
	private RadioButton rbDictionary, rbShortAnswers;
	private ToggleGroup tglChoice, tglIndication, tglSortType;
	private Label lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, lb9;
	private TextField questionTextF, updateQuestionTextF;
	private TextArea stockQ, test, testWithCorrectAnswers, allTestsToShow;
	private TextField textAnswerF, textAnswerCQF;
	private ComboBox<Integer> comboAllSerialNumbers, comboAmountOfAnswers, comboForDeleteAndUpdate,
			comboAllQuestionToTest, comboNumbersOfTests, comboNumOfQuestionsForManual, comboNumOfQuestion;
	private ArrayList<CheckBox> answersCheckBox;
	private ArrayList<Integer> locations;
	private Controller c;
	private Font fontSize;
	private Alert window;
	private CloseQuestion cq;
	private int numOfAnswers, num, numForLimit, serialChoice, serialNumber, numOfQuestions, option;
	private String res;
	private boolean fChoice;

	public View(Stage primaryStage) {
		primaryStage.setTitle("Test System");
		fontSize = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12);
		bpRoot = new BorderPane();
		bpRoot.setPadding(new Insets(10));
		bpRoot.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
		vBox = new VBox();
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox1 = new VBox();
		vBox1.setPadding(new Insets(10));
		vBox1.setSpacing(10);
		vBox1.setAlignment(Pos.TOP_LEFT);
		vBox2 = new VBox();
		vBox2.setPadding(new Insets(10));
		vBox2.setSpacing(10);
		vBox2.setAlignment(Pos.TOP_LEFT);
		vBox3 = new VBox();
		vBox3.setPadding(new Insets(10));
		vBox3.setSpacing(10);
		vBox3.setAlignment(Pos.TOP_LEFT);
		vBox4 = new VBox();
		vBox4.setPadding(new Insets(10));
		vBox4.setSpacing(10);
		vBox4.setAlignment(Pos.TOP_LEFT);
		vBox5 = new VBox();
		vBox5.setPadding(new Insets(10));
		vBox5.setSpacing(10);
		vBox5.setAlignment(Pos.TOP_LEFT);
		vBox6 = new VBox();
		vBox6.setPadding(new Insets(10));
		vBox6.setSpacing(10);
		vBox6.setAlignment(Pos.TOP_CENTER);
		hb1 = new HBox();
		hb2 = new HBox();
		hb3 = new HBox();
		hb4 = new HBox();
		hb5 = new HBox();
		hbTop = new HBox();
		hbBottom = new HBox();
		hb1.setPadding(new Insets(10));
		hb1.setSpacing(10);
		hb2.setPadding(new Insets(10));
		hb2.setSpacing(10);
		hb3.setPadding(new Insets(10));
		hb3.setSpacing(10);
		hb4.setPadding(new Insets(10));
		hb4.setSpacing(10);
		hb5.setPadding(new Insets(10));
		hb5.setSpacing(10);
		hbTop.setPadding(new Insets(10));
		hbTop.setSpacing(90);
		hbTop.setAlignment(Pos.CENTER);
		hbBottom.setPadding(new Insets(10));
		hbBottom.setSpacing(100);
		hbBottom.setAlignment(Pos.CENTER);
		tglChoice = new ToggleGroup();
		tglIndication = new ToggleGroup();
		tglSortType = new ToggleGroup();
		answersCheckBox = new ArrayList<CheckBox>();
		locations = new ArrayList<Integer>();
		comboForDeleteAndUpdate = new ComboBox<Integer>();
		comboForDeleteAndUpdate.setMinSize(230, 50);
		comboForDeleteAndUpdate.setPromptText("choose number of answer");
		comboAllQuestionToTest = new ComboBox<Integer>();
		comboAllQuestionToTest.setMinSize(230, 50);
		comboAllQuestionToTest.setPromptText("Number of questions");
		comboAmountOfAnswers = new ComboBox<Integer>();
		comboAmountOfAnswers.setMinSize(80, 25);
		comboAmountOfAnswers.setPromptText("Amount");
		comboAmountOfAnswers.getItems().addAll(4, 5, 6, 7, 8);
		comboAllSerialNumbers = new ComboBox<Integer>();
		comboAllSerialNumbers.setPromptText("Choose serial number of question");
		comboAllSerialNumbers.setMinSize(230, 50);
		comboNumbersOfTests = new ComboBox<Integer>();
		comboNumbersOfTests.setMinSize(230, 50);
		comboNumbersOfTests.setPromptText("Choose number of test");
		comboNumOfQuestionsForManual = new ComboBox<Integer>();
		comboNumOfQuestionsForManual.setMinSize(230, 50);
		comboNumOfQuestionsForManual.setPromptText("Number of questions");
		comboNumOfQuestion = new ComboBox<Integer>();
		comboNumOfQuestion.setMinSize(230, 50);
		comboNumOfQuestion.setPromptText("Serial number");
		comboAllSerialNumbers.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				initCombo(comboAllSerialNumbers, c.getIdGenerator() - 1);
			}
		});
		lb1 = new Label();
		lb1.setFont(fontSize);
		lb2 = new Label();
		lb2.setFont(fontSize);
		lb3 = new Label();
		lb3.setFont(fontSize);
		lb4 = new Label();
		lb4.setFont(fontSize);
		lb5 = new Label();
		lb5.setFont(fontSize);
		lb6 = new Label(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~:PROJECT BY:~~~~~~~~~~TAL && GAL:~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		lb6.setTextFill(Color.TOMATO);
		lb7 = new Label();
		lb7.setFont(fontSize);
		lb8 = new Label();
		lb8.setFont(fontSize);
		lb9 = new Label();
		lb9.setFont(fontSize);
		stockQ = new TextArea();
		stockQ.setPrefHeight(500);
		stockQ.setPrefWidth(300);
		stockQ.setEditable(false);
		testWithCorrectAnswers = new TextArea();
		testWithCorrectAnswers.setPrefHeight(500);
		testWithCorrectAnswers.setPrefWidth(300);
		testWithCorrectAnswers.setEditable(false);
		allTestsToShow = new TextArea();
		allTestsToShow.setPrefHeight(300);
		allTestsToShow.setPrefWidth(300);
		allTestsToShow.setEditable(false);
		test = new TextArea();
		test.setPrefHeight(500);
		test.setPrefWidth(300);
		test.setEditable(false);
		questionTextF = new TextField();
		textAnswerF = new TextField();
		textAnswerCQF = new TextField();
		updateQuestionTextF = new TextField();
		btShowQuestionsAndAnswersStock = new Button("Show All Questions And Answers Stock");
		designButton(btShowQuestionsAndAnswersStock);
		btAddQuestionAndAnswer = new Button("Adding a question and answer");
		designButton(btAddQuestionAndAnswer);
		updateQuestionText = new RadioButton("Update the text of an existing question");
		updateAnswerText = new RadioButton("Update the text of an existing answer");
		deleteAnswer = new RadioButton("Delete answer");
		rbTrue = new RadioButton("true");
		rbTrue.setToggleGroup(tglIndication);
		rbFalse = new RadioButton("false");
		rbFalse.setToggleGroup(tglIndication);
		rbDictionary = new RadioButton("Sort by dictionary order");
		rbDictionary.setToggleGroup(tglSortType);
		rbShortAnswers = new RadioButton("Sort by answers length");
		rbShortAnswers.setToggleGroup(tglSortType);
		updateQuestionText.setToggleGroup(tglChoice);
		updateAnswerText.setToggleGroup(tglChoice);
		deleteAnswer.setToggleGroup(tglChoice);
		btCreateManualTest = new Button("Creating a test manually");
		designButton(btCreateManualTest);
		btCreateAutomaticTest = new Button("Creating a test automatically");
		designButton(btCreateAutomaticTest);
		btCopyAnExistingTest = new Button("Create a copy of an existing test");
		designButton(btCopyAnExistingTest);
		btAdd = new Button("Add");
		btAddAnswer = new Button("Add");
		btAddAnswer.setMinSize(75, 20);
		btClose = new Button("Close");
		btChange = new Button("Change");
		btAddCloseQuestion = new Button("Adding close question");
		btAddOpenQuestion = new Button("Adding open question");
		btUpdate = new Button("Update");
		btCreateTest = new Button("Create");
		btCreateCopyTest = new Button("Create copy");
		btAddAnswers = new Button("Add answers");
		btOk = new Button("create");
		btOk1 = new Button("Choose");
		btOk2 = new Button("choose");
		btOk3 = new Button("choose");
		btClose.setMinSize(150, 50);
		btClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stockQ.setVisible(false);
				btClose.setVisible(false);

			}
		});
		btClose.setStyle("-fx-background-color: #808080");
		btAdd.setStyle("-fx-background-color: #FFC0CB; -fx-border-color: black; ");
		vBox.getChildren().addAll(stockQ, btClose);
		hbBottom.getChildren().addAll(btCreateManualTest, btCreateAutomaticTest);
		vBox6.getChildren().addAll(hbBottom, lb6);
		bpRoot.setLeft(vBox);
		stockQ.setVisible(false);
		btClose.setVisible(false);
		hbTop.getChildren().addAll(btShowQuestionsAndAnswersStock, btAddQuestionAndAnswer, btCopyAnExistingTest,
				comboAllSerialNumbers);
		bpRoot.setTop(hbTop);
		bpRoot.setBottom(vBox6);
		hb1.setVisible(false);
		hb2.setVisible(false);
		hb3.setVisible(false);
		hb4.setVisible(false);
		vBox1.getChildren().addAll(hb1, hb2, hb3, hb4);
		bpRoot.setCenter(vBox1);
		vBox2.getChildren().addAll(deleteAnswer, updateQuestionText, updateAnswerText, btChange);
		bpRoot.setRight(vBox2);
		Scene scence = new Scene(bpRoot, 1300, 700);

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				window = new Alert(AlertType.WARNING);
				window.setTitle("Exit");
				window.setResizable(false);
				window.setContentText("if you press ok you will log out from the system and the changes will be saved");
				window.showAndWait();
				if (window.getResult() != ButtonType.OK) {
					event.consume();
				} else {
					c.exitProgram();
				}

			}
		});

		showQuestionsAndAnswersStockAction();
		addQuestionAndAnswer();
		createAutomaticTest();
		copyAnExistingTest();
		createManualTest();

		btChange.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if ((comboAllSerialNumbers.getValue() != null) && tglChoice.getSelectedToggle() != null) {
					serialChoice = comboAllSerialNumbers.getValue();
					comboAllSerialNumbers.setDisable(true);
					updateAnswerText.setDisable(true);
					deleteAnswer.setDisable(true);
					updateQuestionText.setDisable(true);
					if (deleteAnswer.isSelected())
						deleteAnswer();
					if (updateAnswerText.isSelected())
						updateAnswer();
					if (updateQuestionText.isSelected())
						updateQuestion();

				} else if (comboAllSerialNumbers.getValue() == null || tglChoice.getSelectedToggle() == null) {
					errorMessage("You need to choose serial number and one of the option");
				}

			}
		});

		primaryStage.setScene(scence);
		primaryStage.show();

	}

	public void showQuestionsAndAnswersStockAction() {
		btShowQuestionsAndAnswersStock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				stockQ.setText(c.showQuestionsAndAnswersStock());
				stockQ.setVisible(true);
				btClose.setVisible(true);

			}
		});

	}

	public void addQuestionAndAnswer() {
		btAddQuestionAndAnswer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				enablePressingOterButtons(true);
				reset();
				btAdd.setDisable(false);
				hb3.setVisible(false);
				hb2.setVisible(false);
				textAnswerF.clear();
				questionTextF.clear();
				hb1.getChildren().setAll(btAddOpenQuestion, btAddCloseQuestion);
				vBox1.setVisible(true);
				hb1.setVisible(true);
				questionTextF.setDisable(false);
				lb1.setText("The text for the question:");
				btAddOpenQuestion.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						hb2.getChildren().setAll(lb2, textAnswerF, btAdd);
						lb2.setText("Enter answer text");
						hb2.setVisible(true);
						btAdd.setVisible(true);
						textAnswerF.setVisible(true);
						hb1.getChildren().setAll(lb1, questionTextF);
						hb1.setVisible(true);
						questionTextF.setVisible(true);
						btAdd.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								if (!questionTextF.getText().isEmpty() && !textAnswerF.getText().isEmpty()) {
									if (c.addQuestionAndAnswer(questionTextF.getText(), "open question",
											textAnswerF.getText()) != null) {
										window = new Alert(AlertType.INFORMATION);
										window.setHeaderText("The question added successfully !");
										enablePressingOterButtons(false);
										window.showAndWait();
										stockQ.setText(c.showQuestionsAndAnswersStock());
										hb2.setVisible(false);
										hb1.setVisible(false);
										initCombo(comboNumOfQuestionsForManual, c.getNumOfQuestions());

									} else
										errorMessage("The question is already exist !");
									questionTextF.clear();
									textAnswerF.clear();
								} else {
									errorMessage("You must enter all the details !");
								}
							}
						});

					}
				});
				btAddCloseQuestion.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						num = 1;
						lb3.setText("Choose number of answers:");
						comboAmountOfAnswers.setValue(null);
						setTitle(comboAmountOfAnswers);
						hb2.getChildren().setAll(lb3, comboAmountOfAnswers, btOk1);
						vBox3.getChildren().setAll(rbTrue, rbFalse);
						hb3.getChildren().setAll(lb4, textAnswerCQF, vBox3, btAddAnswer);
						hb1.getChildren().setAll(lb1, questionTextF, btAdd);
						hb1.setVisible(true);
						questionTextF.setVisible(true);
						btAdd.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent arg0) {
								cq = (CloseQuestion) c.addQuestionAndAnswer(questionTextF.getText(), "close question",
										"empty");
								if (!questionTextF.getText().isEmpty()) {
									if (cq != null) {
										btAdd.setDisable(true);
										questionTextF.setDisable(true);
										hb2.setVisible(true);
										btOk1.setOnAction(new EventHandler<ActionEvent>() {

											@Override
											public void handle(ActionEvent arg0) {
												btOk1.setDisable(true);
												if (comboAmountOfAnswers.getValue() == null) {
													errorMessage("You must choose amount of answers !");
												} else {
													hb3.setVisible(true);
													vBox3.setVisible(true);
													comboAmountOfAnswers.setDisable(true);
													numOfAnswers = comboAmountOfAnswers.getValue();
													lb4.setText("Answer number 1:");

													btAddAnswer.setOnAction(new EventHandler<ActionEvent>() {
														@Override
														public void handle(ActionEvent arg0) {
															if (num <= numOfAnswers) {
																boolean indication;
																if (rbTrue.isSelected()) {
																	indication = true;
																} else {
																	indication = false;
																}
																if (!textAnswerCQF.getText().isEmpty()
																		&& (rbTrue.isSelected()
																				|| rbFalse.isSelected())) {
																	String res = c
																			.addAnswerToCloseQuestion(cq,
																					textAnswerCQF.getText(), indication)
																			.toString();
																	if ((res.equalsIgnoreCase("Added_Successfully"))) {
																		infromationMessage(res);
																		num++;
																		tglIndication.getSelectedToggle()
																				.setSelected(false);
																		if (num > numOfAnswers) {
																			hb3.setVisible(false);
																			hb2.setVisible(false);
																			hb1.setVisible(false);
																			stockQ.setText(
																					c.showQuestionsAndAnswersStock());
																			enablePressingOterButtons(false);
																		}
																	} else {
																		errorMessage(res);
																		tglIndication.getSelectedToggle()
																				.setSelected(false);

																	}
																	textAnswerCQF.clear();

																	lb4.setText("Answer number " + (num) + " :");

																} else {
																	errorMessage("You must enter all details!");
																	lb4.setText("Answer number " + (num) + " :");
																	if (tglIndication.getSelectedToggle() != null) {
																		tglIndication.getSelectedToggle()
																				.setSelected(false);
																	}
																}

															}

														}

													});
												}
											}
										});

									} else {
										errorMessage("The question is already exist !");
										questionTextF.clear();
									}
								} else {
									errorMessage("You must enter a question !");
								}

							}
						});

					}
				});

			}
		});
	}

	public void deleteAnswer() {
		enablePressingOterButtons(true);
		res = c.checkIfCanDeleteAnswer(serialChoice);
		hb2.setVisible(false);
		hb3.setVisible(false);
		hb4.setVisible(false);
		if (res.equalsIgnoreCase("you can delete")) {
			initCombo(comboForDeleteAndUpdate, c.getNumOfAnswers(serialChoice));
			vBox4.getChildren().setAll(comboForDeleteAndUpdate);
			hb1.getChildren().setAll(vBox4);
			hb1.setVisible(true);
			vBox4.setVisible(true);
			comboForDeleteAndUpdate.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					res = c.deleteAnswer(serialChoice, comboForDeleteAndUpdate.getValue());
					infromationMessage(res);
					enablePressingOterButtons(false);
					reset();
				}
			});

		} else {
			errorMessage(res);
			enablePressingOterButtons(false);
			reset();
		}

	}

	public void updateAnswer() {
		enablePressingOterButtons(true);
		boolean flag = c.checkIfCQ(serialChoice);
		hb4.setVisible(false);
		hb3.setVisible(false);
		hb2.setVisible(false);
		lb4.setText("The update answer:");
		if (flag) {
			initCombo(comboForDeleteAndUpdate, c.getNumOfAnswers(serialChoice));
			vBox4.getChildren().setAll(comboForDeleteAndUpdate, lb4, textAnswerF, btUpdate);
		} else {
			vBox4.getChildren().setAll(lb4, textAnswerF, btUpdate);

		}
		vBox4.setVisible(true);
		hb1.getChildren().setAll(vBox4);
		hb1.setVisible(true);
		btUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (!textAnswerF.getText().isEmpty()) {
					if (flag) {
						if (comboForDeleteAndUpdate.getValue() == null) {
							errorMessage("You must choose answer number");
						} else {
							option = comboForDeleteAndUpdate.getValue();
							comboForDeleteAndUpdate.setDisable(false);
							res = c.updateAnswerText(serialChoice, option, textAnswerF.getText());
							infromationMessage(res);
							enablePressingOterButtons(false);
							hb1.setVisible(false);
							reset();
						}
					} else {
						option = -1;
						res = c.updateAnswerText(serialChoice, option, textAnswerF.getText());
						infromationMessage(res);
						enablePressingOterButtons(false);
						hb1.setVisible(false);
						reset();
					}

				} else
					errorMessage("You must enter the update answer");
				comboAllSerialNumbers.setDisable(false);
				updateAnswerText.setDisable(false);
				deleteAnswer.setDisable(false);
				updateQuestionText.setDisable(false);

			}
		});

	}

	public void updateQuestion() {
		enablePressingOterButtons(true);
		lb4.setText("The update question:");
		vBox4.setVisible(true);
		vBox4.getChildren().setAll(lb4, updateQuestionTextF, btUpdate);
		hb1.getChildren().setAll(vBox4);
		hb1.setVisible(true);
		hb4.setVisible(false);
		hb3.setVisible(false);
		hb2.setVisible(false);
		btUpdate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (updateQuestionTextF.getText().isEmpty())
					errorMessage("you must enter the update question");
				else {
					res = c.updateQuestionText(serialChoice, updateQuestionTextF.getText());
					infromationMessage(res);
					enablePressingOterButtons(false);
					reset();
				}

			}
		});

	}

	public void createAutomaticTest() {
		btCreateAutomaticTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				enablePressingOterButtons(true);
				reset();
				comboAllQuestionToTest.setValue(null);
				if (tglSortType.getSelectedToggle() != null) {
					tglSortType.getSelectedToggle().setSelected(false);
				}
				int numOfValidQuestions = c.getNumOfQuestionsForAutomaticTest();
				initCombo(comboAllQuestionToTest, numOfValidQuestions);
				lb1.setText("Choose number of questions for the test:");
				hb1.getChildren().setAll(lb1, comboAllQuestionToTest);
				lb2.setText("Choose sort type: ");
				vBox3.getChildren().setAll(rbDictionary, rbShortAnswers);
				hb2.getChildren().setAll(lb2, vBox3);
				hb3.getChildren().setAll(btCreateTest);
				vBox1.setVisible(true);
				hb1.setVisible(true);
				hb2.setVisible(true);
				hb3.setVisible(true);
				vBox3.setVisible(true);
				comboAllQuestionToTest.setVisible(true);
				stockQ.setText(c.showQuestionsAndAnswersStock());
				btCreateTest.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						if ((comboAllQuestionToTest.getValue() != null) && tglSortType.getSelectedToggle() != null) {
							Test automaticTest = c.createAutomaticTest(comboAllQuestionToTest.getValue());
							if (rbDictionary.isSelected())
								c.insertionSortByDictionary(automaticTest);
							if (rbShortAnswers.isSelected())
								c.insertionSortByAnswerLength(automaticTest);
							test.setText(c.printTest(automaticTest));
							testWithCorrectAnswers.setText(c.PrintTestWithAnswers(automaticTest));
							hb1.getChildren().setAll(test, testWithCorrectAnswers);
							hb2.setVisible(false);
							hb3.setVisible(false);
							enablePressingOterButtons(false);
							try {
								c.saveToFile(automaticTest);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}

						} else if (comboAllQuestionToTest.getValue() == null
								|| tglSortType.getSelectedToggle() == null) {
							errorMessage("You need to choose number od questions and sort type");
						}

					}
				});
			}
		});
	}

	public void createManualTest() {
		btCreateManualTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				enablePressingOterButtons(true);
				hb2.setVisible(false);
				hb3.setVisible(false);
				btOk.setDisable(false);
				comboNumOfQuestionsForManual.setDisable(false);
				initCombo(comboNumOfQuestionsForManual, c.getNumOfQuestions());
				lb9.setText("Choose number of questions in test:");
				hb1.getChildren().setAll(lb9, comboNumOfQuestionsForManual, btOk);
				hb1.setVisible(true);
				btOk.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if (comboNumOfQuestionsForManual.getValue() == null) {
							errorMessage("You must choose number of questions for test! ");
						} else {
							numOfQuestions = comboNumOfQuestionsForManual.getValue();
							btOk.setDisable(true);
							numOfQuestions = comboNumOfQuestionsForManual.getValue();
							initCombo(comboNumOfQuestion, c.getNumOfQuestions());
							comboNumOfQuestionsForManual.setDisable(true);
							Test manualTest = c.createManualTest();
							lb7.setText("Choose number of question to add: ");
							hb2.getChildren().setAll(lb7, comboNumOfQuestion, btOk2);
							hb2.setVisible(true);
							numForLimit = 1;
							btOk2.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									if (comboNumOfQuestion.getValue() == null) {
										errorMessage("You must choose number of question! ");
									} else {
										serialNumber = comboNumOfQuestion.getValue();
										if (numForLimit <= numOfQuestions) {
											if (c.checkIfQuestionExistInTest(serialNumber, manualTest)) {
												errorMessage("This question is already exists in the test!");
												comboNumOfQuestion.setValue(null);
											} else {
												boolean res = c.checkIfCQ(serialNumber);
												if (res) {
													initCheckBox(serialNumber);
													lb5.setText("Mark the answesrs you want to add:");
													hb2.getChildren().setAll(lb5);
													hb3.getChildren().setAll(vBox5);
													hb4.getChildren().setAll(btAddAnswers);
													hb3.setVisible(true);
													hb4.setVisible(true);
													btAddAnswers.setOnAction(new EventHandler<ActionEvent>() {

														@Override
														public void handle(ActionEvent arg0) {
															locations.clear();
															fChoice = false;
															for (int i = 0; i < answersCheckBox.size(); i++) {
																if (answersCheckBox.get(i).isSelected()) {
																	locations.add(i + 1);
																	fChoice = true;
																}
															}
															if (fChoice) {

																c.addCloseQuetionsForManualTest(serialNumber, locations,
																		manualTest);
																infromationMessage(
																		"The question with the answers added successfully!");
																hb2.getChildren().setAll(lb7, comboNumOfQuestion);
																hb3.setVisible(false);
																hb4.setVisible(false);
																numForLimit++;
																comboNumOfQuestion.setValue(null);
															} else {
																errorMessage("You must select at least one answer!");
															}

															if (numForLimit > numOfQuestions) {
																hb2.setVisible(false);
																testWithCorrectAnswers
																		.setText(c.PrintTestWithAnswers(manualTest));
																c.insertionSortByAnswerLength(manualTest);
																test.setText(c.printTest(manualTest));
																enablePressingOterButtons(false);
																hb1.getChildren().setAll(test, testWithCorrectAnswers);
																try {
																	c.saveToFile(manualTest);
																} catch (FileNotFoundException e) {
																	e.printStackTrace();
																}

															}

														}
													});

												} else {
													c.addOpenQuetionsForManualTest(serialNumber, manualTest);
													infromationMessage("The question added successfully!");
													numForLimit++;
													comboNumOfQuestion.setValue(null);
													if (numForLimit > numOfQuestions) {
														hb2.setVisible(false);
														testWithCorrectAnswers
																.setText(c.PrintTestWithAnswers(manualTest));
														c.insertionSortByAnswerLength(manualTest);
														test.setText(c.printTest(manualTest));
														hb1.getChildren().setAll(test, testWithCorrectAnswers);
														enablePressingOterButtons(false);
														try {
															c.saveToFile(manualTest);
														} catch (FileNotFoundException e) {
															e.getMessage();
														}

													}
												}
											}

										}

									}
								}
							});

						}
					}

				});

			}

		});
	}

	public void copyAnExistingTest() {
		btCopyAnExistingTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				enablePressingOterButtons(true);
				hb4.setVisible(false);
				hb3.setVisible(false);
				hb2.setVisible(false);
				hb1.setVisible(false);
				int numOfTestInStock = c.getNumOfTest();
				if (numOfTestInStock != 0) {
					initCombo(comboNumbersOfTests, numOfTestInStock);//
					allTestsToShow.setText(c.printAllTest());
					allTestsToShow.setVisible(true);
					hb1.getChildren().setAll(comboNumbersOfTests, allTestsToShow, btCreateCopyTest);
					hb1.setVisible(true);
					comboNumbersOfTests.setVisible(true);
					btCreateCopyTest.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {
							if (comboNumbersOfTests.getValue() != null) {
								try {
									c.copyAnExistingTest(comboNumbersOfTests.getValue());
								} catch (CloneNotSupportedException e) {
									e.getMessage();
								}
								infromationMessage("The test was copied successfully!");
								enablePressingOterButtons(false);
								hb1.setVisible(false);
							} else {
								errorMessage("You must choose number of test!");
							}
						}
					});
				} else {
					errorMessage("There is no tests in the stock ! ");
					enablePressingOterButtons(false);

				}
			}
		});
	}

	public String geAnswerFromTheUser() {
		return textAnswerF.getText();
	}

	public void setC(Controller c) {
		this.c = c;
	}

	public void designButton(Button b) {
		b.setMinSize(140, 35);
		b.setStyle("-fx-background-color: #FFB6C1");
		b.setTextFill(Color.WHITESMOKE);
		b.setFont(fontSize);
	}

	public void initCombo(ComboBox<Integer> combo, int num) {
		combo.getItems().clear();
		for (int i = 1; i <= num; i++) {
			combo.getItems().add(i);
		}
		setTitle(combo);
	}

	public void setTitle(ComboBox<Integer> combo) {
		combo.setButtonCell(new ListCell<>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(combo.getPromptText());
				} else {
					setText(item.toString());
				}
			}
		});
	}

	public void errorMessage(String message) {
		window = new Alert(AlertType.ERROR);
		window.setHeaderText(message);
		window.showAndWait();

	}

	public void infromationMessage(String message) {
		window = new Alert(AlertType.INFORMATION);
		window.setHeaderText(message);
		window.showAndWait();

	}

	public void initCheckBox(int serialNumber) {
		int numOfCheckBox = c.getNumOfAnswers(serialNumber);
		answersCheckBox.clear();
		vBox5.getChildren().clear();
		lb8.setText(c.getQuestionText(serialNumber));
		vBox5.getChildren().add(lb8);
		for (int i = 1; i <= numOfCheckBox; i++) {
			answersCheckBox.add(new CheckBox(c.getAnswerOfCqWithIndication(serialNumber, i)));
			vBox5.getChildren().add(answersCheckBox.get(i - 1));

		}

	}

	public void enablePressingOterButtons(boolean flag) {
		hbBottom.setDisable(flag);
		btAddQuestionAndAnswer.setDisable(flag);
		btCopyAnExistingTest.setDisable(flag);
		comboAllSerialNumbers.setDisable(flag);
		vBox2.setDisable(flag);
		btShowQuestionsAndAnswersStock.setDisable(false);
	}

	public void reset() {
		stockQ.setText(c.showQuestionsAndAnswersStock());
		vBox4.setVisible(false);
		vBox3.setVisible(false);
		hb4.setVisible(false);
		questionTextF.clear();
		updateQuestionTextF.clear();
		textAnswerF.clear();
		comboAmountOfAnswers.setDisable(false);
		updateAnswerText.setDisable(false);
		deleteAnswer.setDisable(false);
		updateQuestionText.setDisable(false);
		initCombo(comboAllSerialNumbers, c.getIdGenerator() - 1);
		if (tglChoice.getSelectedToggle() != null) {
			tglChoice.getSelectedToggle().setSelected(false);
		}
	}

}