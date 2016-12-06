package nl.roy.vlaamsbrabant.service;

import java.util.List;

import org.bson.types.ObjectId;
import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Information;
import nl.roy.vlaamsbrabant.persistence.InformationDAO;
import nl.roy.vlaamsbrabant.presentation.model.InformationView;

// A class for handling the business logic

public class InformationService extends BaseService
{
    private final InformationDAO informationDAO;
    
    @Inject
    public InformationService( InformationDAO informationDAO )
    {
        this.informationDAO = informationDAO;
    }
    
    public Information get(String id )
    {
        if (id == null)
        	return null;
        
        	return informationDAO.get( id );
    }
    
    public List <Information> getByName(String title )
    {
		return informationDAO.getByName( title );
    }
    
    public void add( InformationView view ) {
    	Information information = new Information();
    	
    	information.setName(view.title);
    	information.setText(view.text);
    	
    	informationDAO.create(information);
    }
    
    public void put(String id, InformationView view ) {
    	Information information = new Information();
    	
    	information.setName(view.title);
    	information.setText(view.text);
    	
    	informationDAO.updateInformation(id, information);
    }
    
      
    public void delete(String id, InformationView view ) {
    	
    	informationDAO.deleteById(new ObjectId(id));
    }
    
    public void deleteAll(InformationView view)
    {
    	informationDAO.getAll().forEach(information -> informationDAO.deleteInformationAll(information));
    	
    }
    
    public void deleteSpecific(InformationView view)
    {
    	Information information = new Information();
    	information.setName(view.title);

    	informationDAO.deleteSpecific(information);
    	
    }

    public List<Information> getAll()
    {
        return informationDAO.getAll();
    }
}
