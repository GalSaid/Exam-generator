package model;

import java.sql.SQLException;

public interface ProgramActions {

	String showQuestionsAndAnswersStock();

	int addQuestionAndAnswer(String text, String typeQuestion, String Answer) throws SQLException;

	String updateQuestionText(int serialNumber, String text);
	String updateAnswerText(int serialNumber,int location,String text);

	String deleteAnswer(int serialNumber, int location);

	int createManualTest();

	int createAutomaticTest(int numOfQuestions);

	void exitProgram();

}