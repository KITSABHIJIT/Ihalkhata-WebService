package com.exp.cemk.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import test.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cemk.exp.calculationservice.implementations.CalculatorServiceImpl;
import com.cemk.exp.calculationservice.pojo.Payment;
import com.exp.cemk.util.CommonUtil;

import domainmodel.Expenditure;

public class GridPaymentDataProcessor {
	private static GridPaymentDataProcessor _instance = new GridPaymentDataProcessor();
	private static final Logger logger = Logger
			.getLogger(GridPaymentDataProcessor.class);

	public static GridPaymentDataProcessor getInstance() {
		// log.debug("AutoFillDemo::getInstance ");

		return _instance;
	}

	public JSONObject getGridPaymentData(int start, int limit, String groupId) {
		logger.info("Controller-->GridPaymentDataProcessor-->Controller-->getSelectedPaymentGrid");

		JSONObject JSONPayment = new JSONObject();

		JSONPayment = getSelectedPaymentGrid(start, limit, groupId);

		// log.debug("In getMatchingBrokers");
		return JSONPayment;
	}

	public JSONObject getSelectedPaymentGrid(int start, int limit,
			String groupId) {
		int reccnt = 0;
		// Connection con = null;
		// Statement stmt = null;
		// ResultSet rs = null;
		int end = start + limit;

		logger.info("Controller-->GridPaymentDataProcessor-->PaymentService-->CalculatorServiceImpl##doCalculationPayments");
		try {

			JSONObject jsonItems = null;

			JSONObject jsonObjectTotalCount = null;

			JSONArray arrayObj = new JSONArray();

			// log.debug("segments are     %%%%%%%" + seg);
			// System.out.println(" inside while of ID query");
			jsonObjectTotalCount = new JSONObject();
			List<Payment> paymentList = new ArrayList<Payment>();

			/*
			 * DeSerializationExpenseData ded= new DeSerializationExpenseData();
			 * ExpenseData ed=ded.getExpenseData();
			 * expDataReturnList=ed.expDataList;
			 * 
			 * System.out.println("Deserialized Expense Data...");
			 */
			CalculatorServiceImpl paymentData = new CalculatorServiceImpl();

			paymentList = paymentData.doCalculationPayments(groupId);
			for (int i = 0; i < paymentList.size(); i++) {
				if (paymentList.get(i).getAmount() > 0) {
					jsonItems = new JSONObject();
					jsonItems.put("amount", paymentList.get(i).getAmount());
					jsonItems.put("borrower", paymentList.get(i).getBorrower());
					jsonItems.put("payee", paymentList.get(i).getPayee());
					jsonItems.put("from", "from");
					jsonItems.put("relation", "will get");
					reccnt = reccnt + 1;
					arrayObj.add(jsonItems);
				}
			}
			JSONArray arrayObjFinal = new JSONArray();

			for (int p = start; p < end; p++) {
				if (p <= arrayObj.size() - 1) {
					arrayObjFinal.add(arrayObj.get(p));
				}
			}

			jsonObjectTotalCount.put("totalCount", reccnt);
			jsonObjectTotalCount.put("items", arrayObjFinal);

			// System.out.println("Data found in ID Query = "+checkFlag);
			// logger.debug("PaymentService-->CalculatorServiceImpl##doCalculationPayments-->"+jsonObjectTotalCount.toString());
			return jsonObjectTotalCount;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.debug("getClientsCache Query Is q1 [  ] Failed");
			e.printStackTrace();
		} finally {
			// BaseDBUtil.closeDbResources(con, stmt, rs);
		}
		return null;
	}

	public JSONObject getHomePaymentDetails(String groupId, String userId) {
		try {
			JSONObject jsonObject = null;
			jsonObject = new JSONObject();
			List<Expenditure> gainerLoserList = new ArrayList<Expenditure>();
			List<Expenditure> debitCreditList = new ArrayList<Expenditure>();
			Test homeData = new Test();
			logger.info("Controller-->getHomePaymentDetails-->Spring Data Access-->getGainerLoserData");
			gainerLoserList = homeData.getGainerLoserData(groupId);
			String gainer = "", loser = "", debtAmount = "", creditAmount = "";
			if (!gainerLoserList.isEmpty()) {
				for (Expenditure ex : gainerLoserList) {
					if (ex.getAmount() > 0)
						gainer = ((gainer == "") ? ex.getUserId() + " Rs."
								+ CommonUtil.getRoundedValue(ex.getAmount())
								: gainer
										+ "<br/>"
										+ ex.getUserId()
										+ " Rs."
										+ CommonUtil.getRoundedValue(ex
												.getAmount()));
					if (ex.getAmount() < 0)
						loser = ((loser == "") ? ex.getUserId() + " Rs."
								+ CommonUtil.getRoundedValue((-ex.getAmount()))
								: loser
										+ "<br/>"
										+ ex.getUserId()
										+ " Rs."
										+ CommonUtil.getRoundedValue((-ex
												.getAmount())));
				}
				if (gainer != "")
					jsonObject.put("gainer", gainer);
				else
					jsonObject.put("gainer", "Not Available");
				if (loser != "")
					jsonObject.put("loser", loser);
				else
					jsonObject.put("loser", "Not Available");

			} else {
				jsonObject.put("gainer", "Not Available");
				jsonObject.put("loser", "Not Available");
			}
			logger.info("Controller-->getHomePaymentDetails-->Spring Data Access-->getCreditDebitData");
			debitCreditList = homeData.getCreditDebitData(groupId, userId);
			if (!debitCreditList.isEmpty()) {
				for (Expenditure ex : debitCreditList) {
					if (ex.getAmount() > 0) {
						creditAmount = " Rs."
								+ CommonUtil.getRoundedValue(ex.getAmount());
						debtAmount = " Rs." + CommonUtil.getRoundedValue(0.00);
					}
					if (ex.getAmount() < 0) {
						debtAmount = " Rs."
								+ CommonUtil.getRoundedValue(-ex.getAmount());
						creditAmount = " Rs."
								+ CommonUtil.getRoundedValue(0.00);
					}
				}
				if (gainer != "")
					jsonObject.put("debit", debtAmount);
				else
					jsonObject.put("debit", "Not Available");
				if (loser != "")
					jsonObject.put("credit", creditAmount);
				else
					jsonObject.put("credit", "Not Available");

			} else {
				jsonObject.put("debit", "Not Available");
				jsonObject.put("credit", "Not Available");
			}
			// System.out.println("Data found in ID Query = "+checkFlag);
			// logger.debug("Controller-->getHomePaymentDetails-->"+jsonObject.toString());
			return jsonObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// log.debug("getClientsCache Query Is q1 [  ] Failed");
			e.printStackTrace();
		} finally {
			// BaseDBUtil.closeDbResources(con, stmt, rs);
		}
		return null;
	}
}
