package nl.roy.vlaamsbrabant.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Exercise_Diaries;
import nl.roy.vlaamsbrabant.model.Food_Diaries;
import nl.roy.vlaamsbrabant.model.Scores;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.presentation.model.Exercise_DiaryView;
import nl.roy.vlaamsbrabant.presentation.model.Food_DiaryView;
import nl.roy.vlaamsbrabant.presentation.model.UserView;

public class UserDAO extends BaseDAO< Users >
{
    @Inject
    public UserDAO(Datastore ds)
    {
    	super (Users.class, ds);
    }
    
    /* 
     * 
     * GETS 
     * 
     */
    
    // Return one document from users by a given name
	public Users getByName(String username) 
	{
		Query<Users> q = createQuery().field( "username" ).equal(username);
		
		return findOne( q );
	}   

	// Return users' exercise diaries
	public List<Exercise_Diaries> getExerciseDiaries(String id) {
		Users user = get(id);
		
		return user.getExercise_diaries();
	}
	
	// Return users' food diaries
	public List<Food_Diaries> getFoodDiaries(String id) {
		Users user = get(id);
		
		return user.getFood_diaries();
	}

	// Return users' exercise diary per week
	public Exercise_Diaries getExerciseDiary(String id, int week_nr) {
    	Users user = get(id);
    	List<Exercise_Diaries> ed = user.getExercise_diaries();
    	Exercise_Diaries exercise_Diary = new Exercise_Diaries();
    	
    	for (Exercise_Diaries e : ed) {
    		if (e.getWeek_nr() == week_nr) { 
    			exercise_Diary = e;
    		}
    	}
    	return exercise_Diary;
	}
	
	// Return users' exercise diary per week
	public Food_Diaries getFoodDiary(String id, int week_nr) {
    	Users user = get(id);
    	List<Food_Diaries> fd = user.getFood_diaries();
    	Food_Diaries food_Diary = new Food_Diaries();
    	
    	for (Food_Diaries f : fd) {
    		if (f.getWeek_nr() == week_nr) 
    			food_Diary = f;
    	}
    	
    	return food_Diary;
	}

	// get scores from users by userId
	public List<Scores> getScores(String id) {
		Users user = get(id);
		
		return user.getScores();
	}
	
	// Return users' score per week
	public Scores getScore(String id, int week_nr) {
    	Users user = get(id);
    	List<Scores> us = user.getScores();
    	Scores score = new Scores();
    	
    	for (Scores s : us) {
    		if (s.getWeek_nr() == week_nr) 
    			score = s;
    	}
    	
    	return score;
	}
	
	/* 
	 * 
	 * POSTS 
	 * 
	 */
	
	// Post new document in users
    public void create( Users user )
    {
		save(user);
		
    	deleteUnwantedExtraEmptyUser();
    }
    
    /* 
     * 
     * PUTS 
     * 
     */
    
	// Update password for this document in users 
    public void updateUser(Users user, String password) {
    	user.setPassword(password);
    	save(user);
    	
    	deleteUnwantedExtraEmptyUser();
    }
    
    // Update password for document in users by id
    public void updateUser( Users user )
    {  	
    	save(user);
    	
    	deleteUnwantedExtraEmptyUser();
    }
    
    // put ExerciseDiary for user
	public void putExercise_Diary( int week_nr, Exercise_DiaryView view, Users user )
    {  	
		List<Exercise_Diaries> diariesList = new ArrayList<Exercise_Diaries>();
   		List<Exercise_Diaries> existingDiaries = user.getExercise_diaries();

    	if (existingDiaries != null) {
    		for(Exercise_Diaries d : existingDiaries) {
    			diariesList.add(d);
	   		}
    	}
    	  		
   		Exercise_Diaries diary = new Exercise_Diaries();
   		diary.setWeek_nr(week_nr);
   		diary.setBewegen(view.bewegen);
   		diariesList.add(diary);
		
		user.setExercise_diary(diariesList);
		save(user);
		
    	deleteUnwantedExtraEmptyUser();
    }
	
	// put ExerciseDiary for user
	public void putFood_Diary( int week_nr, Food_DiaryView view, Users user )
    {  	
		List<Food_Diaries> diariesList = new ArrayList<Food_Diaries>();
   		List<Food_Diaries> existingDiaries = user.getFood_diaries();

    	if (existingDiaries != null) {
    		for(Food_Diaries d : existingDiaries) {
    			diariesList.add(d);
	   		}
    	}
    	  		
   		Food_Diaries diary = new Food_Diaries();
   		diary.setWeek_nr(week_nr);
   		diary.setOntbijt(view.ontbijt);
   		diary.setLunch(view.lunch);
   		diary.setDiner(view.diner);
   		diary.setSnacks(view.snacks);
   		diariesList.add(diary);
		
		user.setFood_diary(diariesList);
		save(user);
		
    	deleteUnwantedExtraEmptyUser();
    }
	
    // Calculate score for user input from questions
	public void putScore(int score, int week_nr, Users user) {
		List<Scores> scoresList = new ArrayList<Scores>();
		if (user.getScores() != null) 
			scoresList = user.getScores();
		
		Scores scoreObj = new Scores();
		scoreObj.setWeek_nr(week_nr);
		scoreObj.setScore(score);
		scoresList.add(scoreObj);
		
		user.setScores(scoresList);
		save(user);
		
		deleteUnwantedExtraEmptyUser();
	}
    
    /* 
     * 
     * DELETES 
     * 
     */
    
	// Delete one document from users by id
    public void deleteUser( ObjectId id ) {    
    	deleteById(id);
    }
    
    public void deleteUsers( Users user ) {
    	delete(user);
    }
    
    /*
     * 
     * Misc
     * 
     */
    
    // check if a user for this id exists
    public boolean checkIfUser(ObjectId id) {
    	Query<Users> query = createQuery().field( "_id" ).equal( id );
		
		if( count(query) == 0) 
			return false;
		
    	return true;
    }
    
    public List<Users> getFilterList(String gender) {
    	Query<Users> q = createQuery();
    	
    	q.filter("gender",gender);
    	
    	return q.asList();
    }
    
    public boolean checkUsersFields(Users user, UserView view) {
		Query<Users> q = createQuery();
		
		List<Criteria> lc = new ArrayList<Criteria>();
    	
		lc.add(q.criteria("_id").equal(user.getId()));
		
    	if (view.username != null)
			lc.add(q.criteria("username").equal(view.username));
    	
    	if (view.password != null)
			lc.add(q.criteria("password").equal(view.password));
    	
    	if (view.first_name != null)
			lc.add(q.criteria("first_name").equal(view.first_name));
    	
    	if (view.last_name != null)
			lc.add(q.criteria("last_name").equal(view.last_name));
    	
    	q.and(lc.toArray(new Criteria[lc.size()]));
    	    	
    	if (q.asList().size() == 0) 
    		return false;
    	
    	return true;
    }
    
/*    public void addActivitiesToList(List<Users> list, UserView view) {
    	Users user = new Users();
    	if (view.bewegen != 0) {
    		user.setBewegen(view.bewegen);
    	}
    	list.add(user);    	
    }*/

    // 'Prevent' the extra empty document after a post or a put
    public void deleteUnwantedExtraEmptyUser() {
    	Query<Users> q = createQuery().filter("username", null);
    	
    	deleteByQuery( q );
    }
}
