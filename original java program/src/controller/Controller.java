package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.StringConcatFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.CloseQuestion;
import model.CloseQuestion.eAddAnswer;
import model.Management;
import model.OpenQuestion;
import model.ProgramActions;
import model.Question;
import model.Test;
import view.View;

public class Controller implements ProgramActions, Serializable {
	private static final long serialVersionUID = 1L;
	private Management model;
	private View view;

	public Controller(Management m, View v) {
		model = m;
		view = v;
		view.setC(this);
	}

	@Override
	public String showQuestionsAndAnswersStock() {
		return model.toString();
	}

	@Override
	public Question addQuestionAndAnswer(String text, String typeQuestion, String answer) {
		if (typeQuestion.equalsIgnoreCase("close question"))
			return (Question) model.addCloseQuestion(text);
		return (Question) model.addOpenQuestion(text, answer);

	}

	public eAddAnswer addAnswerToCloseQuestion(CloseQuestion newQuestion, String answer, boolean isCorecct) {
		return model.addAnswers(newQuestion, answer, isCorecct);
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
	public Test createManualTest() {
		return model.createTest();

	}

	public void addCloseQuetionsForManualTest(int setrialNumber, ArrayList<Integer> locations, Test t) {
		model.addCloseQuestionAndAnswersToTest(setrialNumber, locations, t);

	}

	public void addOpenQuetionsForManualTest(int setrialNumber, Test t) {
		model.addOpenQuestionToTest(setrialNumber, t);

	}

	public String getQuestionText(int serialNumber) {
		Question q = model.getQuestionBySerialNumber(serialNumber);
		return q.getText();
	}

	public void saveToFile(Test test) throws FileNotFoundException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
		Date d = new Date();
		String examFileName = "exam_" + df.format(d);
		examFileName += updateFileName(examFileName);
		String solutionFileName = "solution_" + df.format(d);
		solutionFileName += updateFileName(solutionFileName);
		model.examSave(test, examFileName);
		model.solutionSave(test, solutionFileName);
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

	public boolean checkIfQuestionExistInTest(int serialNumber, Test t) {
		return model.checkIfQuestionExistInTest(serialNumber, t);
	}

	@Override
	public Test createAutomaticTest(int numOfQuestions) {
		Test automaticTest = model.createTest();
		for (int i = 0; i < numOfQuestions; i++) {
			int serialNumber = (int) (Math.random() * model.getNumOfQuestions() + 1);
			boolean isExists = model.checkIfQuestionExistInTest(serialNumber, automaticTest);
			while (isExists) {
				serialNumber = (int) (Math.random() * model.getNumOfQuestions() + 1);
				isExists = model.checkIfQuestionExistInTest(serialNumber, automaticTest);
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
					model.addCloseQuestionAndAnswersToTest(serialNumber, locations, automaticTest);

				} else {
					i--;
				}
			} else if (!checkType) {
				model.addOpenQuestionToTest(serialNumber, automaticTest);
			}
		}
		return automaticTest;
	}

	@Override
	public void copyAnExistingTest(int num) throws CloneNotSupportedException {
		model.cloneTest(num);
	}

	@Override
	public void exitProgram() {
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("model.dat"));
			outFile.writeInt(model.getIdGenerator());
			outFile.writeObject(model);
			outFile.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public void openBinaryFile() {
		try {
			ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("model.dat"));
			model.setIdGenerator(inFile.readInt());
			model = (Management) inFile.readObject();
			inFile.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
	}

	public int getIdGenerator() {
		return model.getIdGenerator();
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

	public void insertionSortByDictionary(Test test) {
		model.insertionSort(test);
	}

	public void insertionSortByAnswerLength(Test test) {
		model.insertionSortByAnswersLength(test);
	}

	public String PrintTestWithAnswers(Test test) {
		return model.testQuestionsWithCorecctAnswers(test);
	}

	public String printTest(Test test) {
		return model.testToString(test);
	}

	public String printAllTest() {
		return model.allTestsToString();
	}

	public int getNumOfTest() {
		return model.getNumOfTests();
	}

	public String getAnswerOfCqWithIndication(int serialNumber, int location) {
		return model.getAnswerTextAndIndicationForCQ(serialNumber, location);
	}

	public int getNumOfQuestions() {
		return model.getNumOfQuestions();
	}

}