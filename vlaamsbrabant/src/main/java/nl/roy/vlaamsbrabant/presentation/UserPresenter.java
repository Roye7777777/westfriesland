package nl.roy.vlaamsbrabant.presentation;

import java.util.ArrayList;
import java.util.List;

import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.presentation.model.UserView;

public class UserPresenter extends BasePresenter
{
    public UserView present( Users user )
    {
        UserView view = new UserView();
        
        view.id = user.getId().toString();
        view.username = user.getName();
        view.password = user.getPassword();
        view.first_name = user.getFirst_name();
        view.last_name = user.getLast_name();
        view.age = user.getAge();
        view.gender = user.getGender();
        view.weight = user.getWeight();
        view.length = user.getLength();
        view.roles = user.getRoles();
        view.exercise_diary = user.getExercise_diaries();
        view.food_diary = user.getFood_diaries();
                
        return view;
    }
    
    // Return UserView(s) for each user 
    public List<UserView> present( List<Users> users )
    {
        List<UserView> views = new ArrayList<UserView>();
        
        for( Users user : users )
            views.add( present( user ) );
        
        return views;
    }
}
