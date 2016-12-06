package nl.roy.vlaamsbrabant.presentation;

import java.util.ArrayList;
import java.util.List;

import nl.roy.vlaamsbrabant.model.Motivation;
import nl.roy.vlaamsbrabant.presentation.model.MotivationView;

public class MotivationPresenter extends BasePresenter
{
    public MotivationView present( Motivation motivation )
    {
        MotivationView view = new MotivationView();
        
        view.id = motivation.getId().toString();
        view.title = motivation.getName();
        view.text = motivation.getText();
        return view;
    }
    
    public List<MotivationView> present( List<Motivation> motivations )
    {
        List<MotivationView> views = new ArrayList<MotivationView>();
        
        for( Motivation motivation : motivations )
            views.add( present( motivation ) );
        
        return views;
    }
}
