package org.mule.extension.currencyconverter.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.InputStream;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;
import org.mule.runtime.extension.api.annotation.param.Connection;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class CurrencyConverterOperations {
  
	@Parameter
	@Example("1.00")
	@DisplayName("Amount")
	private double Amount;
	
   @MediaType(value = ANY, strict = false)
   @DisplayName(CurrencyConverterConstants.Title)
   public InputStream convertCurrency(@Connection CurrencyConverterConnection connection) {
	   return connection.callHttpBase(Amount);
   }
   
   
  
}
