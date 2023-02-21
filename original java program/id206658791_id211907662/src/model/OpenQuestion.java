package model;

import java.io.Serializable;

public class OpenQuestion extends Question implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String corecctAnswer;

	public OpenQuestion(String text, String corecctAnswer) {
		super(text);
		setCorecctAnswer(corecctAnswer);

	}

	public String getCorecctAnswer() {
		return corecctAnswer;
	}

	public boolean setCorecctAnswer(String corecctAnswer) {
		this.corecctAnswer = corecctAnswer;
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenQuestion other = (OpenQuestion) obj;
		return other.getText().equalsIgnoreCase(getText());

	}

	public String printQuestionWithAnswer() {
		return super.printQuestionWithSerialNumber() + "\t" + corecctAnswer + "\n\n";
	}

	@Override
	public int getNumOfCharsOfAllAnswers() {
		return corecctAnswer.length();
	}

	@Override
	public OpenQuestion clone() throws CloneNotSupportedException {
		return (OpenQuestion) super.clone();
	}

}
