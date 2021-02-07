package com.github.tadukoo.web.rest.json;

import com.github.tadukoo.parsing.json.JSONClass;
import com.github.tadukoo.parsing.json.JSONObject;
import com.github.tadukoo.util.pojo.MappedPojo;

import java.lang.reflect.InvocationTargetException;
import java.net.http.HttpResponse;

/**
 * A Response Body Handler used for grabbing the response as a specific {@link JSONClass}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 *
 * @param <J> The {@link JSONClass} to convert the response into
 */
public class JSONClassBodyHandler<J extends JSONClass> implements HttpResponse.BodyHandler<J>{
	/** The {@link JSONClass} class to convert the response into */
	private final Class<J> clazz;
	
	/**
	 * Constructs a JSON Class Body Handler for the given {@link JSONClass}
	 *
	 * @param clazz The {@link JSONClass} class to convert the response into
	 */
	public JSONClassBodyHandler(Class<J> clazz){
		this.clazz = clazz;
	}
	
	/** {@inheritDoc} */
	@Override
	public HttpResponse.BodySubscriber<J> apply(HttpResponse.ResponseInfo responseInfo){
		return asJSON(clazz);
	}
	
	/**
	 * @param clazz The {@link JSONClass} to convert the response into
	 * @param <J> The specific {@link JSONClass} to convert the response into
	 * @return A Response Body Subscriber that will convert a response into a specific {@link JSONClass}
	 */
	public static <J> HttpResponse.BodySubscriber<J> asJSON(Class<J> clazz){
		return HttpResponse.BodySubscribers.mapping(
				// Convert it to a JSON Object first
				JSONObjectBodyHandler.asJSON(),
				// Then convert that JSON Object to the specific JSON Class
				(JSONObject body) -> {
					try{
						JSONClass result = (JSONClass) body;
						return clazz.getDeclaredConstructor(MappedPojo.class).newInstance(result);
					}catch(NoSuchMethodException | InstantiationException |
							IllegalAccessException | InvocationTargetException e){
						// TODO: Ideally send to a logger
						e.printStackTrace();
						throw new RuntimeException("Failed to handle JSON Class response");
					}
				}
		);
	}
}
