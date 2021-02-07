package com.github.tadukoo.web.rest;

/**
 * Method Type represents the HTTP method to be used in the HTTP Request.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public enum MethodType{
	/** Used for a GET method */
	GET("GET"),
	/** USed for a POST method */
	POST("POST"),
	/** Used for a PUT method */
	PUT("PUT"),
	/** Used for a DELETE method */
	DELETE("DELETE"),
	/** Used for a HEAD method */
	HEAD("HEAD"),
	/** Used for a CONNECT method */
	CONNECT("CONNECT"),
	/** Used for an OPTIONS method */
	OPTIONS("OPTIONS"),
	/** Used for a TRACE method */
	TRACE("TRACE"),
	/** Used for a PATCH method */
	PATCH("PATCH");
	
	/** The method string */
	private final String method;
	
	/**
	 * Creates a new Method Type with the given method string
	 *
	 * @param method The method string
	 */
	MethodType(String method){
		this.method = method;
	}
	
	/**
	 * @return The method string
	 */
	public String getMethod(){
		return method;
	}
}
