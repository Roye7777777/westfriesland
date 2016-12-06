package nl.roy.vlaamsbrabant.presentation.model;

import java.util.List;

import nl.roy.vlaamsbrabant.model.Exercise_Diaries;
import nl.roy.vlaamsbrabant.model.Food_Diaries;

public class UserView
{
    public String id;
    public String username;
    public String password;	
    public String first_name;
    public String last_name;
    public int age;
    public String gender;
    public double weight;
    public double length;
    public List<String> roles;
	public List<Exercise_Diaries> exercise_diary;
	public List<Food_Diaries> food_diary;
}
