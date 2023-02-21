package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

//import model.CloseQuestion.eAddAnswer;

public class Management {
	private int questionIdGenerator;
	private int testIdGenerator;
	private static SqlConnector mySql = null;

	public static enum eAddAnswer {
		Added_Successfully, Collection_Is_Full, Already_Exist, Error_In_SQL
	};

	public void setSql(String userName, String password) throws ClassNotFoundException, SQLException {
		mySql = new SqlConnector(userName, password);
		questionIdGenerator = mySql.getNumOfQuestions() + 1;
		testIdGenerator = mySql.getNumOfTests() + 1;
	}

	public int createTest(String testType) {
		mySql.createTest(testIdGenerator, testType);
		return testIdGenerator++;
	}

	public int getQuestionIdGenerator() {
		return mySql.getNumOfQuestions();
	}

	public int getNumOfTests() {
		return testIdGenerator - 1;
	}

	public boolean getAnswerIndication(int serialNumber, int location) {
		return mySql.getAnswerIndication(serialNumber, location);
	}

	public String getAnswerTextAndIndicationForCQ(int serialNumber, int location) {
		return mySql.getAnswerTextAndIndicationForCQ(serialNumber, location);
	}

	public int getNumOfQuestions() {
		return mySql.getNumOfQuestions();
	}

	public int getNumOfFalseAnswers(int serialNumber) {
		return mySql.getNumOfFalseAnswersInCQ(serialNumber);
	}

	public boolean checkIfCQ(int serialNumber) {
		return mySql.checkIfCQ(serialNumber);
	}

	public int getNumOfAnswersInQuestion(int serialNumber) {
		return mySql.getNumOfAnswersInQuestion(serialNumber);
	}

	public int addOpenQuestion(String text, String corecctAnswer) throws SQLException {
		if (mySql.checkIfQuestionExist(text, "open"))
			return -1;
		mySql.addOpenQuestion(questionIdGenerator, text, corecctAnswer);
		return questionIdGenerator++;
	}

	public int addCloseQuestion(String text) throws SQLException {
		if (mySql.checkIfQuestionExist(text, "close"))
			return -1;
		mySql.addCloseQuestion(questionIdGenerator, text);
		return questionIdGenerator++;
	}

	public eAddAnswer addAnswers(int questionId, String text, boolean isCorecct) throws SQLException {
		mySql.addAnswerToTable(text);
		return mySql.addAnswerToCloseQuestion(questionId, text, isCorecct);

	}

	public String toString() {
		try {
			return mySql.printAllQuestionsWithAnswers();
		} catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
			return null;
		}

	}

	public String getQuestionBySerialNumber(int serialNumber) {
		return mySql.getQuestionBySerialNumber(serialNumber);
	}

	public String updateQuestion(int serialNumber, String text) {
		if (!mySql.updateQuestion(serialNumber, text))
			return "There is already a question with such wording";
		return "Successfully updated!";
	}

	public String deleteAnswer(int serialNumber, int location) {
		mySql.deleteAnswer(serialNumber, location);
		return "Successfully deleted!";

	}

	public String updateAnswer(int serialNumber, int location, String text) {
		if (location != -1 && mySql.checkIfAnswerInCQ(serialNumber, text))
			return "Answer already exist";
		mySql.updateAnswerText(serialNumber, location, text);
		return "Your answer successfully updated!";
	}

	public boolean checkIfQuestionExistInTest(int qId, int testId) {
		return mySql.checkIfQuestionExistInTest(qId, testId);

	}

	public int getNumOfQuestionForAutomaticTest() {
		int counter = mySql.getNumOfQuestions();
		int numOfQuestions = counter;
		for (int i = 1; i <= numOfQuestions; i++) {
			if (mySql.checkIfCQ(i)) {
				if (mySql.getNumOfFalseAnswersInCQ(i) < 3) {
					counter--;
				}
			}
		}
		return counter;
	}

	public void addCloseQuestionAndAnswersToTest(int serialNumber, ArrayList<Integer> locations, int testId) {
		mySql.addCloseQuestionAndAnswersToTest(serialNumber, locations, testId);
	}

	public void addOpenQuestionToTest(int qId, int testId) {
		mySql.addOpenQuestionToTest(qId, testId);
	}

	public String testQuestionsWithCorecctAnswers(int testId) {
		return mySql.testQuestionsWithCorecctAnswers(testId);
	}

	public String testToString(int testId) {
		return mySql.testToString(testId);

	}

	public void examSave(String examFileName, String questionnaire) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(examFileName));
		pw.println(questionnaire);
		pw.close();
	}

	public void solutionSave(String solutionFileName, String examWithAnswers) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(solutionFileName));
		pw.println(examWithAnswers);
		pw.close();
	}

	public void exit() throws SQLException {
		mySql.close();
	}

}