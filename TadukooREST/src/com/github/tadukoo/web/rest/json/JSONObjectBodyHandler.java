package com.github.tadukoo.web.rest.json;

import com.github.tadukoo.parsing.json.JSONConverter;
import com.github.tadukoo.parsing.json.JSONObject;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * A Response Body Handler used for grabbing the response as a {@link JSONObject}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public class JSONObjectBodyHandler implements HttpResponse.BodyHandler<JSONObject>{
	
	/** {@inheritDoc} */
	@Override
	public HttpResponse.BodySubscriber<JSONObject> apply(HttpResponse.ResponseInfo responseInfo){
		return asJSON();
	}
	
	/**
	 * @return A Response Body Subscriber that will convert a response into a {@link JSONObject}
	 */
	public static HttpResponse.BodySubscriber<JSONObject> asJSON(){
		return HttpResponse.BodySubscribers.mapping(
				// Grab the response as a string first
				HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
				// Convert that string into a JSON Object
				(String body) -> {
					JSONConverter converter = new JSONConverter();
					return converter.parseJSON(body);
				}
		);
	}
}
