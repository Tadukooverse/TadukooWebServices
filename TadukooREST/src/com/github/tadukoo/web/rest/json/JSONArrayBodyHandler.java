package com.github.tadukoo.web.rest.json;

import com.github.tadukoo.parsing.json.JSONArray;
import com.github.tadukoo.parsing.json.JSONObject;

import java.net.http.HttpResponse;

/**
 * A Response Body Handler used for grabbing the response as a {@link JSONArray}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public class JSONArrayBodyHandler implements HttpResponse.BodyHandler<JSONArray>{
	
	/** {@inheritDoc} */
	@Override
	public HttpResponse.BodySubscriber<JSONArray> apply(HttpResponse.ResponseInfo responseInfo){
		return asJSON();
	}
	
	/**
	 * @return A Response Body Subscriber that will convert a response into a {@link JSONArray}
	 */
	public static HttpResponse.BodySubscriber<JSONArray> asJSON(){
		return HttpResponse.BodySubscribers.mapping(
				JSONObjectBodyHandler.asJSON(),
				(JSONObject body) -> (JSONArray) body
		);
	}
}
