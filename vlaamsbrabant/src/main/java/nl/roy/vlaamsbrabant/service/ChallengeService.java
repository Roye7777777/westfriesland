package nl.roy.vlaamsbrabant.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Challenge;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.persistence.ChallengeDAO;
import nl.roy.vlaamsbrabant.presentation.model.ChallengeView;


public class ChallengeService extends BaseService
{
    private final ChallengeDAO challengeDAO;
    
    @Inject
    public ChallengeService( ChallengeDAO challengeDAO )
    {
        this.challengeDAO = challengeDAO;
    }
    
    /* GETS */
    public List<Challenge> getAll()
    {
        return challengeDAO.getAll();
    }
    
 // Return one document from challenges by title and week_nr
    public Challenge getByCredentials( String title, String week_nr ) {
    	Challenge challenge = new Challenge();
    	
    	challenge.setTitle(title);
    	challenge.setWeek_nr(week_nr);
    	
    	return challengeDAO.getByCredentials(challenge);
    }
    // Return one document from challenges by challengeId (id)
    public Challenge getById( Users authenticator, String id )
    {
                
		return challengeDAO.get( id );
    }

    // Return one document from challenges by a given title
    public List<Challenge> getByTitle( Users authenticator, String title )
    {
		return challengeDAO.getByTitle(  title );
    }
    
    // Return one document from challenges by a given week_nr
    public List<Challenge> getByWeek_nr( Users authenticator, String week_nr )
    {
		return challengeDAO.getByWeek_nr(  week_nr );
    }
        
    /* POSTS */
	// Post new document in challenges
    public void add( ChallengeView view ) {
    	Challenge challenge = new Challenge();
    	
    	challenge.setTitle(view.title);
    	challenge.setWeek_nr(view.week_nr);
    	
    	challengeDAO.create(challenge);
    }
    
    /* PUTS */  
    public void put(Users authenticator, String id, ChallengeView view ) {
    	Challenge challenge = new Challenge();
    	
    	challenge.setTitle(view.title);
    	challenge.setWeek_nr(view.week_nr);
    	
    	challengeDAO.updateChallengeById(id, challenge, challengeDAO);
    }
    
    public void putAll(ChallengeView view) {
    	if (view.week_nr == null)
    		return;
    	
    	List<Challenge> challengeList = challengeDAO.createQuery().filter("title",view.title).asList();
    	
    	challengeList.forEach(challenge -> challengeDAO.updateChallenge(challenge, view.week_nr));
    }
    
    /* DELETES */
	// Delete one document from challenges by id
    public void delete( String id ) {
    	ObjectId objectId = new ObjectId(id);
    	Query<Challenge> query = challengeDAO.createQuery().field( "_id" ).equal( objectId );
		
		if( challengeDAO.count(query) == 0) 
			return;
		
    	challengeDAO.deleteChallenge( objectId );
    }
    
 // Delete all documents by matching input challengeview
    public void delete(ChallengeView view)
    {
    	Challenge challenge = new Challenge();
    	
    	// dit in een lijst zetten, doorloopen en iedere key met value in een q.and zetten somehow?
    	challenge.setTitle(view.title);
    	challenge.setWeek_nr(view.week_nr);
    	
    	// If there's only a title, delete by week_nr
    	if (challenge.getWeek_nr() == null) {
    		challengeDAO.deleteAllByTitle(challenge);
    	}
    	// If there's only a week_nr, delete by title
    	else if (challenge.getTitle() == null) {
    		challengeDAO.deleteAllByWeek_nr(challenge);
    	}
    	// If there's a title and week_nr, delete explicitly by both 
    	else {    	
    		challengeDAO.deleteAll(challenge);
    	}
    }
}
    
    

    

	

