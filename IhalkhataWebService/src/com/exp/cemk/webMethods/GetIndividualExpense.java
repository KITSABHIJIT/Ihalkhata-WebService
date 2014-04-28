package com.exp.cemk.webMethods;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import test.Test;

import com.exp.cemk.util.CommonUtil;
import com.exp.cemk.util.DateUtil;
import com.exp.cemk.util.ReturnJson;

import domainmodel.IndividualExpense;

public class GetIndividualExpense {
	private static final Logger logger = Logger
			.getLogger(GetIndividualExpense.class);

	public static ReturnJson getData(MultivaluedMap<String, String> formParams) {
		String userId = formParams.getFirst("param1");
		String dataRange = formParams.getFirst("param2");
		JSONArray returnJsonArr = new JSONArray();
		ReturnJson result = new ReturnJson();
		try {
			logger.info("Controller-->getIndividualExpense--->Spring Data Acess--->getIndividualExpenseData");

			String month = DateUtil.getStartMonth(dataRange);
			String year = DateUtil.getStartYear(dataRange);
			String endmonth = DateUtil.getEndMonth(dataRange);
			String endYear = DateUtil.getEndYear(dataRange);
			List<IndividualExpense> individualExpenseList = new ArrayList<IndividualExpense>();

			Test individualExpense = new Test();
			individualExpenseList = individualExpense.getIndividualExpenseData(
					userId, month, year, endmonth, endYear, "", "individual");

			for (int i = 0; i < individualExpenseList.size(); i++) {
				JSONObject jsonItems = new JSONObject();
				jsonItems.put("date", DateUtil.getFormatedDate("EEEE, dd MMM yyyy", "yyyy-MM-dd", individualExpenseList.get(i).getDate()));
				jsonItems.put("item", individualExpenseList.get(i).getItem());
				jsonItems.put("desc", individualExpenseList.get(i).getDesc());
				jsonItems.put("amount", "Rs."+(CommonUtil
						.getRoundedValue(individualExpenseList.get(i)
								.getAmount())));
				returnJsonArr.add(jsonItems);
			}
			result.setSuccess(true);
			result.setMsg(returnJsonArr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
