package nl.roy.vlaamsbrabant.service;

import io.dropwizard.auth.Authorizer;
import nl.roy.vlaamsbrabant.model.Users;

public class AuthorizationService extends BaseService implements Authorizer<Users>
{
	@Override
	public boolean authorize(Users authenticator, String role )
	{
		return authenticator.hasRole(role);
	}

}
