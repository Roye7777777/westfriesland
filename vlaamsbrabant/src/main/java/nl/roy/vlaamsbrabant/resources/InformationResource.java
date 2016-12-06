package nl.roy.vlaamsbrabant.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.inject.Inject;

import io.dropwizard.auth.Auth;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.model.Information;
import nl.roy.vlaamsbrabant.presentation.model.InformationView;
import nl.roy.vlaamsbrabant.presentation.InformationPresenter;
import nl.roy.vlaamsbrabant.service.InformationService;


// A class for processing the API call

@SuppressWarnings("unused")
@Path("/information")
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class InformationResource extends BaseResource {
	private final InformationService informationService;
	
	@Inject
	public InformationResource(InformationService informationService)
	{
		this.informationService = informationService;
	}
	
	@RolesAllowed("ADMIN")
	// Return a list of all information documents
	@GET
	public List<InformationView> getAll()
	{
		List<Information> information = informationService.getAll();
		
		InformationPresenter informationPresenter = new InformationPresenter();

		return informationPresenter.present(information);
	}
	
	@RolesAllowed("ADMIN")
	@GET
	// Return a specific information article
	@Path("/{informationId}")
	public Information get( @PathParam("informationId") String informationId, @Auth Users authenticator)
	{
		Information information = informationService.get(informationId);
		if(information == null)
			return null;
		
		return information;
	}
	
	@GET
	// Return a specific information article on title
	@Path("/title/{title}")
	public List <Information> getByName( @PathParam("title") String title, @Auth Users authenticator)
	{
		List <Information> information = informationService.getByName(title);
		if(information == null)
			return null;
		
		return information;
	}
	
	@RolesAllowed("ADMIN")
	@PUT
	// Change a specific information article
	@Path("/{informationId}")
	public void put( @PathParam("informationId") String informationId, InformationView view, @Auth Users authenticator)
	{
		informationService.put(informationId, view);
	}
	
	
	@RolesAllowed("ADMIN")
	// Delete a specific information article
	@DELETE
	@Path("/{informationId}")
	public void delete( @PathParam("informationId") String informationId, InformationView view, @Auth Users authenticator)
	{
		informationService.delete(informationId, view);
	}
	
	@RolesAllowed("ADMIN")
	// Delete a information on title
	@DELETE
	public void delete(InformationView view)
	{
		informationService.deleteSpecific(view);
	}
	
	@RolesAllowed("ADMIN")
	// Delete all information
	@DELETE
	@Path("/all")
	public void deleteAll(InformationView view)
	{
		informationService.deleteAll(view);
	}
	
	@RolesAllowed("ADMIN")
	// Create new information
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(InformationView view)
	{
		if (view == null)
			return;
		
		informationService.add(view);
	}
}
