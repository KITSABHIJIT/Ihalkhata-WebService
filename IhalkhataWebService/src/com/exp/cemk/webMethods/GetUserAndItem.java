package com.exp.cemk.webMethods;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import test.Test;

import com.exp.cemk.util.ReturnJson;

import domainmodel.Person;

public class GetUserAndItem {
	private static final Logger logger = Logger.getLogger(GetUserAndItem.class);

	public static ReturnJson getUserAndItemsData(
			MultivaluedMap<String, String> formParams) {
		String userId = formParams.getFirst("param1");
		String groupId = formParams.getFirst("param2");
		logger.info("Controller-->getSelectedUserData-->Spring Data Access-->getUserData");
		List<Person> usersList = new ArrayList<Person>();
		Test usersListData = new Test();
		usersList = usersListData.getUserData(groupId);
		JSONArray userNameArray = new JSONArray();
		JSONArray userIdArray = new JSONArray();
		for (Person p : usersList) {
			userIdArray.add(p.getUserId());
			userNameArray.add(p.getUserName());
		}
		logger.info("Controller-->GetItemsProcessor-->Spring Data Access-->getMatchItems");
		Test getItems = new Test();
		List<String> itemsList = getItems.getMatchItems(null, userId);
		JSONArray itemsArray = new JSONArray();
		for (String s : itemsList)
			itemsArray.add(s);

		JSONObject resultObj = new JSONObject();
		resultObj.put("users", userNameArray);
		resultObj.put("usersId", userIdArray);
		resultObj.put("items", itemsArray);
		ReturnJson result = new ReturnJson();
		result.setSuccess(true);
		result.setMsg(resultObj.toString());
		return result;

	}
}
