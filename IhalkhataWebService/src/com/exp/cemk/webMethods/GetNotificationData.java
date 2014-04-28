package com.exp.cemk.webMethods;

import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.exp.cemk.controller.GridPaymentDetailsProcessor;
import com.exp.cemk.util.ReturnJson;

public class GetNotificationData {
	private static final Logger logger = Logger
			.getLogger(GetNotificationData.class);

	public static ReturnJson getNotificationData(
			MultivaluedMap<String, String> formParams) {
		String groupId = formParams.getFirst("param1");
		;
		String userId = formParams.getFirst("param2");
		;
		String type = formParams.getFirst("param3");
		;
		JSONArray returnJson = new JSONArray();
		ReturnJson result = new ReturnJson();
		try {
			logger.info("Controller-->getDebtCreditNotifications-->PaymentService-->CalculatorServiceImpl##getNotification");
			returnJson = GridPaymentDetailsProcessor.getInstance()
					.getAllDebtCreditNotifications(groupId, userId, type);
			result.setSuccess(true);
			result.setMsg(returnJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
