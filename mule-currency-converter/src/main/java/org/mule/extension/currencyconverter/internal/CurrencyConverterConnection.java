package org.mule.extension.currencyconverter.internal;

import java.io.IOException;
import java.io.InputStream;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpConstants.Method;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class CurrencyConverterConnection {
	
	private CurrencyConverterGenConfig genConfig;
	private int connectionTimeout;
	private HttpClient httpClient;
	private HttpRequestBuilder httpRequestBuilder;
	
	/**
	 * @param gConfig
	 * @param pConfig
	 * @param cTimeout
	 */
	public CurrencyConverterConnection(HttpService httpService, CurrencyConverterGenConfig gConfig, int cTimeout) {
		genConfig = gConfig;
		connectionTimeout = cTimeout;
		initHttpClient(httpService);
	}
	
	
	public void initHttpClient(HttpService httpService) {
		HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
		builder.setName("iTechtionsCurrencyConverter");
		httpClient = httpService.getClientFactory().create(builder.build());
		httpRequestBuilder = HttpRequest.builder();
		
		httpClient.start();
	}

	public void invalidate() {
    	httpClient.stop();
	}
	
	public boolean isConnected() throws Exception{		
		String ccURI = CurrencyConverterConstants.getURL();
		
		HttpRequest request = httpRequestBuilder
				.method(Method.GET)
				.uri(ccURI)		
				.build();
		
		HttpResponse httpResponse = httpClient.send(request, connectionTimeout, false, null);
		
		if(httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300)
			return true;
		else
			throw new ConnectionException("Error connectiong to the server: Error Code " + httpResponse.getStatusCode());
	}
	
	
	public InputStream callHttpBase(double amount) {
		String ccBase = genConfig.ccBaseDeclaration.toString();
		String ccTarget = genConfig.ccTargetDeclaration.toString();
		
		HttpResponse httpResponse = null;
		String strUri = CurrencyConverterConstants.getURL();
		
		System.out.println("URL is " + strUri);
		
		MultiMap<String, String> qp = new MultiMap<String, String>();
		qp.put("base", ccBase);
		qp.put("symbols", ccTarget);
		
		
		HttpRequest request = httpRequestBuilder
				.method(Method.GET)
				.uri(strUri)
				.queryParams(qp)
				.build();
		
		System.out.println("Request is: " + request);
		
		
		
		try {
			httpResponse = httpClient.send(request, connectionTimeout, false, null);
			System.out.println(httpResponse);
			return httpResponse.getEntity().getContent();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch (java.util.concurrent.TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
