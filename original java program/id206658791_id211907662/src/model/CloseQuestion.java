package model;

import java.io.Serializable;
import java.util.ArrayList;

public class CloseQuestion extends Question implements Serializable, Cloneable {

	public static enum eAddAnswer {
		Added_Successfully, Collection_Is_Full, Already_Exist
	};

	private static final long serialVersionUID = 1L;

	public static final int MAX_OP = 10;
	private Set<Answer> allAnswers;
	private Answer corecctAnswer;

	public CloseQuestion(String text) {
		super(text);
		allAnswers = new Set<>();

	}

	public CloseQuestion(CloseQuestion other) {
		super(other);
		allAnswers = new Set<Answer>();
		allAnswers.add(new Answer("There is more than one answer", false));
		allAnswers.add(new Answer("There is no correct answer",false));

	}

	@Override
	public int getNumOfCharsOfAllAnswers() {
		int numOfChars = 0;
		for (int i = 0; i < allAnswers.size(); i++) {
			numOfChars += allAnswers.get(i).getText().length();
		}
		return numOfChars;
	}

	public int getNumOfAnswers() {
		return allAnswers.size();
	}

	public eAddAnswer addAnswer(Answer answer) {
		if (allAnswers.size() == MAX_OP)
			return eAddAnswer.Collection_Is_Full;
		if (allAnswers.contains(answer))
			return eAddAnswer.Already_Exist;
		allAnswers.add(answer);
		// numOfAnswers++;
		return eAddAnswer.Added_Successfully;

	}

	public boolean setCorecctAnswer() {
		int count = 0;
		for (int i = 2; i < allAnswers.size(); i++) {
			if (allAnswers.get(i).getCorecct()) {
				count++;
				this.corecctAnswer = allAnswers.get(i);
			}
		}
		if (count > 1) {
			allAnswers.get(0).setCorecct(true);
			this.corecctAnswer = allAnswers.get(0);
		}
		if (count == 0) {
			allAnswers.get(1).setCorecct(true);
			this.corecctAnswer = allAnswers.get(1);
		}
		return true;

	}

	public Answer getCorecctAnswer() {
		return corecctAnswer;
	}

	public int getNumOfFalseAnswers() {
		int counter = 0;
		for (int i = 0; i < allAnswers.size(); i++) {
			if (allAnswers.get(i).getCorecct() == false)
				counter++;
		}
		return counter;
	}

	public void deleteAnswer(int location) {
		allAnswers.remove(location - 1);
		// numOfAnswers--;
	}

	public void updateAnswer(int location, String text) {
		allAnswers.get(location - 1).setText(text);
	}

	public Answer getAnswerByLoaction(int location) {
		return allAnswers.get(location - 1);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloseQuestion other = (CloseQuestion) obj;
		return other.getText().equalsIgnoreCase(getText());

	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(super.toString());
		for (int i = 0; i < allAnswers.size(); i++) {
			sb.append("\t(" + (i + 1) + ") " + allAnswers.get(i).toString() + "\n");
		}
		return sb.toString();

	}

	public String printQuestionWithIndications() {
		StringBuffer sb = new StringBuffer(super.printQuestionWithSerialNumber());
		for (int i = 0; i < allAnswers.size(); i++) {
			sb.append("\t(" + (i + 1) + ") " + allAnswers.get(i).toString() + "--->" + allAnswers.get(i).getCorecct()
					+ "\n");

		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public CloseQuestion clone() throws CloneNotSupportedException {
		CloseQuestion temp = (CloseQuestion) super.clone();
		temp.setAllAnswers(new Set<Answer>());
		for (int i = 0; i < allAnswers.size(); i++) {
			temp.addAnswer(this.allAnswers.get(i).clone());
		}
		return temp;
	}


	public void setAllAnswers(Set<Answer> allAnswers) {
		this.allAnswers = allAnswers;
	}
	
	

}
