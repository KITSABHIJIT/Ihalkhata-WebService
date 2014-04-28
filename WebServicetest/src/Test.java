import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class Test {
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		MultivaluedMap queryParams = new MultivaluedMapImpl();
		//queryParams.add("param1", "abhijit");
		queryParams.add("param1", "100");
		queryParams.add("param2", "1");

		// Get XML for application
		/*System.out.println(service.queryParams(queryParams).path("rest")
				.path("dologin").accept(MediaType.APPLICATION_JSON)
				.get(String.class));*/
		ClientResponse response = service.type(
				"application/x-www-form-urlencoded").post(ClientResponse.class,
				queryParams);
		System.out.println(response.getEntity(String.class));
	}

	private static URI getBaseURI() {
		return UriBuilder
				.fromUri(
						"http://ihalkhata.j.layershift.co.uk/IhalkhataWebService/rest/service/expense")
				.build();
	}
}