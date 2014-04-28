package com.exp.cemk.webMethods;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import test.Test;

import com.cemk.exp.calculationservice.implementations.CalculatorServiceImpl;
import com.cemk.exp.calculationservice.interfaces.CalculationServiceException;
import com.cemk.exp.calculationservice.pojo.Payment;
import com.exp.cemk.util.CommonUtil;
import com.exp.cemk.util.DateUtil;
import com.exp.cemk.util.ReturnJson;

import domainmodel.Payments;

public class GetPaymentData {
	public static ReturnJson getPaymentDataTask(
			MultivaluedMap<String, String> formParams) {
		ReturnJson todo = new ReturnJson();
		List<Payment> paymentList = new ArrayList<Payment>();

		String groupId = formParams.getFirst("param1");
		todo.setSuccess(true);
		JSONArray paymentData = new JSONArray();
		CalculatorServiceImpl paymentDataDump = new CalculatorServiceImpl();
		try {
			paymentList = paymentDataDump.doCalculationPayments(groupId);
		} catch (CalculationServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < paymentList.size(); i++) {
			if (paymentList.get(i).getAmount() > 0) {
				JSONObject payment = new JSONObject();
				payment.put("payee", paymentList.get(i).getPayee());
				payment.put("borrower", paymentList.get(i).getBorrower());
				payment.put("amount", CommonUtil.getRoundedValue(paymentList
						.get(i).getAmount()));
				paymentData.add(payment);
			}
		}
		todo.setMsg(paymentData.toString());
		return todo;

	}

	public static ReturnJson getPaymentsDoneDataTask(
			MultivaluedMap<String, String> formParams) {
		ReturnJson todo = new ReturnJson();
		String groupId = formParams.getFirst("param1");
		todo.setSuccess(true);
		JSONArray paymentData = new JSONArray();
		List<Payments> paidPaymentsList = new ArrayList<Payments>();
		Test paidPaymentsData = new Test();
		paidPaymentsList = paidPaymentsData.getPaidPayments(groupId);
		for (int i = 0; i < paidPaymentsList.size(); i++) {
			if (paidPaymentsList.get(i).getAmount() > 0) {
				JSONObject payment = new JSONObject();
				payment.put("payee", paidPaymentsList.get(i).getPayee());
				payment.put("receiver", paidPaymentsList.get(i).getReceiver());
				payment.put("amount", CommonUtil
						.getRoundedValue(paidPaymentsList.get(i).getAmount()));
				payment.put("date", DateUtil.getFormatedDate(
						"EEEE, dd MMM yyyy", "yyyy-MM-dd", paidPaymentsList
								.get(i).getDate()));
				paymentData.add(payment);
			}
		}
		todo.setMsg(paymentData.toString());
		return todo;

	}

}
