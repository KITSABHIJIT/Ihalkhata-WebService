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

import domainmodel.Expenditure;
import domainmodel.Person;

public class GetExpenseData {
	private static final Logger logger = Logger.getLogger(GetExpenseData.class);

	public static ReturnJson getData(MultivaluedMap<String, String> formParams) {
		String groupId = formParams.getFirst("param1");
		String dataRange = formParams.getFirst("param2");
		JSONArray expenditureJsonArr = new JSONArray();
		ReturnJson result = new ReturnJson();
		try {
			logger.info("Controller-->getSelectedExpenditureGrid-->Spring Data Access-->getExpenditureData");
			List<Expenditure> expenditureList = new ArrayList<Expenditure>();
			List<Expenditure> offeringList = new ArrayList<Expenditure>();
			Test expenditureData = new Test();
			String month = DateUtil.getStartMonth(dataRange);
			String year = DateUtil.getStartYear(dataRange);
			String endmonth = DateUtil.getEndMonth(dataRange);
			String endYear = DateUtil.getEndYear(dataRange);
			expenditureList = expenditureData.getExpenditureData(month, year,
					endmonth, endYear, "1", groupId);
			offeringList = expenditureData.getExpenditureData(month, year,
					endmonth, endYear, "2", groupId);
			List<Person> usersList = new ArrayList<Person>();
			Test usersListData = new Test();
			usersList = usersListData.getUserData(groupId);

			for (Person p : usersList) {
				JSONObject expenditureItems = new JSONObject();
				expenditureItems.put("userId", (p.getUserName()));
				for (Expenditure ex : expenditureList) {
					if (p.getUserName().equals(ex.getUserId())) {
						expenditureItems.put(
								"expense",
								"Rs."
										+ CommonUtil.getRoundedValue(ex
												.getAmount()));
						break;
					} else {
						expenditureItems.put("expense",
								"Rs." + CommonUtil.getRoundedValue(0));
					}

				}
				for (Expenditure of : offeringList) {
					if (p.getUserName().equals(of.getUserId())) {
						expenditureItems.put(
								"offering",
								"Rs."
										+ CommonUtil.getRoundedValue(of
												.getAmount()));
						break;
					} else {
						expenditureItems.put("offering",
								"Rs." + CommonUtil.getRoundedValue(0));
					}

				}

				expenditureJsonArr.add(expenditureItems);
			}
			result.setSuccess(true);
			result.setMsg(expenditureJsonArr.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
