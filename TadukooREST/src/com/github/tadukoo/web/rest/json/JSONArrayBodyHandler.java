package com.github.tadukoo.web.rest.json;

import com.github.tadukoo.parsing.json.JSONArray;
import com.github.tadukoo.parsing.json.JSONArrayList;
import com.github.tadukoo.parsing.json.JSONClass;
import com.github.tadukoo.parsing.json.JSONObject;
import com.github.tadukoo.util.pojo.MappedPojo;

import java.lang.reflect.Constructor;
import java.net.http.HttpResponse;
import java.util.function.Function;

/**
 * A Response Body Handler used for grabbing the response as a {@link JSONArray}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1.2
 * @since Alpha v.0.1
 */
public class JSONArrayBodyHandler<Item> implements HttpResponse.BodyHandler<JSONArray<Item>>{
	/** The class to convert the response items into */
	private final Class<Item> clazz;
	
	/**
	 * Constructs a JSON Array Body Handler for the given type of item
	 *
	 * @param clazz The class to convert the response items into
	 */
	public JSONArrayBodyHandler(Class<Item> clazz){
		this.clazz = clazz;
	}
	
	/** {@inheritDoc} */
	@Override
	public HttpResponse.BodySubscriber<JSONArray<Item>> apply(HttpResponse.ResponseInfo responseInfo){
		return asJSON(clazz);
	}
	
	/**
	 * @return A Response Body Subscriber that will convert a response into a {@link JSONArray}
	 */
	@SuppressWarnings("unchecked")
	public static <Item> HttpResponse.BodySubscriber<JSONArray<Item>> asJSON(Class<Item> clazz){
		return HttpResponse.BodySubscribers.mapping(
				JSONObjectBodyHandler.asJSON(),
				(Function<? super JSONObject, ? extends JSONArray<Item>>) (JSONObject body) -> {
					try{
						if(clazz == Object.class || clazz == String.class || clazz == Boolean.class || clazz == Double.class){
							// Certain classes can just be cast
							return (JSONArrayList<Item>) body;
						}else if(JSONClass.class.isAssignableFrom(clazz)){
							// If it's a JSONClass, we can use constructors
							Constructor<Item> constructor = clazz.getDeclaredConstructor(MappedPojo.class);
							JSONArrayList<JSONClass> classList = (JSONArrayList<JSONClass>) body;
							
							// Convert the JSONClasses to the proper class
							JSONArrayList<Item> itemList = new JSONArrayList<>();
							for(JSONClass jsonClass: classList){
								itemList.add(constructor.newInstance(jsonClass));
							}
							return itemList;
						}else{
							throw new IllegalArgumentException("Don't know how to convert with class " + clazz.getCanonicalName());
						}
					}catch(Exception e){
						// TODO: Ideally send to a logger
						e.printStackTrace();
						throw new RuntimeException("Failed to handle JSON Class response");
					}
				}
		);
	}
}
