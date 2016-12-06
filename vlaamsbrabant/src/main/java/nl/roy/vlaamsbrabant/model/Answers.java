package nl.roy.vlaamsbrabant.model;

import java.util.List;

public class Answers {
	public int week_nr;
	public List<String> answers;
	
	public int getWeek_nr() {
		return week_nr;
	}
	public void setWeek_nr(int week_nr) {
		this.week_nr = week_nr;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
}
