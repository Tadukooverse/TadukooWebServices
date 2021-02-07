package com.github.tadukoo.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint represents a HTTP endpoint that can be accessed and run
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 *
 * @param <ResponseType> The type of the response
 */
public interface Endpoint<ResponseType>{
	
	/**
	 * @return The URL to use for this endpoint
	 */
	String getURL();
	
	/**
	 * @return The {@link MethodType} to use for this endpoint
	 */
	MethodType getMethodType();
	
	/**
	 * @return The headers to use for this endpoint - defaults to an empty map
	 */
	default Map<String, String> getHeaders(){
		return new HashMap<>();
	}
	
	/**
	 * @return A {@link HttpRequest.BodyPublisher BodyPublisher} to use for this endpoint - defaults to no body
	 */
	default HttpRequest.BodyPublisher getBodyPublisher(){
		return HttpRequest.BodyPublishers.noBody();
	}
	
	/**
	 * @return A {@link HttpResponse.BodyHandler BodyHandler} to use for this endpoint
	 */
	HttpResponse.BodyHandler<ResponseType> getBodyHandler();
	
	/**
	 * Runs the endpoint and returns the result - this version builds a new {@link HttpClient client}
	 *
	 * @return The response, formatted using the BodyHandler
	 * @throws IOException If anything goes wrong during sending/receiving
	 * @throws InterruptedException If the operation gets interrupted
	 */
	default ResponseType runEndpoint() throws IOException, InterruptedException{
		HttpClient client = HttpClient.newBuilder()
				.build();
		
		return runEndpoint(client);
	}
	
	/**
	 * Runs the endpoint and returns the result - this version takes in a {@link HttpClient client}
	 *
	 * @param client The {@link HttpClient} to be used
	 * @return The response, formatted using the BodyHandler
	 * @throws IOException If anything goes wrong during sending/receiving
	 * @throws InterruptedException If the operation gets interrupted
	 */
	default ResponseType runEndpoint(HttpClient client) throws IOException, InterruptedException{
		// Start a new HttpRequest
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
				.uri(URI.create(getURL()))
				.method(getMethodType().getMethod(), getBodyPublisher());
		// Add headers to the request
		getHeaders().forEach(requestBuilder::header);
		
		return client.send(requestBuilder.build(), getBodyHandler()).body();
	}
}
