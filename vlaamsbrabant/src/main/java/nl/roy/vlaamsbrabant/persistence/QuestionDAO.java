package nl.roy.vlaamsbrabant.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.google.inject.Inject;

import nl.roy.vlaamsbrabant.model.Questions;
import nl.roy.vlaamsbrabant.presentation.model.AnswerView;

public class QuestionDAO extends BaseDAO< Questions >
{
    @Inject
    public QuestionDAO(Datastore ds)
    {
    	super (Questions.class, ds);
    }
    
    /*
     * 
     * GETS
     * 
     */
	public List<Questions> getAllByWeek(int week) {
		Query<Questions> q = createQuery().field( "week_nr" ).equal( week );
		
		return find(q).asList();
	}
    
	public Questions getOneByWeek(int week_nr, int question_nr) {
		Query<Questions> q = createQuery();
    	q.and(
    		q.criteria("week_nr").equal(week_nr),
    		q.criteria("question_nr").equal(question_nr)
		);
    	
		return findOne( q );
	}
    
    /*
     * 
     * POSTS/PUTS
     * 
     */
    
	// Post new document in users
    public void alter( Questions question )
    {
		save(question);
		
    	deleteUnwantedExtraEmptyQuestion();
    }
    
    /*
     * 
     * DELETES
     * 
     */
    
	// Delete one document from questions by id
    public void deleteQuestion( ObjectId id ) {    
    	deleteById(id);
    }
    
    /*
     * 
     * Misc
     * 
     */

    // check if a question for this id exists
    public boolean checkIfQuestion(ObjectId id) {
    	Query<Questions> query = createQuery().field( "_id" ).equal( id );
		
		if( count(query) == 0) 
			return false;
		
    	return true;
    }
    
    public int calculateScore(int week_nr, AnswerView view) {
		List<String> answers = view.answers;
		int score = 0;
		
		for (int i = 0; i < answers.size(); i++) {
			Query<Questions> q = createQuery();
			q.and(
				q.criteria("week_nr").equal(week_nr),
				q.criteria("question_nr").equal(i+1),
				q.criteria("correct").equal(answers.get(i))
			);
			if (!q.asList().isEmpty())
				score++;
		}		
		return score;
    }
    
    // 'Prevent' the extra empty document after a post or a put 
    public void deleteUnwantedExtraEmptyQuestion() {
    	this.deleteByQuery(createQuery().filter("question_nr", null));
    }
}