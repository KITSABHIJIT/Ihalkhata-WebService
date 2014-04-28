package com.exp.cemk.webMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import com.cemk.exp.dataentryservice.implementation.DataEntryServiceImpl;
import com.cemk.exp.dataentryservice.interfaces.DataEntryService;
import com.cemk.exp.dataentryservice.interfaces.DataEntryServiceException;
import com.cemk.exp.dataentryservice.interfaces.ExpenditureDTO;
import com.cemk.exp.date.util.StringToSQLDate;
import com.exp.cemk.AndroidNotification.GCMBroadcast;
import com.exp.cemk.controller.UserDataProcessor;
import com.exp.cemk.util.CommonUtil;
import com.exp.cemk.util.ReturnJson;

import domainmodel.Person;

public class DataEntry {
	private static final Logger logger = Logger.getLogger(DataEntry.class);

	public static ReturnJson doDataEntry(
			MultivaluedMap<String, String> formParams) {
		String date = formParams.getFirst("param1");
		String itemlist = formParams.getFirst("param2");
		String desc = formParams.getFirst("param3");
		String totalExp = formParams.getFirst("param4");
		String shareHolders = formParams.getFirst("param5");
		String shareHolderCount = formParams.getFirst("param6");
		String giver = formParams.getFirst("param7");
		Boolean flag = false;
		long expId = 0;
		logger.info("DataEntry-->Controller-->GridPaymentDataProcessor##getGridPaymentData");

		ReturnJson result = new ReturnJson();
		try {
			logger.info("Controller-->getIndividualExpense--->Spring Data Acess--->getIndividualExpenseData");
			ExpenditureDTO expDTO = new ExpenditureDTO();
			DataEntryService des = new DataEntryServiceImpl();
			ArrayList<String> shareholderList = new ArrayList<String>();
			Collections.addAll(shareholderList, shareHolders.split("[,]"));

			expDTO.setDate(StringToSQLDate.toSQLDate(date));
			expDTO.setItem(itemlist);
			expDTO.setItemDesc(desc);
			expDTO.setShareholderList(shareholderList);
			expDTO.setNoOfShareholder(Integer.parseInt(shareHolderCount));
			expDTO.setPrice(Double.parseDouble(totalExp));
			expDTO.setCreatorId(giver);
			try {
				logger.info("DataEntry-->Data Entry Service-->DataEntryServiceImpl##insertExpData");
				expId = des.insertExpData(expDTO);
				logger.info("Data Inserted with id : " + expId);
				flag = true;
			} catch (DataEntryServiceException e) {
				e.printStackTrace();
			}
			if (flag) {
				List<Person> groupUser = UserDataProcessor.getInstance()
						.getGroupUserData(giver);
				List<String> androidIdList = new ArrayList<String>();
				if (!groupUser.isEmpty()) {
					for (Person p : groupUser) {
						if (!CommonUtil.isNullorEmpty(p.getAndroidId()))
							androidIdList.add(p.getAndroidId());
					}
					if (!androidIdList.isEmpty()) {
						String msg = CommonUtil.constructNotificationMsg(expId);
						GCMBroadcast.getInstance().sendBroadCastMessage(msg,
								androidIdList);
					}
				}
				result.setSuccess(true);
				result.setMsg("Data has Been Succesfully Entered");
			} else {
				result.setSuccess(false);
				result.setMsg("Data Entry Failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
