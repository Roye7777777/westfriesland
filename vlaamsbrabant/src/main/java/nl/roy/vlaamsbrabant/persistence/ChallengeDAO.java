package nl.roy.vlaamsbrabant.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Challenge;

public class ChallengeDAO extends BaseDAO< Challenge >
{
    @Inject
    public ChallengeDAO(Datastore ds)
    {
    	super (Challenge.class, ds);
    }
    
    /* GETS */
    // Return one or more document from challenges by title
    public List <Challenge> getByTitle(String title) 
	{
		Query<Challenge> query = createQuery().field( "title" ).equal( title);
		
		return find( query ).asList();
	}
    
 // Return one document from challenges by week_nr
    public List<Challenge> getByWeek_nr(String week_nr) 
	{
		Query<Challenge> query = createQuery().field( "week_nr" ).equal( week_nr);
		
		return find( query ).asList();
	}
    
    /* GETS */
    // Return one document from challenges by title and week_nr
    public Challenge getByCredentials(Challenge challenge) {
    	Query<Challenge> q = createQuery();
    	q.and(
    		q.criteria("title").equal(challenge.getTitle()),
    		q.criteria("week_nr").equal(challenge.getWeek_nr())
		);
    	
    	return findOne(q);    
    }
    
    /* POSTS */
	// Post new document in challenges
    public void create( Challenge challenge )
    {
    	if (challenge != null) 
    	this.save(challenge);
    	deleteUnwantedExtraEmptyChallenge();
    }
    
       
    /* PUTS */
	// Update title for this document in challenges
	public void updateChallenge(Challenge challenge, String title) {
		this.update(
				this.createQuery().filter("_id", challenge.getId()),
				this.createUpdateOperations().set("title", title)
	    	);
		deleteUnwantedExtraEmptyChallenge();
		
	} 
	
	/* PUTS */
	// Update week_nr for document in challenges by id
	public void updateChallengeById(String id, Challenge challenge,  ChallengeDAO challengeDAO) {
		this.update(
		    	challengeDAO.createQuery().field("_id").equal(new ObjectId(id)),
		    	challengeDAO.createUpdateOperations().set("week_nr", challenge.getWeek_nr())
	  	);
		deleteUnwantedExtraEmptyChallenge();
	} 
	
	/* DELETES */
	// Delete one document from challenges by id
    public void deleteChallenge( ObjectId id ) {    
    	this.deleteById(id);
    }
    
    // Delete all documents in challenges with a given title
    public void deleteAllByTitle(Challenge challenge) 
    {
    	this.deleteByQuery(this.createQuery().filter("title", challenge.getTitle()));
    }
    
    // Delete all documents in challenges with a given week_nr
    public void deleteAllByWeek_nr(Challenge challenge) 
    {
    	this.deleteByQuery(this.createQuery().filter("week_nr", challenge.getWeek_nr()));
    }

    // Delete all documents in challenges with a given title and a given week_nr
    public void deleteAll(Challenge challenge) {
    	Query<Challenge> q = createQuery();
    
    	q.and(
    		q.criteria("title").equal(challenge.getTitle()),
    		q.criteria("week_nr").equal(challenge.getWeek_nr())
		);

    	this.deleteByQuery(q);
    }
    
    // 'Prevent' the extra empty document after a post or a put
     public void deleteUnwantedExtraEmptyChallenge() {
    	this.deleteByQuery(createQuery().filter("title", null));
    }
	
	}
