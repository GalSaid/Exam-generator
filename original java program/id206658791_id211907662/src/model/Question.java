package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Question implements Serializable, Comparable<Question> {

	private static final long serialVersionUID = 1L;
	private static int idGenerator = 1;
	private String text;
	private int serialNumber;

	public Question(String text) {
		setText(text);
		serialNumber = idGenerator++;

	}

	public Question(Question other) {
		setText(other.text);
		serialNumber = other.serialNumber;

	}

	public static boolean setIdGenerator(int idGenerator) {
		Question.idGenerator = idGenerator;
		return true;
	}

	public static int getIdGenerator() {
		return idGenerator;
	}

	public String getText() {
		return text;
	}

	public boolean setText(String text) {
		this.text = text;
		return true;

	}

	public int getSerialNumber() {
		return serialNumber;
	}

	@Override
	public int compareTo(Question other) {
		return this.getNumOfCharsOfAllAnswers() - other.getNumOfCharsOfAllAnswers();

	}

	public abstract int getNumOfCharsOfAllAnswers();

	@Override
	public abstract boolean equals(Object obj);

	public String toString() {
		return text + "\n";
	}

	public String printQuestionWithSerialNumber() {
		return serialNumber + ") " + text + "\n";
	}

	@Override
	public Question clone() throws CloneNotSupportedException {
		return (Question) super.clone();
	}
}