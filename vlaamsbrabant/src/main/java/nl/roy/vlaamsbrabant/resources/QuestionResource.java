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

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

import com.google.inject.Inject;

import io.dropwizard.auth.Auth;
//import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.model.Questions;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.presentation.model.QuestionView;
import nl.roy.vlaamsbrabant.presentation.model.AnswerView;
import nl.roy.vlaamsbrabant.presentation.model.UserView;
import nl.roy.vlaamsbrabant.presentation.QuestionPresenter;
import nl.roy.vlaamsbrabant.presentation.UserPresenter;
import nl.roy.vlaamsbrabant.service.QuestionService;

@SuppressWarnings("unused")
@Path("/questions")
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
@RolesAllowed("ADMIN")
/*
==============================================================================
questions
==============================================================================

get /								get all questions (admin only)			v
get /{week}							get questions for this week 			v
get /{week}/{q_nr}					get one question						v
																		
post /								post new question (admin only)			v
																		
put /{id}							edit question (admin only)				v
																		
delete /{id}						delete question (admin only)			v
  */
public class QuestionResource extends BaseResource {
	private final QuestionService questionService;
	
	@Inject
	public QuestionResource(QuestionService questionService)
	{
		this.questionService = questionService;
	}
	
	/* 
	 * 
	 * GET 
	 * 
	 */
	@GET
	// Get all questions
	// /questions
	public List<QuestionView> getAll()
	{
		List<Questions> questions = questionService.getAll();
		
		QuestionPresenter questionPresenter = new QuestionPresenter();

		return questionPresenter.present(questions);
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{week_nr}")
	// Get questions for this week
	public List<QuestionView> getAllForWeek( @PathParam("week_nr") String week )
	{
		if (!isNumericAndNotEmpty(week))
			return null;
		
		List<Questions> questions = questionService.getAllByWeek(Integer.parseInt(week));
		
		QuestionPresenter questionPresenter = new QuestionPresenter();

		return questionPresenter.present(questions);	
	}
	
	@GET
	@RolesAllowed("USER")
	@Path("/{week_nr}/{q_nr}")
	// Get one question for this week
	public Questions getOneForWeek( @PathParam("week_nr") String week, @PathParam("q_nr") String question)
	{
		if (!isNumericAndNotEmpty(week) || !isNumericAndNotEmpty(question)) 
			return null;
		
		return questionService.getOneByWeek(Integer.parseInt(week), Integer.parseInt(question));
	}

	/*
	 * 
	 * POSTS
	 * 
	 */
	
	@POST
	// Post new document in questions
	// TODO: multiple correct answers are not possible now
	public Response create(QuestionView view)
	{
		if (view == null)
			return Response.status(400).build();
		
		questionService.createQuestion(view);
		
		return Response.status(200).build();
	}
	 
	/*
	 * 
	 * PUTS
	 * 
	 */
	
	@PUT
	@Path("/{id}")
	// put question
	public Response putQuestion(@PathParam("id") String id, QuestionView view, @Auth Users authenticator)
	{
		if (view == null)
			return Response.status(400).build();
		
		questionService.putQuestion(id, view, authenticator);
		
		return Response.status(200).build();
	}
	
	/*
	 * 
	 * DELETES
	 * 
	 */
	
	@DELETE
	@Path("/{id}")
	// Delete one document from questions by id
	public Response delete( @PathParam("id") String id, @Auth Users authenticator)
	{
		questionService.delete(id, authenticator);

		return Response.status(200).build();
	}

	private boolean isNumericAndNotEmpty(String str) 
	{
		// check if week is empty and if it only contains numbers
		String pattern = "^[0-9]*$";
		if (str.isEmpty() || !str.matches(pattern))
			return false;
		return true;
	}
	
//    private void isNumeric(String str, String el) {
//    	if (!StringUtils.isNumeric(str)) {
//    		try {
//				throw new Exception(el + " is not a number");
//			} catch (Exception e) {
//				e.getMessage();
//			}
//    	}
//    }	
}