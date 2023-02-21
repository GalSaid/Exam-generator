package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Management.eAddAnswer;
import model.Management;
import model.ProgramActions;
import view.EnterView;

public class Controller implements ProgramActions {
	private Management model;
	private EnterView view;

	public Controller(Management m, EnterView v) {
		model = m;
		view = v;
		view.setC(this);

	}

	@Override
	public String showQuestionsAndAnswersStock() {
		return model.toString();
	}

	@Override
	public int addQuestionAndAnswer(String text, String typeQuestion, String answer) {
		try {
			if (typeQuestion.equalsIgnoreCase("close question"))
				return model.addCloseQuestion(text);
			return model.addOpenQuestion(text, answer);

		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();

			}
			return -1;
		}
	}

	public eAddAnswer addAnswerToCloseQuestion(int questionId, String answer, boolean isCorecct) {
		try {
			return model.addAnswers(questionId, answer, isCorecct);
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();

			}
			return null;
		}
	}

	@Override
	public String updateQuestionText(int serialNumber, String text) {
		return model.updateQuestion(serialNumber, text);
	}

	@Override
	public String updateAnswerText(int serialNumber, int location, String text) {
		return model.updateAnswer(serialNumber, location, text);
	}

	@Override
	public String deleteAnswer(int serialNumber, int location) {
		return model.deleteAnswer(serialNumber, location);
	}

	@Override
	public int createManualTest() {
		return model.createTest("manual");

	}

	public void addCloseQuetionsForManualTest(int setrialNumber, ArrayList<Integer> locations, int t) {
		model.addCloseQuestionAndAnswersToTest(setrialNumber, locations, t);

	}

	public void addOpenQuetionsForManualTest(int setrialNumber, int t) {
		model.addOpenQuestionToTest(setrialNumber, t);
	}

	public String getQuestionText(int serialNumber) {
		return model.getQuestionBySerialNumber(serialNumber);
	}

	public void saveToFile(String questionnaire, String examWithAnswers) throws FileNotFoundException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
		Date d = new Date();
		String examFileName = "exam_" + df.format(d);
		examFileName += updateFileName(examFileName);
		String solutionFileName = "solution_" + df.format(d);
		solutionFileName += updateFileName(solutionFileName);
		model.examSave(examFileName, questionnaire);
		model.solutionSave(solutionFileName, examWithAnswers);
	}

	public static String updateFileName(String fileName) {
		String st = "";
		boolean isExsit = true;
		if (new File(fileName).exists()) {
			for (int i = 2; isExsit; i++) {
				String temp = fileName;
				temp += "(" + i + ")";
				if (!new File(temp).exists()) {
					isExsit = false;
					st = "(" + i + ")";
				}
			}
		}
		return st;
	}

	public boolean checkIfQuestionExistInTest(int serialNumber, int t) {
		return model.checkIfQuestionExistInTest(serialNumber, t);
	}

	@Override
	public int createAutomaticTest(int numOfQuestions) {
		int automaticTestId = model.createTest("automatic");
		for (int i = 0; i < numOfQuestions; i++) {
			int serialNumber = (int) (Math.random() * model.getNumOfQuestions() + 1);
			boolean isExists = model.checkIfQuestionExistInTest(serialNumber, automaticTestId);
			while (isExists) {
				serialNumber = (int) (Math.random() * model.getNumOfQuestions() + 1);
				isExists = model.checkIfQuestionExistInTest(serialNumber, automaticTestId);
			}
			boolean checkType = model.checkIfCQ(serialNumber);
			if (checkType) {
				int numOfFalseAnswers = model.getNumOfFalseAnswers(serialNumber);
				if (numOfFalseAnswers >= 3) {
					ArrayList<Integer> locations = new ArrayList<Integer>();
					int numOfCorecct = 0;
					for (int j = 0; j < 4; j++) {
						int answerNumber = (int) (Math.random() * model.getNumOfAnswersInQuestion(serialNumber) + 1);
						if (locations.contains(answerNumber)) {
							j--;
						} else {
							boolean indication = model.getAnswerIndication(serialNumber, answerNumber);
							if (indication == true) {
								if (numOfCorecct >= 1) {
									j--;
								} else {
									locations.add(j, answerNumber);
									numOfCorecct++;
								}
							} else if (indication == false) {
								locations.add(j, answerNumber);
							}
						}
					}
					model.addCloseQuestionAndAnswersToTest(serialNumber, locations, automaticTestId);

				} else {
					i--;
				}
			} else if (!checkType) {
				model.addOpenQuestionToTest(serialNumber, automaticTestId);
			}
		}
		return automaticTestId;
	}

	@Override
	public void exitProgram() {
		try {
			model.exit();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
		}
	}

	public int getIdGenerator() {
		return model.getQuestionIdGenerator();
	}

	public String checkIfCanDeleteAnswer(int serialNumber) {
		if (!model.checkIfCQ(serialNumber))
			return "You can't delete answer from open question";
		if (model.getNumOfAnswersInQuestion(serialNumber) <= 4)
			return "There are minimum answers , can not be deleted";
		return "you can delete";

	}

	public boolean checkIfCQ(int serialNumber) {
		return model.checkIfCQ(serialNumber);

	}

	public int getNumOfAnswers(int serialNumber) {
		return model.getNumOfAnswersInQuestion(serialNumber);
	}

	public int getNumOfQuestionsForAutomaticTest() {
		return model.getNumOfQuestionForAutomaticTest();
	}

	public String PrintTestWithAnswers(int testId) {
		return model.testQuestionsWithCorecctAnswers(testId);
	}

	public String printTest(int testId) {
		return model.testToString(testId);
	}

	public String getAnswerOfCqWithIndication(int serialNumber, int location) {
		return model.getAnswerTextAndIndicationForCQ(serialNumber, location);
	}

	public int getNumOfQuestions() {
		return model.getNumOfQuestions();
	}

	public String createConnection(String userName, String password) {
		try {
			model.setSql(userName, password);
			return "ok";
		} catch (ClassNotFoundException e) {
			return "Error in open!";
		} catch (SQLException e) {
			return "incorrect values!";
		}
	}

}