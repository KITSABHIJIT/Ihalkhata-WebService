package com.exp.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.exp.webMethods.ReturnJson;
import com.exp.webMethods.WebMethods;

@Path("/service")
public class TestWebService {
	@POST
	@Path("/writeData")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson doLogin(MultivaluedMap<String, String> formParams) {
		return WebMethods.writeData(formParams.getFirst("param1"));

	}

	@POST
	@Path("/readData")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/x-www-form-urlencoded")
	public ReturnJson getHomepageData(MultivaluedMap<String, String> formParams) {
		return WebMethods.readData();

	}

}
