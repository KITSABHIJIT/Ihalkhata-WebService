package com.exp.cemk.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.exp.cemk.util.ReturnJson;
import com.exp.cemk.webMethods.AndroidRegistration;
import com.exp.cemk.webMethods.DataEntry;
import com.exp.cemk.webMethods.GetExpenseData;
import com.exp.cemk.webMethods.GetHomePageData;
import com.exp.cemk.webMethods.GetIndividualExpense;
import com.exp.cemk.webMethods.GetNotificationData;
import com.exp.cemk.webMethods.GetPaymentData;
import com.exp.cemk.webMethods.GetProfileImage;
import com.exp.cemk.webMethods.GetUserAndItem;
import com.exp.cemk.webMethods.Login;

@Path("/service")
public class IhalkhataWebservice {
	@POST
	@Path("/doLogin")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson doLogin(MultivaluedMap<String, String> formParams) {
		return Login.userLogin(formParams.getFirst("param1"),
				formParams.getFirst("param2"));

	}

	@POST
	@Path("/home")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getHomepageData(MultivaluedMap<String, String> formParams) {
		return GetHomePageData.getData(formParams.getFirst("param1"),
				formParams.getFirst("param2"));

	}

	@POST
	@Path("/payment")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getPaymentData(MultivaluedMap<String, String> formParams) {
		return GetPaymentData.getPaymentDataTask(formParams);
	}

	@POST
	@Path("/paymentsDone")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getPaymentsDoneData(
			MultivaluedMap<String, String> formParams) {
		return GetPaymentData.getPaymentsDoneDataTask(formParams);
	}

	@POST
	@Path("/image")
	@Produces("image/*")
	@Consumes("application/x-www-form-urlencoded")
	public Response getProfileImage(MultivaluedMap<String, String> formParams) {
		return GetProfileImage.getImage(formParams);
	}

	@POST
	@Path("/expense")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getExpenseData(MultivaluedMap<String, String> formParams) {
		return GetExpenseData.getData(formParams);
	}

	@POST
	@Path("/individual")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getIndividualData(
			MultivaluedMap<String, String> formParams) {
		return GetIndividualExpense.getData(formParams);
	}

	@POST
	@Path("/usersanditemdata")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getUserAndItemsData(
			MultivaluedMap<String, String> formParams) {
		return GetUserAndItem.getUserAndItemsData(formParams);
	}

	@POST
	@Path("/enterdata")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson doDataEntry(MultivaluedMap<String, String> formParams) {
		return DataEntry.doDataEntry(formParams);
	}

	@POST
	@Path("/androidRegistration")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson doAndroidRegistration(
			MultivaluedMap<String, String> formParams) {
		return AndroidRegistration.doAndroidRegistration(formParams);
	}

	@POST
	@Path("/getNotification")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getNotification(MultivaluedMap<String, String> formParams) {
		return GetNotificationData.getNotificationData(formParams);
	}
}
