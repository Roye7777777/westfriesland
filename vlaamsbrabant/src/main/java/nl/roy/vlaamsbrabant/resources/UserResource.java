package nl.roy.vlaamsbrabant.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mongodb.morphia.annotations.Entity;

import com.google.inject.Inject;

import io.dropwizard.auth.Auth;
import nl.roy.vlaamsbrabant.model.Exercise_Diaries;
import nl.roy.vlaamsbrabant.model.Food_Diaries;
import nl.roy.vlaamsbrabant.model.Scores;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.presentation.model.Exercise_DiaryView;
import nl.roy.vlaamsbrabant.presentation.model.Food_DiaryView;
import nl.roy.vlaamsbrabant.presentation.model.AnswerView;
import nl.roy.vlaamsbrabant.presentation.model.UserView;
import nl.roy.vlaamsbrabant.presentation.UserPresenter;
import nl.roy.vlaamsbrabant.service.UserService;

@SuppressWarnings("unused")
@Path("/users")
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
@RolesAllowed("ADMIN")
/*
==============================================================================
users
==============================================================================

/users/.. 	=

get /								all users (admin)						v
get /{id}							specific user							v		
get /{id}/exercise_diaries			all user exercise diary (or range)		v
get /{id}/food_diaries				all user food diary (or range)			v
get /{id}/exercise_diary/{week}		one user exercise diary by week			v
get /{id}/food_diary/{week}			one user food diary by week				v	
get /{id}/scores					get scores on questions from user 		v
get /{id}/score/{week_nr}			get score from week from user			v												
																			
post /								add a user (admin only)					v
																			
put /								edit all users (admin only)				v
put /{id}							edit one user (user/admin)				v
put /{id}/roles						edit one's roles 						v		
put /{id}/food_diaries/{week}		add/edit food diary	(user/admin)		v
put /{id}/exercise_diaries/{week}	add/edit exorcist diary	(user/admin)	v
put /{id}/score/{week}				post quiz answers, calculate score		v
																			
delete /							delete all users (admin only)	v
delete /{id}						delete a user (admin only)				v
 */
public class UserResource extends BaseResource {
	private final UserService userService;
	
	@Inject
	public UserResource(UserService userService)
	{
		this.userService = userService;
	}
	
	/* 
	 * 
	 * GETS
	 * 
	 */
	
