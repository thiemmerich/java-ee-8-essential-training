package com.linkedin;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@Named
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName="local")
public class SecurityConfig {

}
