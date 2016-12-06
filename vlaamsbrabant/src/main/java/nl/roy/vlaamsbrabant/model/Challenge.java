package nl.roy.vlaamsbrabant.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "challenges")
public class Challenge extends EntityModel 
{
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String week_nr;
	
	
	public void setTitle( String title )
	{
		this.title = title;
	}
	
    public void setWeek_nr( String week_nr )
    {
        this.week_nr = week_nr;
    }
	

	public String getWeek_nr() {
		return week_nr;
	}

	
	public String getTitle() {
		return title;
	}

	
}
