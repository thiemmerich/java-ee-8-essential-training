package com.linkedin;

import java.io.IOException;

import javax.annotation.security.DeclareRoles;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@DeclareRoles({"admin","basic"})
@ServletSecurity(@HttpConstraint(rolesAllowed="admin"))
@WebServlet("/secure")
public class SecureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject
	private SecurityContext securityContext;
	
    public SecureServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("User: ").append(securityContext.getCallerPrincipal().getName());
	}

}
