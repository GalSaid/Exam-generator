package model;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import model.CloseQuestion.eAddAnswer;

public class Management implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Question> allQuestions;
	private ArrayList<Test> allTests;

	public Management() {
		allQuestions = new ArrayList<Question>();
		allTests = new ArrayList<Test>();
	}

	public Test createTest() {
		Test test = new Test();
		allTests.add(test);
		return test;
	}

	public void setIdGenerator(int idGenerator) {
		Question.setIdGenerator(idGenerator);
	}

	public int getIdGenerator() {
		return Question.getIdGenerator();
	}

	public int getNumOfTests() {
		return allTests.size();
	}

	public boolean getAnswerIndication(int serialNumber, int location) {
		CloseQuestion cQ = (CloseQuestion) getQuestionBySerialNumber(serialNumber);
		Answer answer = cQ.getAnswerByLoaction(location);
		return answer.getCorecct();
	}
	
	public String getAnswerTextAndIndicationForCQ(int serialNumber,int location) {
	 CloseQuestion cQ=(CloseQuestion)getQuestionBySerialNumber(serialNumber);
	 Answer ans = cQ.getAnswerByLoaction(location);
	 return ans.toString() +"--->"+ ans.getCorecct();
		
	}

	public int getNumOfQuestions() {
		return allQuestions.size();
	}

	public int getNumOfFalseAnswers(int serialNumber) {
		CloseQuestion cQ = (CloseQuestion) getQuestionBySerialNumber(serialNumber);
		return cQ.getNumOfFalseAnswers();
	}

	public boolean checkIfCQ(int serialNumber) {
		Question q = getQuestionBySerialNumber(serialNumber);
		if (q instanceof OpenQuestion)
			return false;
		return true;
	}

	public String getQuestion(int serialNumber) {
		Question q = getQuestionBySerialNumber(serialNumber);
		if (q instanceof OpenQuestion) {
			q = (OpenQuestion) q;
		} else {
			q = (CloseQuestion) q;
		}
		return q.toString();

	}

	public int getNumOfAnswersInQuestion(int serialNumber) {
		CloseQuestion cQ = (CloseQuestion) getQuestionBySerialNumber(serialNumber);
		return cQ.getNumOfAnswers();
	}

	public OpenQuestion addOpenQuestion(String text, String corecctAnswer) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQuestion) {
				if (allQuestions.get(i).getText().equalsIgnoreCase(text))
					return null;
			}
		}
		allQuestions.add(new OpenQuestion(text, corecctAnswer));
		return (OpenQuestion) allQuestions.get(allQuestions.size() - 1);
	}

	public CloseQuestion addCloseQuestion(String text) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof CloseQuestion) {
				if (allQuestions.get(i).getText().equalsIgnoreCase(text))
					return null;

			}
		}
		allQuestions.add(new CloseQuestion(text));
		return (CloseQuestion) allQuestions.get(allQuestions.size() - 1);
	}

	public eAddAnswer addAnswers(CloseQuestion newQuestion, String text, boolean isCorecct) {
		Answer answer = new Answer(text, isCorecct);
		return newQuestion.addAnswer(answer);

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQuestion) {
				OpenQuestion oQ = (OpenQuestion) allQuestions.get(i);
				sb.append(oQ.printQuestionWithAnswer());
			} else {
				CloseQuestion cQ = (CloseQuestion) allQuestions.get(i);
				sb.append(cQ.printQuestionWithIndications());
			}

		}
		return sb.toString();

	}

	public Question getQuestionBySerialNumber(int serialNumber) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i).getSerialNumber() == serialNumber)
				return allQuestions.get(i);
		}
		return null;

	}

	public String updateQuestion(int serialNumber, String text) {
		Question q = getQuestionBySerialNumber(serialNumber);
		if (q == null)
			return "There is no such question..";
		boolean check = checkIfCQ(serialNumber);
		for (int i = 0; i < allQuestions.size(); i++) {
			if (check) {
				if (allQuestions.get(i) instanceof CloseQuestion) {
					if (text.equalsIgnoreCase(allQuestions.get(i).getText()))
						return "There is already a question with such wording";
				}
			} else if (!check) {
				if (allQuestions.get(i) instanceof OpenQuestion) {
					if (text.equalsIgnoreCase(allQuestions.get(i).getText()))
						return "There is already a question with such wording";
				}
			}
		}
		q.setText(text);
		return "Successfully updated!";
	}

	public String deleteAnswer(int serialNumber, int location) {
		Question q = getQuestionBySerialNumber(serialNumber);
		if (q == null)
			return "There is no such question..";
		if (!checkIfCQ(serialNumber))
			return "You cannot delete answer from open question..";
		if (getNumOfAnswersInQuestion(serialNumber) <= 4)
			return "There are minimum answers , can not be deleted";
		CloseQuestion cQ = (CloseQuestion) q;
		if (location > cQ.getNumOfAnswers() || location < 1)
			return "There is no such answer..";
		cQ.deleteAnswer(location);
		return "Successfully deleted!";

	}

	public String updateAnswer(int serialNumber, int location, String text) {
		Question q = getQuestionBySerialNumber(serialNumber);
		if (q == null)
			return "There is no such question..";
		if (q instanceof OpenQuestion) {
			OpenQuestion oQ = (OpenQuestion) q;
			oQ.setCorecctAnswer(text);
			return "Your answer successfully updated!";
		}
		CloseQuestion cQ = (CloseQuestion) q;
		if (location > cQ.getNumOfAnswers() || location < 1)
			return "There is no such answer..";
		for (int i = 0; i < cQ.getNumOfAnswers(); i++) {
			if (cQ.getAnswerByLoaction(i + 1).getText().equalsIgnoreCase(text))
				return "Answer already exist";
		}
		cQ.updateAnswer(location, text);
		return "Your answer successfully updated!";
	}

	public boolean checkIfQuestionExistInTest(int serialNumber, Test test) {
		return test.checkIfQuestionExist(serialNumber);

	}

	public int getNumOfQuestionForAutomaticTest() {
		int counter = allQuestions.size();
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof CloseQuestion) {
				CloseQuestion cQ = (CloseQuestion) allQuestions.get(i);
				if (cQ.getNumOfFalseAnswers() < 3) {
					counter--;
				}
			}
		}
		return counter;
	}

	public void addCloseQuestionAndAnswersToTest(int serialNumber, ArrayList<Integer> locations, Test test) {
		CloseQuestion cQ = (CloseQuestion) getQuestionBySerialNumber(serialNumber);
		test.addCloseQuestionWithAnswers(cQ, locations);

	}

	public void addOpenQuestionToTest(int serialNumber, Test test) {
		OpenQuestion oQ = (OpenQuestion) getQuestionBySerialNumber(serialNumber);
		test.addOpenQuestion(oQ);
	}

	public String testQuestionsWithCorecctAnswers(Test test) {
		return test.testWithCorecctAnswers();
	}

	public String testToString(Test test) {
		return test.toString();

	}

	public void examSave(Test test, String examFileName) throws FileNotFoundException {
		test.examSave(examFileName);
	}

	public void solutionSave(Test test, String solutionFileName) throws FileNotFoundException {
		test.solutionSave(solutionFileName);
	}

	public void insertionSort(Test test) {
		test.insertionSort();
	}

	public void insertionSortByAnswersLength(Test test) {
		test.insertionSortByAnswersLength();
	}

	public Test cloneTest(int location) throws CloneNotSupportedException {
		return allTests.get(location - 1).clone();
	}

	public String allTestsToString() {
		StringBuffer sb= new StringBuffer();
		for (int i = 0; i < allTests.size(); i++) {
			sb.append("Test number " + (i + 1) + ":\n" + allTests.get(i).toString());
			sb.append("\n______________________________________________________\n");
		}
		return sb.toString();

	}
	

	public Test getTest(int index) {
		return allTests.get(index - 1);
	}

}