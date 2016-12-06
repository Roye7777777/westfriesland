package nl.roy.vlaamsbrabant.model;

public class Food_Diaries {
	public int week_nr;
	public String ontbijt;
	public String lunch;
	public String diner;
	public String snacks;
	
	public int getWeek_nr() {
		return week_nr;
	}
	public void setWeek_nr(int week_nr) {
		this.week_nr = week_nr;
	}
	public String getOntbijt() {
		return ontbijt;
	}
	public void setOntbijt(String ontbijt) {
		this.ontbijt = ontbijt;
	}
	public String getLunch() {
		return lunch;
	}
	public void setLunch(String lunch) {
		this.lunch = lunch;
	}
	public String getDiner() {
		return diner;
	}
	public void setDiner(String diner) {
		this.diner = diner;
	}
	public String getSnacks() {
		return snacks;
	}
	public void setSnacks(String snacks) {
		this.snacks = snacks;
	}
}
