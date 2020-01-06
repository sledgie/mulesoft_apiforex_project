package org.mule.extension.currencyconverter.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;

import javax.inject.Inject;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class CurrencyConverterConnectionProvider implements PoolingConnectionProvider<CurrencyConverterConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterConnectionProvider.class);

 /**
  * A parameter that is always required to be configured.
  */
  @Parameter
  @Placement(tab = "Advanced")
  @Optional(defaultValue = "50000")
  int connectionTimeout;

  @ParameterGroup(name = "Converter")
  CurrencyConverterGenConfig genConfig;
  
  @Inject
  private HttpService httpService;
 
  @Override
  public CurrencyConverterConnection connect() throws ConnectionException {
    return new CurrencyConverterConnection(httpService, genConfig, connectionTimeout);
  }

  @Override
  public void disconnect(CurrencyConverterConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting to Backend API " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(CurrencyConverterConnection connection) {
	  ConnectionValidationResult result;
	  
	  try {
		  if(connection.isConnected()) {
			  result = ConnectionValidationResult.success();
		  }
		  else {
			  result = ConnectionValidationResult.failure("Connection Failed", new Exception());
		  }
	  }
	  catch(Exception e) {
		  result = ConnectionValidationResult.failure("Connection Failed: " + e.getMessage(), new Exception());
	  }
	  
    return result;
  }
}
