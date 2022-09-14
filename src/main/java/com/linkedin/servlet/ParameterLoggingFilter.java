package com.linkedin.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.jboss.logging.Logger;

/**
 * Servlet Filter implementation class ParameterLoggingFilter
 */
@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class ParameterLoggingFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(ParameterLoggingFilter.class);
    /**
     * Default constructor. 
     */
    public ParameterLoggingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.getParameterMap().entrySet().stream().forEach(entry ->{
			//System.out.println(String.format("%s:%s", entry.getKey(), entry.getValue()[0]));
			LOGGER.info(String.format("%s:%s", entry.getKey(), entry.getValue()[0]));
		});
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
