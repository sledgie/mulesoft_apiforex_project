package org.mule.extension.currencyconverter.internal;

public class CurrencyConverterConstants {
	public static final String Title = "Convert Currency";
	private static final String baseUri = "https://api.ratesapi.io/api/latest";
	
	private CurrencyConverterConstants() {
		
	}
	
	public static String getURL() {	
		return (baseUri);
	}
}
