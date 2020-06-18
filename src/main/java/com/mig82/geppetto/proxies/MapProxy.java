package com.mig82.geppetto.proxies;

import com.mig82.geppetto.helpers.JsonHelper;

import java.util.Arrays;
import java.util.Map;
import java.util.Collection;
//import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

public class MapProxy {

	private static final Logger LOGGER = Logger.getLogger( MapProxy.class.getName() );

	private Map<String, Object> map;

	public MapProxy(){
		this(new HashMap<String, Object>());
	}

	public MapProxy(Map<String, Object> map){
		this.map = map;
	}

	public MapProxy(Object[] maps, int index){

		//this.map = maps[index] == null ? new HashMap<String, Object>() : (HashMap<String, Object>)maps[index];
		if(index < maps.length && maps[index] != null){

			map = (HashMap<String, Object>)maps[index];
		}
		else{

			map = new HashMap<String, Object>();

			LOGGER.log(Level.INFO, "That's odd. Unable to find Fabric parameter map at index {0}.\n\t" +
					"There are only {1} maps in the maps array.", new Object[]{
					index,
					maps.length
			});
		}

	}

	public void put(String key, Object value) {
		map.put(key, value);
	}

	public Boolean containsKey(String key){
		return containsKey(key, false);
	}

	public Boolean containsKey(String key, Boolean ignoreCase){
		Boolean found = false;
		if (map.containsKey(key)){
			found = true;
		}
		else if(ignoreCase){
			found = getRealKey(key) == null ? false : true;
		}
		return found;
	}

	public String getRealKey(String key){
		String realKey = null;
		if (map.containsKey(key)) {
			realKey = key;
		}
		else {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if(entry.getKey().toUpperCase().equals(key.toUpperCase())){
					realKey = entry.getKey();
					break;
				}
			}
		}
		return realKey;
	}

	public String getString(String key) {
		return getString(key, null);
	}

	public String getString(String key, String defaultValue){
		String realKey = getRealKey(key);
		return realKey != null ? (String)map.get(realKey) : defaultValue;
	}

	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}

	public Boolean getBoolean(String key, Boolean defaultValue){

		Boolean booleanValue = defaultValue;

		String realKey = getRealKey(key);
		if(realKey != null){
			if(map.get(realKey) instanceof Boolean){
				booleanValue = (Boolean)map.get(realKey);
			}
			else if (map.get(realKey) instanceof String){
				booleanValue = Boolean.parseBoolean((String)map.get(realKey));
			}
		}
		return booleanValue;
	}

	public Integer getInteger(String key) {
		return getInteger(key, null);
	}

	public Integer getInteger(String key, Integer defaultValue){

		Integer integerValue = defaultValue;

		String realKey = getRealKey(key);
		if(realKey != null){
			if(map.get(realKey) instanceof Integer){
				integerValue = (Integer)map.get(realKey);
			}
			else if (map.get(realKey) instanceof String){
				integerValue = Integer.parseInt((String)map.get(realKey));
			}
		}
		return integerValue;
	}

	public Float getFloat(String key) {
		return getFloat(key, null);
	}

	public Float getFloat(String key, Float defaultValue){

		Float integerValue = defaultValue;

		String realKey = getRealKey(key);
		if(realKey != null){
			if(map.get(realKey) instanceof Float){
				integerValue = (Float)map.get(realKey);
			}
			else if (map.get(realKey) instanceof String){
				integerValue = Float.parseFloat((String)map.get(realKey));
			}
		}
		return integerValue;
	}

	public Map<String, Object> parseMapFromJson(String key){

		String realKey = getRealKey(key);
		if(realKey != null && map.get(realKey) != null){
			String json = (String)map.get(realKey);
			return JsonHelper.toMap(json);
		}
		else{
			return null;
		}
	}

	public Collection<String> parseStringCollectionFromJson(String key){

		String realKey = getRealKey(key);
		if(realKey != null && map.get(realKey) != null){
			String json = (String)map.get(realKey);
			return JsonHelper.toStringCollection(json);
		}
		else{
			return null;
		}
	}

	public Collection<Integer> parseIntCollectionFromJson(String key){

		String realKey = getRealKey(key);
		if(realKey != null && map.get(realKey) != null){
			String json = (String)map.get(realKey);
			return JsonHelper.toIntCollection(json);
		}
		else{
			return null;
		}
	}

	/**
	 * Retrieves a value from the map and parses is into a Collection of Maps -e.g.: [{"a":1, "b": "foo"}, {"c":3}].
	 * */
	public Collection<Map<String, Object>> parseMapCollectionFromJson(String key){

		String realKey = getRealKey(key);
		if(realKey != null && map.get(realKey) != null){
			String json = (String)map.get(realKey);
			return JsonHelper.toCollection(json);
		}
		else{
			return null;
		}
	}

	/**
	 * Returns the name of the class of the value for a given key.
	 * This is specially useful for determining what type is Fabric receiving a parameter as.
	 * */
	public String getClassName(String key){
		String realKey = getRealKey(key);
		if(realKey != null){
			return map.get(realKey).getClass().getCanonicalName();
		}
		else{
			return null;
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(map.entrySet().toArray());
	}
}
