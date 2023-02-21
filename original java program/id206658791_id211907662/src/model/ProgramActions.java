package model;

public interface ProgramActions {

	String showQuestionsAndAnswersStock();

	Question addQuestionAndAnswer(String text, String typeQuestion, String Answer);

	String updateQuestionText(int serialNumber, String text);
	String updateAnswerText(int serialNumber,int location,String text);

	String deleteAnswer(int serialNumber, int location);

	Test createManualTest();

	Test createAutomaticTest(int numOfQuestions);

	void copyAnExistingTest(int num) throws CloneNotSupportedException;

	void exitProgram();

}