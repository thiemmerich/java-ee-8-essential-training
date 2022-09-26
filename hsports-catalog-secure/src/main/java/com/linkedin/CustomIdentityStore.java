package com.linkedin;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

@Named
@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

	public CredentialValidationResult validate(UsernamePasswordCredential credential) {
		
		if(credential.compareTo("kevin", "password")) {
			return new CredentialValidationResult("kevin",
					new HashSet<>(Arrays.asList("admin","basic")));
		}
		
		return CredentialValidationResult.INVALID_RESULT;
	}

	
}
