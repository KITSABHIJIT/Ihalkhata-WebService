package com.exp.cemk.webMethods;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.exp.cemk.controller.GridPaymentDataProcessor;
import com.exp.cemk.util.ReturnJson;

public class GetHomePageData {
	private static final Logger logger = Logger
			.getLogger(GetHomePageData.class);

	public static ReturnJson getData(String userName, String groupId) {
		JSONObject returnJson = new JSONObject();
		ReturnJson result = new ReturnJson();
		try {
			logger.info("Servlet-->GetHomePageDataServlet-->Controller-->GridPaymentDataProcessor##getHomePaymentDetails");
			returnJson = GridPaymentDataProcessor.getInstance()
					.getHomePaymentDetails(groupId, userName);
			result.setSuccess(true);
			result.setMsg(returnJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