	@GET
	// Return a list of all documents from users
	// /users
	public List<UserView> getAll()
	{
		List<Users> users = userService.getAll();
		UserPresenter userPresenter = new UserPresenter();

		return userPresenter.present(users);
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{id}")
	// Return one document from users by userId
	public Users getById( @PathParam("id") String id, @Auth Users authenticator )
	{
		return userService.getById(authenticator, id);
	}

	@GET
	@RolesAllowed("USER")
	@Path("/{id}/exercise_diaries")
	// Return one document from users by userId
	public List<Exercise_Diaries> getExerciseDiaries( @PathParam("id") String id, @Auth Users authenticator)
	{		
		return userService.getExerciseDiaries(authenticator, id);
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{id}/food_diaries")
	// Return one document from users by userId
	public List<Food_Diaries> getFoodDiaries( @PathParam("id") String id, @Auth Users authenticator)
	{		
		return userService.getFoodDiaries(authenticator, id);
	}
		
	@GET
	@RolesAllowed("USER")
	@Path("/{id}/exercise_diary/{week_nr}")
	// Return one document from users by userId
	public Exercise_Diaries getExerciseDiary( @PathParam("id") String id, @PathParam("week_nr") String week, @Auth Users authenticator)
	{
		if (!isNumericAndNotEmpty(week))
			return null;
		
		return userService.getExerciseDiary(authenticator, id, Integer.parseInt(week));
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{id}/food_diary/{week_nr}")
	// Return one document from users by userId 
	public Food_Diaries getFoodDiary( @PathParam("id") String id, @PathParam("week_nr") String week, @Auth Users authenticator)
	{
		if (!isNumericAndNotEmpty(week))
			return null;
		
		return userService.getFoodDiary(authenticator, id, Integer.parseInt(week));
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{id}/scores")
	// Return scores from users by userId
	public List<Scores> getScores( @PathParam("id") String id, @Auth Users authenticator) 
	{	
		return userService.getScores(authenticator, id);
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{id}/score/{week_nr}")
	// Return score from users by userId and week
	public Scores getScore( @PathParam("id") String id, @PathParam("week_nr") String week, @Auth Users authenticator) 
	{
		if (!isNumericAndNotEmpty(week))
			return null;
		
		return userService.getScore(authenticator, id, Integer.parseInt(week));
	}
		
	/* 
	 * 
	 * POSTS 
	 * 
	 */
	
	@POST
	// Post new document in users
	public Response create(UserView view)
	{
		if (view == null)
			return Response.status(400).build();
		
		userService.createUser(view);
		
		return Response.status(200).build();
	}
	
	/* 
	 * 
	 * PUTS 
	 * 
	 */
	
	@PUT
	// Update password for all found documents in users, by username
	public Response putAll(UserView view) {
		if (view == null)
			return Response.status(400).build();
		
		userService.putAll(view);
		
		return Response.status(200).build();
	}
	
	@PUT
	@RolesAllowed("USER")
	@Path("/{id}")
	// Update password one or more document(s) by userId 
	public Response put( @PathParam("id") String id, UserView view, @Auth Users authenticator)
	{
		if (view == null)
			return Response.status(400).build();
		
		userService.putOne(authenticator, id, view);
		
		return Response.status(200).build();
	}
		
	@PUT
	@Path("/{id}/roles")
	// Update roles for user
	public Response putRoles( @PathParam("id") String id, UserView view)
	{
		if (view == null)
			return Response.status(400).build();
		
		userService.putRolesForOne(id, view);
		
		return Response.status(200).build();
	}
	
	@PUT
	@RolesAllowed("USER")
	@Path("/{id}/excercise_diaries/{week_nr}")
	// Update exercise diary
	public Response putExercise_diary( @PathParam("id") String id, @PathParam("week_nr") String week, Exercise_DiaryView view, @Auth Users authenticator )
	{
		if (!isNumericAndNotEmpty(week) || view == null)
			return Response.status(400).build();
		
		userService.putExercise_diary(id, Integer.parseInt(week), view, authenticator);
		
		return Response.status(200).build();
	}
	
	@PUT
	@RolesAllowed("USER")
	@Path("/{id}/food_diaries/{week_nr}")
	// Update food diary
	public Response putFood_diary( @PathParam("id") String id, @PathParam("week_nr") String week, Food_DiaryView view, @Auth Users authenticator )
	{
		if (!isNumericAndNotEmpty(week) || view == null)
			return Response.status(400).build();
		
		userService.putFood_diary(id, Integer.parseInt(week), view, authenticator);
		
		return Response.status(200).build();
	}

	@PUT
	@RolesAllowed("USER")
	@Path("/{id}/score/{week_nr}")
	// Calculate the score of a quiz the user has sent
	public Response sendScore(@PathParam("id") String id, @PathParam("week_nr") String week, AnswerView view, @Auth Users authenticator) 
	{
		if (!isNumericAndNotEmpty(week) || view == null)
			return Response.status(400).build();
		
		userService.calculateScore(id, Integer.parseInt(week), view, authenticator);

		return Response.status(200).build();
	}
	
	/*
	 * 
	 * DELETES
	 * 
	 */
	
	@DELETE
	@Path("/{id}")
	// Delete one document from users by id
	public Response deleteUser( @PathParam("id") String id)
	{
		userService.deleteUser(id);
		return Response.status(200).build();
	}

	@DELETE
	// Delete all documents by matching input userview
	public Response deleteUsers(UserView view)
	{
    	if (view == null)
    		return Response.status(400).build();
    	
		userService.deleteUsers(view);

		return Response.status(200).build();
	}
	
	private boolean isNumericAndNotEmpty(String week) 
	{
		// check if week is empty and if it only contains numbers
		String pattern = "^[0-9]*$";
		if (week.isEmpty() || !week.matches(pattern))
			return false;
		return true;
	}
}
