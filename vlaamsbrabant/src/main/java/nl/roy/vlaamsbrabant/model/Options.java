package nl.roy.vlaamsbrabant.model;

public class Options extends Questions
{
	private String text;
	private int correct;

	public String getOptions() {
		return "{\"A\": { \"text\": \"" + text + "\", \"correct\": " + correct + "} }";
	}
}
