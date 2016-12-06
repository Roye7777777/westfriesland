package nl.roy.vlaamsbrabant.persistence;

import java.util.List;

// A class for dealing with persistence and database retrieval

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import com.google.inject.Inject;
import nl.roy.vlaamsbrabant.model.Information;

public class InformationDAO extends BaseDAO< Information >
{
    @Inject
    public InformationDAO(Datastore ds)
    {
    	super (Information.class, ds);
    }
    
    public void create( Information information )
    {
    	this.save(information);
    }
    
       
    public void updateInformation(String id, Information information)
    {
    	this.update(
	    	this.createQuery().field("_id").equal(new ObjectId(id)),
	    	this.createUpdateOperations().set("title", information.getName()
	    	
	    ));
    }
   
	public List <Information> getByName(String title) 
	{
		Query<Information> query = createQuery().field( "title" ).equal( title);
		return find( query ).asList();
	}
	
	
	 public void deleteSpecific(Information information) {
	    	Query<Information> q = createQuery();
	   
	    	
	    	q.field("title").equal(information.getName());
			
	    	
	    	deleteByQuery(q);
	    }
	 
	 public void deleteInformationAll(Information information) {
	    	delete(information);
	    }
}
