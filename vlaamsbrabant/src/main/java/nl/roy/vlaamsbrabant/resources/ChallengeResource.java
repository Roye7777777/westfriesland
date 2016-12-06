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
import nl.roy.vlaamsbrabant.model.Challenge;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.presentation.model.ChallengeView;
import nl.roy.vlaamsbrabant.presentation.ChallengePresenter;
import nl.roy.vlaamsbrabant.service.ChallengeService;


@SuppressWarnings("unused")
@Path("/challenges")
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
@RolesAllowed("ADMIN")
public class ChallengeResource extends BaseResource {
	private final ChallengeService challengeService;
	
	@Inject
	public ChallengeResource(ChallengeService challengeService)
	{
		this.challengeService = challengeService;
	}
	
	@GET
	// Return a list of all documents from challenges
	public List<ChallengeView> getAll()
	{
		List<Challenge> challenges = challengeService.getAll();
		
		ChallengePresenter challengePresenter = new ChallengePresenter();

		return challengePresenter.present(challenges);
	}
	
	@GET
	@Path("/title/{title}/week_nr/{week_nr}")
	// Return one document from users by title and week_nr
	public Challenge getByCredentials( @PathParam("title") String title, @PathParam("week_nr") String week_nr) {
		Challenge challenge = challengeService.getByCredentials(title, week_nr);
		
		if (challenge == null) 
			return null;
		
		return challenge;
	}
	
	@GET
	@Path("/id/{challengeId}")
	public Challenge getById( @PathParam("challengeId") String challengeId, @Auth Users authenticator)
	{
		Challenge challenge = challengeService.getById( authenticator, challengeId);
		if(challenge == null)
			return null;
		
		return challenge;
	}
	
	@GET
	@Path("/title/{title}")
	public List<Challenge> getByTitle( @PathParam("title") String title, @Auth Users authenticator)
	{
		List<Challenge> challenge = challengeService.getByTitle(authenticator, title);
		if(challenge == null)
			return null;
		
		return challenge;
	}
	
	@GET
	@Path("/week_nr/{week_nr}")
	public List<Challenge> getByWeek_nr( @PathParam("week_nr") String week_nr, @Auth Users authenticator)
	{
		List<Challenge> challenge = challengeService.getByWeek_nr( authenticator, week_nr);
		if(challenge == null)
			return null;
		
		return challenge;
	}
	
	/* PUTS */	
	@PUT
	@Path("/id/{challengeId}")
	public void put( @PathParam("challengeId") String challengeId, ChallengeView view, @Auth Users authenticator)
	{
		challengeService.put(authenticator, challengeId, view);
	}
	
	@RolesAllowed("ADMIN")
	@PUT
	// Update week_nr for all found documents in challenges
	public void putAll(ChallengeView view) {
		challengeService.putAll(view);
	}
	
	/* POSTS */
	@POST
	public void create(ChallengeView view)
	{
		if (view == null)
			return;
		
		challengeService.add(view);
	}
	
	/* DELETES */
	@DELETE
	@Path("/id/{challengeId}")
	// Delete one document from challenges by id
	public void delete( @PathParam("challengeId") String challengeId)
	{
		challengeService.delete(challengeId);
	}

	@RolesAllowed("ADMIN")
	@DELETE
	// Delete all documents by matching input userview
	public void delete(ChallengeView view)
	{
		challengeService.delete(view);
	}
	
}
