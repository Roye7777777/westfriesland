package nl.roy.vlaamsbrabant.service;

import java.util.List;

import org.bson.types.ObjectId;
import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Motivation;
import nl.roy.vlaamsbrabant.persistence.MotivationDAO;
import nl.roy.vlaamsbrabant.presentation.model.MotivationView;

// A class for handling the business logic

public class MotivationService extends BaseService
{
    private final MotivationDAO motivationDAO;
    
    @Inject
    public MotivationService( MotivationDAO motivationDAO )
    {
        this.motivationDAO = motivationDAO;
    }
    
    public Motivation get(String id )
    {
        if (id == null)
        	return null;
        
        	return motivationDAO.get( id );
    }
    
    public List <Motivation> getByName(String title )
    {
		return motivationDAO.getByName( title );
    }
    
    public void add( MotivationView view ) {
    	Motivation motivation = new Motivation();
    	
    	motivation.setName(view.title);
    	motivation.setText(view.text);
    	
    	motivationDAO.create(motivation);
    }
    
    public void put(String id, MotivationView view ) {
    	Motivation motivation = new Motivation();
    	
    	motivation.setName(view.title);
    	motivation.setText(view.text);
    	
    	motivationDAO.updateMotivation(id, motivation);
    }
    
    public void putAll(MotivationView view) {
    	if (view.title == null)
    		return;
    	
    	motivationDAO.getAll().forEach(motivation -> motivationDAO.updateMotivationAll(motivation, view.title));
    }
    
    
    public void delete(String id, MotivationView view ) {
    	
    	motivationDAO.deleteById(new ObjectId(id));
    }
    
    public void deleteAll(MotivationView view)
    {
    	motivationDAO.getAll().forEach(motivation -> motivationDAO.deleteMotivationAll(motivation));
    	
    }
    
    public void deleteSpecific(MotivationView view)
    {
    	Motivation motivation = new Motivation();
    	motivation.setName(view.title);

    	motivationDAO.deleteSpecific(motivation);
    	
    }

    public List<Motivation> getAll()
    {
        return motivationDAO.getAll();
    }
}
