package com.github.tadukoo.web.rest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndpointTest{
	private static class DummyEndpoint implements Endpoint<String>{
		@Override
		public String getURL(){
			return null;
		}
		
		@Override
		public MethodType getMethodType(){
			return null;
		}
		
		@Override
		public HttpResponse.BodyHandler<String> getBodyHandler(){
			return null;
		}
	}
	private final DummyEndpoint endpoint = new DummyEndpoint();
	
	@Test
	public void testDefaultHeaders(){
		assertTrue(endpoint.getHeaders().isEmpty());
	}
	
	@Test
	public void testDefaultBodyPublisher(){
		assertEquals(HttpRequest.BodyPublishers.noBody().getClass(), endpoint.getBodyPublisher().getClass());
	}
	
	@Test
	public void testGetEndpoint() throws IOException, InterruptedException{
		Endpoint<String> postmanEchoEndpoint = new Endpoint<>(){
			@Override
			public String getURL(){
				return "https://postman-echo.com/get";
			}
			
			@Override
			public MethodType getMethodType(){
				return MethodType.GET;
			}
			
			@Override
			public HttpResponse.BodyHandler<String> getBodyHandler(){
				return HttpResponse.BodyHandlers.ofString();
			}
		};
		assertNotNull(postmanEchoEndpoint.runEndpoint());
	}
}
