package com.github.tadukoo.web.rest.json;

import com.github.tadukoo.parsing.json.AbstractJSONClass;
import com.github.tadukoo.parsing.json.JSONClass;
import com.github.tadukoo.util.pojo.MappedPojo;
import com.github.tadukoo.web.rest.Endpoint;
import com.github.tadukoo.web.rest.MethodType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JSONClassBodyHandlerTest{
	private static class PostmanEchoGet extends AbstractJSONClass{
		
		public PostmanEchoGet(){
			super();
		}
		
		public PostmanEchoGet(MappedPojo pojo){
			super(pojo);
		}
		
		public JSONClass getArgs(){
			return (JSONClass) getItem("args");
		}
		
		public JSONClass getHeaders(){
			return (JSONClass) getItem("headers");
		}
		
		public String getURL(){
			return (String) getItem("url");
		}
	}
	
	@Test
	public void testJSONClassBodyHandler() throws IOException, InterruptedException{
		Endpoint<PostmanEchoGet> postmanEchoEndpoint = new Endpoint<>(){
			@Override
			public String getURL(){
				return "https://postman-echo.com/get?foo1=bar1&foo2=bar2";
			}
			
			@Override
			public MethodType getMethodType(){
				return MethodType.GET;
			}
			
			@Override
			public HttpResponse.BodyHandler<PostmanEchoGet> getBodyHandler(){
				return new JSONClassBodyHandler<>(PostmanEchoGet.class);
			}
		};
		
		// Run the endpoint
		PostmanEchoGet response = postmanEchoEndpoint.runEndpoint();
		
		// Grab the args and verify them
		JSONClass args = response.getArgs();
		assertNotNull(args);
		assertEquals("bar1", args.getItem("foo1"));
		assertEquals("bar2", args.getItem("foo2"));
		
		// Grab the headers and verify them
		JSONClass headers = response.getHeaders();
		assertNotNull(headers);
		assertEquals("https", headers.getItem("x-forwarded-proto"));
		assertEquals("postman-echo.com", headers.getItem("host"));
		assertNotNull(headers.getItem("user-agent"));
		assertEquals("443", headers.getItem("x-forwarded-port"));
		
		// Grab url and verify it
		assertEquals("https://postman-echo.com/get?foo1=bar1&foo2=bar2", response.getURL());
	}
}
