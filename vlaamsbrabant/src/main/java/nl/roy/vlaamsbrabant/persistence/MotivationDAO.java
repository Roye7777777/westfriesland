package nl.roy.vlaamsbrabant.persistence;

import java.util.List;

// A class for dealing with persistence and database retrieval

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import com.google.inject.Inject;
import nl.roy.vlaamsbrabant.model.Motivation;

public class MotivationDAO extends BaseDAO< Motivation >
{
    @Inject
    public MotivationDAO(Datastore ds)
    {
    	super (Motivation.class, ds);
    }
    
    public void create( Motivation motivation )
    {
    	this.save(motivation);
    }
    
       
    public void updateMotivation(String id, Motivation motivation)
    {
    	this.update(
	    	this.createQuery().field("_id").equal(new ObjectId(id)),
	    	this.createUpdateOperations().set("title", motivation.getName()
	    	
	    ));
    }
   
	public List <Motivation> getByName(String title) 
	{
		Query<Motivation> query = createQuery().field( "title" ).equal( title);
		return find( query ).asList();
	}
	
    public void updateMotivationAll(Motivation motivation, String title) {
    	motivation.setName(title);
    	save(motivation);
    }
	
	 public void deleteSpecific(Motivation motivation) {
	    	Query<Motivation> q = createQuery();
	   
	    	
	    	q.field("title").equal(motivation.getName());
			
	    	
	    	deleteByQuery(q);
	    }
	 
	 public void deleteMotivationAll(Motivation motivation) {
	    	delete(motivation);
	    }
}
