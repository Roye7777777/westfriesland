package nl.roy.vlaamsbrabant.presentation;

import java.util.ArrayList;
import java.util.List;

import nl.roy.vlaamsbrabant.model.Challenge;
import nl.roy.vlaamsbrabant.presentation.model.ChallengeView;



public class ChallengePresenter extends BasePresenter
{
    public ChallengeView present( Challenge challenge )
    {
        ChallengeView view = new ChallengeView();
        
        view.id = challenge.getId().toString();
        view.title = challenge.getTitle();
        view.week_nr = challenge.getWeek_nr();
                
        return view;
    }
    
    public List<ChallengeView> present( List<Challenge> challenges )
    {
        List<ChallengeView> views = new ArrayList<ChallengeView>();
        
        for( Challenge challenge : challenges )
            views.add( present( challenge ) );
        
        return views;
    }
}