package nl.roy.vlaamsbrabant.service;

import java.util.Optional;

import javax.inject.Inject;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import nl.roy.vlaamsbrabant.model.Users;
import nl.roy.vlaamsbrabant.persistence.UserDAO;

public class AuthenticationService extends BaseService
				implements Authenticator< BasicCredentials, Users >
{
	private final UserDAO userDAO;
	
	@Inject
	public AuthenticationService( UserDAO userDAO )
	{
		this.userDAO = userDAO;
	}

	@Override
	public Optional<Users> authenticate(BasicCredentials credentials) throws AuthenticationException 
	{
		Users user = userDAO.getByName( credentials.getUsername() );
		
		if ((user != null) && checkPassword(user, credentials.getPassword() ) )
			return Optional.of(user);
		else 
			return Optional.empty();
			
	}
	
	private boolean checkPassword( Users user, String password)
	{
		return user.getPassword().equals( password);
	}
	

}
