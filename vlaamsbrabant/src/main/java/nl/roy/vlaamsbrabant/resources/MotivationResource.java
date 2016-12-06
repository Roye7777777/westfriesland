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
import nl.roy.vlaamsbrabant.model.Motivation;
import nl.roy.vlaamsbrabant.presentation.model.MotivationView;
import nl.roy.vlaamsbrabant.presentation.MotivationPresenter;
import nl.roy.vlaamsbrabant.service.MotivationService;


// A class for processing the API call

@SuppressWarnings("unused")
@Path("/motivation")
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class MotivationResource extends BaseResource {
	private final MotivationService motivationService;
	
	@Inject
	public MotivationResource(MotivationService motivationService)
	{
		this.motivationService = motivationService;
	}
	
	@RolesAllowed("ADMIN")
	@GET
	public List<MotivationView> getAll()
	{
		List<Motivation> motivation = motivationService.getAll();
		
		MotivationPresenter motivationPresenter = new MotivationPresenter();

		return motivationPresenter.present(motivation);
	}
	
	@RolesAllowed("ADMIN")
	@GET
	@Path("/{motivationId}")
	public Motivation get( @PathParam("motivationId") String motivationId, @Auth Users authenticator)
	{
		Motivation motivation = motivationService.get(motivationId);
		if(motivation == null)
			return null;
		
		return motivation;
	}
	
	@GET
	@Path("/title/{title}")
	public List <Motivation> getByName( @PathParam("title") String title, @Auth Users authenticator)
	{
		List <Motivation> motivation = motivationService.getByName(title);
		if(motivation == null)
			return null;
		
		return motivation;
	}
	
	@RolesAllowed("ADMIN")
	@PUT
	@Path("/{motivationId}")
	public void put( @PathParam("motivationId") String motivationId, MotivationView view, @Auth Users authenticator)
	{
		motivationService.put(motivationId, view);
	}
	
	@PUT
	@RolesAllowed("ADMIN")
	public void putAll(MotivationView view) {
		motivationService.putAll(view);
	}
	
	@RolesAllowed("ADMIN")
	@DELETE
	@Path("/{motivationId}")
	public void delete( @PathParam("motivationId") String motivationId, MotivationView view, @Auth Users authenticator)
	{
		motivationService.delete(motivationId, view);
	}
	
	@RolesAllowed("ADMIN")
	@DELETE
	public void delete(MotivationView view)
	{
		motivationService.deleteSpecific(view);
	}
	
	@RolesAllowed("ADMIN")
	@DELETE
	@Path("/all")
	public void deleteAll(MotivationView view)
	{
		motivationService.deleteAll(view);
	}
	
	@RolesAllowed("ADMIN")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(MotivationView view)
	{
		if (view == null)
			return;
		
		motivationService.add(view);
	}
}
