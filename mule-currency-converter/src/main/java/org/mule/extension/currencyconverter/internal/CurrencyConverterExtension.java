package org.mule.extension.currencyconverter.internal;


import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
//import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "currency-converter")
@Extension(name = "Currency Converter")
@ConnectionProviders(CurrencyConverterConnectionProvider.class)
//@Configurations(CurrencyConverterConfiguration.class)
@Operations({CurrencyConverterOperations.class, ScopeLogger.class})
public class CurrencyConverterExtension {

}
