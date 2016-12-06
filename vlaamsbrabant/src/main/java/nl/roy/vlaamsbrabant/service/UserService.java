package nl.roy.vlaamsbrabant.service;

import java.util.ArrayList;
import java.util.List;

//import org.bson.types.ObjectId;
//import org.mongodb.morphia.Datastore;

import com.google.inject.Inject;

import org.bson.types.ObjectId;

import nl.roy.vlaamsbrabant.model.Exercise_Diaries;
import nl.roy.vlaamsbrabant.model.Food_Diaries;
import nl.roy.vlaamsbrabant.model.Scores;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.persistence.QuestionDAO;
import nl.roy.vlaamsbrabant.persistence.UserDAO;
import nl.roy.vlaamsbrabant.presentation.model.Exercise_DiaryView;
import nl.roy.vlaamsbrabant.presentation.model.Food_DiaryView;
import nl.roy.vlaamsbrabant.presentation.model.AnswerView;
import nl.roy.vlaamsbrabant.presentation.model.UserView;

public class UserService extends BaseService
{
	private final QuestionDAO questionDAO;
    private final UserDAO userDAO;
    
    @Inject
    public UserService( QuestionDAO questionDAO, UserDAO userDAO )
    {
    	this.questionDAO = questionDAO;
        this.userDAO = userDAO;
    }
    
    /* 
     * 
     * GETS 
     * 
     */
    
    // Get all users
    // /users
    public List<Users> getAll()
    {
        return userDAO.getAll();
    }
    
    // Return one document from users by userId (id)
    public Users getById( Users authenticator, String id )
    {
		checkIfOwnId(authenticator);
		
		return userDAO.get( id );
    }
    
	// Return users' exercise diaries
    public List<Exercise_Diaries> getExerciseDiaries( Users authenticator, String id ) {
		checkIfOwnId(authenticator);
		
    	return userDAO.getExerciseDiaries(id);
    }
    
	// Return users' food diaries
    public List<Food_Diaries> getFoodDiaries( Users authenticator, String id ) {
		checkIfOwnId(authenticator);
		
    	return userDAO.getFoodDiaries(id);
    }
        
	// Return users' exercise diary per week
    public Exercise_Diaries getExerciseDiary( Users authenticator, String id, int week_nr ) {
		checkIfOwnId(authenticator);
		
    	return userDAO.getExerciseDiary(id, week_nr);
    }
    
	// Return users' food diary per week
    public Food_Diaries getFoodDiary( Users authenticator, String id, int week_nr ) {
		checkIfOwnId(authenticator);
		
    	return userDAO.getFoodDiary(id, week_nr);
    }
    
    // Get scores from users by userId
    public List<Scores> getScores(Users authenticator, String id) {
		checkIfOwnId(authenticator);
		
		return userDAO.getScores(id);
	}

    // Get score from users by userId and week
    public Scores getScore(Users authenticator, String id, int week_nr) {
		checkIfOwnId(authenticator);
		
		return userDAO.getScore(id, week_nr);
	}
    
    /* 
     * 
     * POSTS 
     * 
     */
    
	// Post new document in users
    public void createUser( UserView view ) {
    	userDAO.create(setNewUser(view));
    }
    
    /* 
     * 
     * PUTS 
     * 
     */  
    
	// Update password for all found documents in users
    public void putAll(UserView view) {
    	if (view.password == null)
    		return;
    	
    	userDAO.getAll().forEach(user -> userDAO.updateUser(user, view.password));
    }
    
    // Update password for one document, by userId. N.B.: roles and diaries can't be changed here. - user
    public void putOne( Users authenticator, String id, UserView view ) {
		checkIfOwnId(authenticator);
		
    	userDAO.updateUser(setUser(userDAO.get(new ObjectId(id)), view));
    }
        
    // Update roles for user
    public void putRolesForOne(String id, UserView view) {	
    	Users user = userDAO.get(id);
    	if (view.roles != null)
    		user.setRoles(view.roles);
    	
    	userDAO.updateUser(user);
    }
    
    // Update user exercise diary - user
    // TODO: cannot edit currently existing diary yet.
    public void putExercise_diary(String id, int week_nr, Exercise_DiaryView view, Users authenticator) {
		checkIfOwnId(authenticator);
    	
    	Users user = userDAO.get(id);
    	
    	userDAO.putExercise_Diary(week_nr, view, user);
    }
        
    // Update user exercise diary - user
    // TODO: cannot edit currently existing diary yet.
    public void putFood_diary(String id, int week_nr, Food_DiaryView view, Users authenticator) {
		checkIfOwnId(authenticator);
    	
    	Users user = userDAO.get(id);
    	
    	userDAO.putFood_Diary(week_nr, view, user);
    }
        
    // Calculate score for user input from questions
    // TODO: cannot edit currently existing diary yet.
	public void calculateScore(String id, int week_nr, AnswerView view, Users authenticator) {
		checkIfOwnId(authenticator);
		
		Users user = userDAO.get(id);
		int score = questionDAO.calculateScore(week_nr, view);
		
		userDAO.putScore(score, week_nr, user);
	}
	
	/* 
     * 
     * DELETES 
     * 
     */
    
	// Delete one document from users by id
    public void deleteUser( String id ) {    	
    	ObjectId objectId = new ObjectId(id);
    	if ( !userDAO.checkIfUser(objectId) )
    		return;

    	userDAO.deleteUser( objectId );
    }
    
	// Delete all documents by matching input userview
    public void deleteUsers(UserView view)
    {    	
    	List<Users> users = userDAO.getAll();
    	List<Users> usersToDelete = new ArrayList<Users>();
    	
    	for (Users u : users) {
	    	if (userDAO.checkUsersFields(u, view))
	    		usersToDelete.add(u);
    	}
    	
    	usersToDelete.forEach(user -> userDAO.deleteUsers(user));
    }
    
    /*
     * 
     * Misc
     * 
     */
    
    private void checkIfOwnId(Users authenticator) {
    	if (!authenticator.hasRole("ADMIN")) 
    		return;
	}
    
    private Users setNewUser(UserView view) {
    	Users user = new Users();
    	return setUser(user, view);
    }
    
    private Users setUser(Users user, UserView view) {
    	if (view.username != null)
    		user.setName(view.username);
    	
    	if (view.password != null)
    		user.setPassword(view.password);
    	
    	if (view.first_name != null)
    		user.setFirst_name(view.first_name);
    	
    	if (view.last_name != null)
    		user.setLast_name(view.last_name);
    	
    	if (view.age != 0) 
    		user.setAge(view.age);
    	
    	if (view.gender != null)
    		user.setGender(view.gender);
    	
    	if (view.weight != 0)
    		user.setWeight(view.weight);
    	
    	if (view.length != 0)
    		user.setLength(view.length);
    	
    	if (view.roles != null) 
    		user.setRoles(view.roles);
    	
    	return user;
    }
}
