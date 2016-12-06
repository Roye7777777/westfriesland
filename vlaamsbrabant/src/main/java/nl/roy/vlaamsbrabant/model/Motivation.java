package nl.roy.vlaamsbrabant.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "motivation")
public class Motivation extends EntityModel
{
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String text;
	
//	private boolean married;
	
	
	public String getName()
	{
		return title;
	}
	
	public void setName( String title )
	{
		this.title = title;
	}
	
    public void setText( String text )
    {
        this.text = text;
    }
	

	public String getText() {
		return text;
	}


}
