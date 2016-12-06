package nl.roy.vlaamsbrabant.model;

import java.security.Principal;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users") // noClassnameStored = true moet weg - morphia vindt 't kut
public class Users extends EntityModel implements Principal
{
	@NotEmpty
	private String username;
		
	@NotEmpty
	@Length(min = 3, max = 10)
	private String password;	

	@NotEmpty
	private String first_name;
	
	@NotEmpty
	private String last_name;

	@NotEmpty
	private int age;
	
	@NotEmpty
	private String gender;
	private double weight;
	private double length;
	private List<String> roles;
	
	private List<Exercise_Diaries> exercise_diaries;
	private List<Food_Diaries> food_diaries;
	private List<Scores> scores;
	
	public String getName()
	{
		return username;
	}
	
	public void setName( String username )
	{
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
    public void setPassword( String password )
    {
        this.password = password;
    }

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}
    
	public boolean hasRole(String role) {
		return roles.contains(role);
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<Exercise_Diaries> getExercise_diaries() {
		return exercise_diaries;
	}

	public void setExercise_diary(List<Exercise_Diaries> exercise_diaries) {
		this.exercise_diaries = exercise_diaries;
	}
	
	public List<Food_Diaries> getFood_diaries() {
		return food_diaries;
	}

	public void setFood_diary(List<Food_Diaries> food_diaries) {
		this.food_diaries = food_diaries;
	}

	public List<Scores> getScores() {
		return scores;
	}

	public void setScores(List<Scores> scores) {
		this.scores = scores;
	}

}
