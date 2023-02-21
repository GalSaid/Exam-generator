package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Test implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Question> testQuestions;

	public Test() {
		testQuestions = new ArrayList<Question>();
	}


	@Override
	public Test clone() throws CloneNotSupportedException {
		Test temp = (Test) super.clone();
		temp.setTestQuestions(new ArrayList<Question>());
		for (int i = 0; i < this.testQuestions.size(); i++) {
			temp.testQuestions.add(i, this.testQuestions.get(i).clone());
		}

		return temp;
	}


	public void setTestQuestions(ArrayList<Question> testQuestions) {
		this.testQuestions = testQuestions;
	}

	public int getNumOfQuestions() {
		return testQuestions.size();
	}

	public void examSave(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.println(toString());
		pw.close();

	}

	public void solutionSave(String fileName) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(fileName));
		pw.println(testWithCorecctAnswers());
		pw.close();

	}

	public void addCloseQuestionWithAnswers(CloseQuestion cQ, ArrayList<Integer> locations) {
		testQuestions.add(new CloseQuestion(cQ));
		for (int i = 0; i < locations.size(); i++) {
			Answer answer = cQ.getAnswerByLoaction(locations.get(i));
			((CloseQuestion) testQuestions.get(testQuestions.size() - 1)).addAnswer(answer);

		}
		((CloseQuestion) testQuestions.get(testQuestions.size() - 1)).setCorecctAnswer();
	}

	public void addOpenQuestion(OpenQuestion oQ) {
		testQuestions.add(oQ);
	}

	public boolean checkIfQuestionExist(int serialNumber) {
		for (int i = 0; i < testQuestions.size(); i++) {
			if (testQuestions.get(i).getSerialNumber() == serialNumber)
				return true;
		}
		return false;

	}

	public void insertionSortByAnswersLength() {
		for (int i = 1; i < testQuestions.size(); i++) {
			boolean isAfter = true;
			for (int j = i; j > 0 && isAfter; j--) {
				int num = testQuestions.get(j - 1).compareTo(testQuestions.get(j));
				isAfter = num > 0;
				if (isAfter) {
					Question temp = testQuestions.get(j);
					testQuestions.set(j, testQuestions.get(j - 1));
					testQuestions.set(j - 1, temp);
				}
			}
		}
	}

	public void insertionSort() {
		for (int i = 1; i < testQuestions.size(); i++) {
			boolean isAfter = true;
			for (int j = i; j > 0 && isAfter; j--) {
				int num = testQuestions.get(j - 1).getText().compareToIgnoreCase(testQuestions.get(j).getText());
				isAfter = num > 0;
				if (isAfter) {
					Question temp = testQuestions.get(j);
					testQuestions.set(j, testQuestions.get(j - 1));
					testQuestions.set(j - 1, temp);
				}
			}
		}
	}

	public String testWithCorecctAnswers() {
		StringBuffer sb = new StringBuffer("The questions of the test with the corecct answers are : \n");
		for (int i = 0; i < testQuestions.size(); i++) {
			if (testQuestions.get(i) instanceof OpenQuestion) {
				OpenQuestion oQ = (OpenQuestion) testQuestions.get(i);
				sb.append((i + 1) + ") " + oQ.toString() + oQ.getCorecctAnswer() + "\n\n");
			} else {
				CloseQuestion cQ = (CloseQuestion) testQuestions.get(i);
				sb.append((i + 1) + ") " + cQ.getText() + "\n" + cQ.getCorecctAnswer().getText() + "\n\n");
			}
		}
		return sb.toString();

	}

	public String toString() {
		StringBuffer sb = new StringBuffer("The test is : \n");
		for (int i = 0; i < testQuestions.size(); i++) {
			if (testQuestions.get(i) instanceof OpenQuestion) {
				OpenQuestion oQ = (OpenQuestion) testQuestions.get(i);
				sb.append((i + 1) + ") " + oQ.toString());
			} else {
				CloseQuestion cQ = (CloseQuestion) testQuestions.get(i);
				sb.append((i + 1) + ") " + cQ.toString());
			}
			sb.append("\n");
		}
		return sb.toString();

	}
	
	public Question getQuestion(int index) {
		return testQuestions.get(index-1);
	}

}
