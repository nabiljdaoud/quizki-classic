package com.haxwell.apps.questions.managers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.haxwell.apps.questions.entities.AbstractEntity;
import com.haxwell.apps.questions.utils.CollectionUtil;
import com.haxwell.apps.questions.utils.StringUtil;

/**
 * Used to collect information from a Manager to return to a JSP that has been called by AJAX
 * 
 * @author johnathanj
 */
public class AJAXReturnData {
	
	public Collection<? extends AbstractEntity> entities = null;
	public int additionalItemCount = -1;
	public int additionalInfoCode = -1;
	
	protected Map<String, String> jsonStringMap = new HashMap<String, String>();
	
	public void addKeyValuePairToJSON(String key, String value) {
		jsonStringMap.put(key, value);
	}
	
	public String toJSON() {
		String rtn = "{ ";
		
		Iterator<String> iterator = jsonStringMap.keySet().iterator();
		
		while (iterator.hasNext())
		{
			String key = iterator.next();
			
			rtn += "\"" + key + "\": \"" + jsonStringMap.get(key) + "\", ";
		}
		
		rtn += "\"addlItemCount\":\"" + additionalItemCount + "\", \"addlInfoCode\":\"" + additionalInfoCode + "\" ";
		
		String entitiesAsJSONString = CollectionUtil.toJSON(entities, CollectionUtil.DONT_ADD_OPENING_CLOSING_CURLY_BRACES);
		
		if (!StringUtil.isNullOrEmpty(entitiesAsJSONString))
			rtn += ", " + entitiesAsJSONString;
		
		rtn += " }";
		
		return rtn;
	}
}
