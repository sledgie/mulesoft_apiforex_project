package org.mule.extension.currencyconverter.internal;

import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

public class CurrencyConverterGenConfig {
	private static final String GENL = "General";
	
	public enum Base{
		EUR, CAD, PHP, INR
	};
	
	public enum Target{
		EUR, CAD, PHP, INR
	};

	@Parameter
	@Placement(tab = GENL)
	@DisplayName("Base")
	@Summary("Base: EUR, CAD, PHP, INR")
	@Expression(org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED)	
	String ccBaseDeclaration;
	
	//public String getBase() {
	//	return ccBaseDeclaration.name();
	//}
	
	@Parameter
	@Placement(tab = GENL)
	@DisplayName("Target")
	@Summary("Target: EUR, CAD, PHP, INR")
	@Expression(org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED)
	String ccTargetDeclaration;
	
	//public String getTarget() {
	//	return ccTargetDeclaration.toString();
	//}
}
