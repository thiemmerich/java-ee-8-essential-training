package com.linkedin.jsf;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

@Logging
@Interceptor
public class LoggingInterceptor {

	private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class);

	public LoggingInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		LOG.info("Method invoked is " + ic.getMethod().getName());
		return ic.proceed();
	}

}
