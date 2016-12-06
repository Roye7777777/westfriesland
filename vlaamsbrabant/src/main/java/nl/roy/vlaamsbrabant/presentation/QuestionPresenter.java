package nl.roy.vlaamsbrabant.presentation;

import java.util.ArrayList;
import java.util.List;

import nl.roy.vlaamsbrabant.model.Questions;
import nl.roy.vlaamsbrabant.presentation.model.QuestionView;

public class QuestionPresenter extends BasePresenter
{
    public QuestionView present( Questions question )
    {
        QuestionView view = new QuestionView();
        
        view.id = question.getId().toString();
        view.week_nr = question.getWeek_nr();
        view.question_nr = question.getQuestion_nr();
        view.question = question.getQuestion();
        view.a = question.getA();
        view.b = question.getB();
        view.c = question.getC();
        view.d = question.getD();
        view.e = question.getE();
        view.f = question.getF();
        view.correct = question.getCorrect();
                
        return view;
    }
    
    public List<QuestionView> present( List<Questions> questions )
    {
        List<QuestionView> views = new ArrayList<QuestionView>();
        
        for( Questions question : questions )
            views.add( present( question ) );
        
        return views;
    }
}
