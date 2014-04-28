package com.exp.cemk.webMethods;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.exp.cemk.controller.UserDataProcessor;
import com.exp.cemk.util.ReturnJson;

public class AndroidRegistration {
	private static final Logger logger = Logger
			.getLogger(AndroidRegistration.class);

	public static ReturnJson doAndroidRegistration(
			MultivaluedMap<String, String> formParams) {
		ReturnJson todo = new ReturnJson();
		String regId = formParams.getFirst("param1");
		String userId = formParams.getFirst("param2");
		logger.info("Controller-->updateAndroidRegId-->Spring Data Access-->getUserData");
		int rowsAffected = UserDataProcessor.getInstance().updateAndroidRegId(
				regId, userId);
		if (rowsAffected > 0) {
			logger.info("## " + userId + ": Android Reg Id updated to " + regId);
			todo.setSuccess(true);
			todo.setMsg("## " + userId + ": Android Reg Id updated to " + regId);
		} else {
			logger.info(userId + ": Android Reg Id updaion failed");
			todo.setSuccess(false);
			todo.setMsg(userId + ": Android Reg Id updaion failed");
		}
		return todo;
	}
}
