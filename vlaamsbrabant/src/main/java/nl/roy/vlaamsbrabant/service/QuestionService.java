package nl.roy.vlaamsbrabant.service;

import java.util.List;

import org.bson.types.ObjectId;

//import org.bson.types.ObjectId;
//import org.mongodb.morphia.Datastore;

import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Questions;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.persistence.QuestionDAO;
import nl.roy.vlaamsbrabant.presentation.model.QuestionView;

public class QuestionService extends BaseService
{
    private final QuestionDAO questionDAO;
    
    @Inject
    public QuestionService( QuestionDAO questionDAO )
    {
        this.questionDAO = questionDAO;
    }
    
    /* 
     * 
     * GETS 
     * 
     */
    public List<Questions> getAll()
    {
        return questionDAO.getAll();
    }
    
    public List<Questions> getAllByWeek(int week_nr)
    {
		return questionDAO.getAllByWeek(week_nr);
    }
    
    public Questions getOneByWeek(int week_nr, int question_nr) 
    {    	    	
    	return questionDAO.getOneByWeek(week_nr, question_nr);
    }
    
    /*
     * 
     * POSTS
     * 
     */
    
	// Post new document in users
    public void createQuestion( QuestionView view ) 
    {		    	    	
    	questionDAO.alter(setPost(view));
    }
    
    /*
     * 
     * PUTS
     * 
     */
    
    // Put question
    public void putQuestion( String id, QuestionView view, Users authenticator )
    {    	
    	checkIfOwnId(authenticator);
    	
    	questionDAO.alter(setPut(id, view));
    }
    
    /*
     * 
     * DELETES
     * 
     */
    
	// Delete one document from questions by id
    public void delete( String id, Users authenticator ) {
    	checkIfOwnId(authenticator);
    	
    	ObjectId objectId = new ObjectId();
    	if ( !questionDAO.checkIfQuestion(objectId) )
    		return;

    	questionDAO.deleteQuestion( objectId );
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
    
    private Questions setPost (QuestionView view) {
    	Questions question = new Questions();
    	
    	question.setWeek_nr(view.week_nr);
    	question.setQuestion_nr(view.question_nr);
    	question.setQuestion(view.question);
    	question.setA(view.a);
    	question.setB(view.b);
    	
    	if (view.c != null)
    		question.setC(view.c);
    	
    	if (view.d != null)
        	question.setD(view.d);
    	
    	if (view.e != null)
        	question.setE(view.e);
    	
    	if (view.f != null)
        	question.setF(view.f);
    	
    	question.setCorrect(view.correct);
    	
    	return question;
    }
    
    private Questions setPut(String id, QuestionView view) {
    	Questions question = questionDAO.get(id);
    	
    	if (view.week_nr != 0)
    		question.setWeek_nr(view.week_nr);
    	
    	if (view.question_nr != 0)
    		question.setQuestion_nr(view.question_nr);
    	
    	if (view.question != null)
    		question.setQuestion(view.question);
    	
    	if (view.a != null)
    		question.setA(view.a);
    	
    	if (view.b != null)
    		question.setB(view.b);
    	
    	if (view.c != null)
    		question.setC(view.c);
    	
    	if (view.d != null)
        	question.setD(view.d);
    	
    	if (view.e != null)
        	question.setE(view.e);
    	
    	if (view.f != null)
        	question.setF(view.f);
    	
    	if (view.correct != null)
    		question.setCorrect(view.correct);
    	
    	return question;
    }
}