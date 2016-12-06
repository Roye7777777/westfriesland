package nl.roy.vlaamsbrabant.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "questions") // noClassnameStored = true
public class Questions extends EntityModel
{
	@NotEmpty
	private int week_nr;
	
	@NotEmpty
	private int question_nr;
	
	@NotEmpty
	private String question;	
	
	@NotEmpty
	private String a;
	
	@NotEmpty
	private String b;
	
	private String c;
	private String d;
	private String e;
	private String f;
	
	@NotEmpty
	private String correct;

	public int getWeek_nr() {
		return week_nr;
	}

	public void setWeek_nr(int week_nr) {
		this.week_nr = week_nr;
	}

	public int getQuestion_nr() {
		return question_nr;
	}

	public void setQuestion_nr(int question_nr) {
		this.question_nr = question_nr;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}
}
