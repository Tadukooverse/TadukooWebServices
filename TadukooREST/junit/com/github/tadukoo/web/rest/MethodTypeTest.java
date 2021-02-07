package com.github.tadukoo.web.rest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodTypeTest{
	
	@Test
	public void testGET(){
		assertEquals("GET", MethodType.GET.getMethod());
	}
	
	@Test
	public void testPOST(){
		assertEquals("POST", MethodType.POST.getMethod());
	}
	
	@Test
	public void testPUT(){
		assertEquals("PUT", MethodType.PUT.getMethod());
	}
	
	@Test
	public void testDELETE(){
		assertEquals("DELETE", MethodType.DELETE.getMethod());
	}
	
	@Test
	public void testHEAD(){
		assertEquals("HEAD", MethodType.HEAD.getMethod());
	}
	
	@Test
	public void testCONNECT(){
		assertEquals("CONNECT", MethodType.CONNECT.getMethod());
	}
	
	@Test
	public void testOPTIONS(){
		assertEquals("OPTIONS", MethodType.OPTIONS.getMethod());
	}
	
	@Test
	public void testTRACE(){
		assertEquals("TRACE", MethodType.TRACE.getMethod());
	}
	
	@Test
	public void testPATCH(){
		assertEquals("PATCH", MethodType.PATCH.getMethod());
	}
}
